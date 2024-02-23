package com.example.stradiusers.fragments

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stradiusers.R
import com.example.stradiusers.activity.MainActivity
import com.example.stradiusers.adapters.OptionsAdapter
import com.example.stradiusers.databinding.FragmentUserDetailBinding
import com.example.stradiusers.utils.CircleImageTrasnformator
import com.example.stradiusers.utils.OptionsList
import com.example.stradiusers.viewmodels.UserDetailViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDetailFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var bundle: Bundle
    private val viewModel: UserDetailViewModel by viewModels()
    private lateinit var userArgs: UserDetailFragmentArgs
    private lateinit var adapter: OptionsAdapter
    private lateinit var optionsList: List<OptionsList>
    private val REQUEST_IMAGE_CAPTURE = 1

    companion object {
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                launchCamera()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        initValues()
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.imgCamera.setOnClickListener {
            requestPermissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    private fun initValues() {
        bundle = arguments ?: return
        userArgs = UserDetailFragmentArgs.fromBundle(bundle)
        optionsList = viewModel.createOptionsList(userArgs.userArgument)
        val fullName = "${userArgs.userArgument?.name?.first} ${userArgs.userArgument?.name?.last}"
        Picasso.get().load(userArgs.userArgument?.picture?.large)
            .transform(CircleImageTrasnformator()).into(binding.imgUser)
        configAdapter()
        val actividadPrincipal = activity
        if (actividadPrincipal is MainActivity) {
            actividadPrincipal.supportActionBar?.title = fullName
        }

    }

    private fun launchCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    private fun configAdapter() {
        adapter = OptionsAdapter(optionsList)
        binding.rvOptions.layoutManager = LinearLayoutManager(context)
        binding.rvOptions.adapter = adapter
    }

    private fun initObservers() {
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val latitude = userArgs.userArgument!!.location.coordinates.latitude
        val longitude = userArgs.userArgument!!.location.coordinates.longitude
        val marker = R.drawable.img_marker_map
        val markerTag = getString(R.string.user_location)
        val location = LatLng(latitude.toDouble(), longitude.toDouble())

        googleMap.addMarker(
            MarkerOptions().position(location).title(markerTag)
                .icon(BitmapDescriptorFactory.fromResource(marker))
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5f))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        val actividadPrincipal = activity
        if (actividadPrincipal is MainActivity) {
            actividadPrincipal.supportActionBar?.title = getString(R.string.toolbar_title_contacts)
        }
        _binding = null
    }
}
