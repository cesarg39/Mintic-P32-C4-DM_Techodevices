package com.misiontic2022.technodevices.view.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.misiontic2022.technodevices.R
import com.misiontic2022.technodevices.databinding.FragmentMapsLocationBinding

class MapsLocationFragment : Fragment(R.layout.fragment_maps_location), OnMapReadyCallback {

    private lateinit var binding: FragmentMapsLocationBinding

    private lateinit var map: GoogleMap

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapsLocationBinding.bind(view)
        createMapFragment()

    }

    private fun createMapFragment() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }



}