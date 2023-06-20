package com.example.googlemapdemo


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.googlemapdemo.databinding.FragmentMapBinding

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {


    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap

    private lateinit var locationList: ArrayList<LatLng>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)



        locationList = ArrayList()

        val futurePark = LatLng(23.813334, 90.4242)
        val nikunjo = LatLng( 23.797911, 90.414391)
        val laMerridian = LatLng( 23.767811, 90.413391)



        locationList.add(futurePark)
        locationList.add(nikunjo)
        locationList.add(laMerridian)


        val mapFragment = childFragmentManager
            .findFragmentById(R.id.googleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        googleMap.clear()
        googleMap.uiSettings.isZoomControlsEnabled = true

        for (l in locationList.indices) {

            Log.e("TAG", "l " + l + "" + locationList[l])

            mMap.addMarker(MarkerOptions().position(locationList[l]).title("Marker"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationList[l], 10f))
        }

    }

}