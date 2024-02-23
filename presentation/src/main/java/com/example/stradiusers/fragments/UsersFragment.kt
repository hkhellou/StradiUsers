package com.example.stradiusers.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.operations.UserParams
import com.example.stradiusers.adapters.UsersListAdapter
import com.example.stradiusers.databinding.FragmentUsersBinding
import com.example.stradiusers.dialogs.ErrorExceptionDialog
import com.example.stradiusers.viewmodels.UsersFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Random

@AndroidEntryPoint
class UsersFragment : Fragment(), UsersListAdapter.UserClickListener {

    private var _binding: FragmentUsersBinding? = null
    private lateinit var adapter: UsersListAdapter
    private val viewModel: UsersFragmentViewModel by viewModels()
    private lateinit var bundle: Bundle
    private lateinit var quantityArgs: UsersFragmentArgs
    private val binding get() = _binding!!
    private var callUsers: Boolean = true
    private var quantityPagination = "10"
    private var quantityPaginationAux = "10"
    private var restante = 0
    private var page = 1
    private var seed = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initValues()
        initListeners()
        initObservers()
        getUsers()
    }

    private fun getUsers() {
        if (quantityArgs.quantity?.toInt()!! <= 10) quantityPagination =
            quantityArgs.quantity.toString()
        if (callUsers) viewModel.getUsers(page.toString(), quantityPagination, seed.toString())
        restante = quantityArgs.quantity!!.toInt() - quantityPagination.toInt()
    }

    private fun initListeners() {
        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val view: View = binding.rvUsers.getChildAt(binding.rvUsers.childCount - 1)
                val diff: Int = view.bottom - (binding.rvUsers.height + binding.rvUsers.scrollY)

                if (diff == 0 && restante > 0) {
                    viewModel.showLoader.postValue(true)
                    page += 1
                    quantityPagination = restante.toString()
                    if (quantityPagination.toInt() > quantityPaginationAux.toInt()) {
                        viewModel.getUsers(page.toString(), quantityPaginationAux, seed.toString())
                    } else {
                        viewModel.getUsers(page.toString(), quantityPagination, seed.toString())
                    }
                    restante -= quantityPaginationAux.toInt()
                }
            }
        })
    }

    private fun initObservers() {
        viewModel.showLoader.observe(viewLifecycleOwner, Observer { showLoader ->
            if (showLoader) {
                binding.loader.visibility = VISIBLE
                binding.rvUsers.visibility = GONE
            } else {
                binding.loader.visibility = GONE
                binding.rvUsers.visibility = VISIBLE
            }

        })
        viewModel.itemMutableList.observe(viewLifecycleOwner, Observer {
            configAdapter(it)
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { errorMessage ->
            ErrorExceptionDialog(errorMessage).show(parentFragmentManager, "errorDialog")
        })
    }

    private fun initValues() {
        bundle = arguments ?: return
        quantityArgs = UsersFragmentArgs.fromBundle(bundle)
        seed = Random().nextInt(50)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configAdapter(list: List<UserParams>) {
        adapter = UsersListAdapter(list, this)
        binding.rvUsers.layoutManager = LinearLayoutManager(context)
        binding.rvUsers.adapter = adapter
    }

    override fun userClickListener(user: UserParams) {
        val action =
            UsersFragmentDirections.actionUsersFragmentToUserDetailFragment().setUserArgument(user)
        findNavController().navigate(action)
    }

    override fun onPause() {
        super.onPause()
        /** se pone esta variable en false para que no vuelva a cargar nuevos usuarios al volver desde la vista de detalle de usuario **/
        callUsers = false
    }
}