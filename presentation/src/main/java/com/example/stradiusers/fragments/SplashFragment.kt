package com.example.stradiusers.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.stradiusers.R
import com.example.stradiusers.activity.MainActivity
import com.example.stradiusers.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding
    private var quantity = ""
    private lateinit var spannableString: SpannableString

    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val actividadPrincipal = activity
        if (actividadPrincipal is MainActivity) {
            actividadPrincipal.supportActionBar?.hide()
        }
        initValues()
        initListeners()

    }

    private fun initValues() {
        val hint = getString(R.string.quantity_et_hint)
        spannableString = SpannableString(hint)
        spannableString.setSpan(
            ForegroundColorSpan(Color.RED),
            0,
            hint.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    private fun initListeners() {
        binding?.btnAccept?.setOnClickListener {
            quantity = binding?.etQuantityUsers?.text.toString()
            if (!quantity.isEmpty() && quantity.toInt() >= 1 && quantity.toInt() <= 5000) {
                val action = SplashFragmentDirections.actionSplashFragmentToUsersFragment()
                    .setQuantity(quantity)
                findNavController().navigate(action)
            } else {
                binding?.etQuantityUsers?.text?.clear()
                binding?.etQuantityUsers?.hint = spannableString
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.etQuantityUsers?.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val actividadPrincipal = activity
        if (actividadPrincipal is MainActivity) {
            actividadPrincipal.supportActionBar?.show()
        }
        _binding = null
    }
}