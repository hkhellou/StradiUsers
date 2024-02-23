package com.example.stradiusers.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.stradiusers.databinding.ErrorExceptionDialogFragmentBinding

class ErrorExceptionDialog(private val errorMessage: String) : DialogFragment() {

    private lateinit var binding: ErrorExceptionDialogFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ErrorExceptionDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.tvErrorMessage.text = errorMessage
        initListeners()
    }

    private fun initListeners() {
        binding.btnErrorDialog.setOnClickListener {
            dialog?.dismiss()
        }

        binding.imgCloseDialog.setOnClickListener {
            dialog?.dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        /** ajustamos el ancho del dialogo al 85% de porcentaje de ancho de la pantalla del dispositivo **/
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog?.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
    }

}