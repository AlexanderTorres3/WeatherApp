package com.example.weatherapp

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.databinding.FragmentSearchBinding
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment :Fragment(R.layout.fragment_search){
    @Inject lateinit var viewModel: SearchFragmentViewModel
    private lateinit var binding: FragmentSearchBinding
    private lateinit var currentConditions: CurrentConditions
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.enableButton.observe(this) { enable ->
            binding.zipCodeButton.isEnabled = enable
        }

        binding.zipCodeEditText.addTextChangedListener( object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                p0?.toString()?.let { viewModel.updateZipCode(it) }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        viewModel.currentConditions.observe(this){result ->
            currentConditions = result
        }

        binding.zipCodeButton.setOnClickListener {
            try {
                viewModel.loadDataZip()
                val action = SearchFragmentDirections
                    .actionSearchFragmentToCurrentConditionsFragment(currentConditions)
                findNavController().navigate(action)
            } catch (e: retrofit2.HttpException){
                SubmitErrorDialog().show(childFragmentManager, SubmitErrorDialog.TAG)
            }
        }

        binding.getLocationButton.setOnClickListener {
            requestLocation()
            try {
                viewModel.loadDataLatLon()
                val action = SearchFragmentDirections
                    .actionSearchFragmentToCurrentConditionsFragment(currentConditions)
                findNavController().navigate(action)
            } catch (e: retrofit2.HttpException){
                SubmitErrorDialog().show(childFragmentManager, SubmitErrorDialog.TAG)
            }
        }

        binding.notificationButton.setOnClickListener {
            requestLocation()
            if (ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context!!,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }else {
                if (viewModel.getNotificationStatus() == false) {
                    viewModel.setNotificationsToTrue()
                    requireActivity().startService(Intent(context, NotificationService::class.java))
                } else {
                    viewModel.setNotificationsToFalse()
                    requireActivity().stopService(Intent(context, NotificationService::class.java))
                }
            }
            updateNotificationButton()
        }

        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    requestLocation()
                }
                else -> {

                }
            }
        }
    }



    private fun requestLocation(){
        if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
            AlertDialog.Builder(context)
                .setTitle(R.string.location_rationale)
                .setNeutralButton("Ok"){_, _->
                    locationPermissionRequest.launch(
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                    )
                }
                .show()
        }else {
            locationPermissionRequest.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
            )
        }
    }

    override fun onResume() {
        super.onResume()
        requestLocationUpdate()
        updateNotificationButton()
    }

    private fun requestLocationUpdate(){
        if (ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val locationProvider = LocationServices.getFusedLocationProviderClient(context!!)

        locationProvider.lastLocation.addOnSuccessListener {
            if(it != null) {
                viewModel.updateLatLon(it.latitude, it.longitude)
            }
        }
    }

    private fun updateNotificationButton(){
        if (!viewModel.getNotificationStatus()){
            binding.notificationButton.text = "Turn Notifications On"
        } else{
            binding.notificationButton.text = "Turn Notifications Off"
        }
    }
}