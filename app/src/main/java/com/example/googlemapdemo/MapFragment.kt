package com.example.googlemapdemo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.googlemapdemo.databinding.FragmentMapBinding
import com.example.googlemapdemo.utils.LocationPermissionService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback {


    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationList: ArrayList<LatLng>
    private lateinit var markerIconList: ArrayList<BitmapDescriptor>

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
            if (result) {

            } else {
                //show dialog and explain why you need this permission

            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)



        locationList = ArrayList()
        markerIconList = ArrayList()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.googleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        //locationList.clear()

        val markerIcon = markerIcon(R.drawable.location)
        val markerIconOne = markerIcon(R.drawable.marker_icon)
        val markerIconTwo = markerIcon(R.drawable.marker_icon_one)

        markerIconList.add(markerIcon)
        markerIconList.add(markerIconOne)
        markerIconList.add(markerIconTwo)
        markerIconList.add(markerIconOne)
        markerIconList.add(markerIconTwo)
        markerIconList.add(markerIconOne)

        googleMap.clear()
        googleMap.uiSettings.isZoomControlsEnabled = true

        if (LocationPermissionService.isLocationPermissionGranted(requireContext())) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)

                    locationList.add(currentLatLng)
                    locationList.add(LatLng(23.813334, 90.4242))
                    locationList.add(LatLng(23.797911, 90.414391))
                    locationList.add(LatLng(23.7980911, 90.414401))
                    locationList.add(LatLng(23.797921, 90.434391))
                    locationList.add(LatLng(23.797901, 90.454391))

                    for (l in locationList.indices) {
                        Log.e("TAG", "l " + l + "" + locationList[l])
                        mMap.addMarker(
                            MarkerOptions().position(locationList[l]).title("Marker")
                                .icon(markerIconList[l])
                        )
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationList[l], 13f))
                    }
                }
            }


        } else {
            LocationPermissionService.requestLocationPermission(launcher)
        }
    }


    private fun markerIcon(icon: Int): BitmapDescriptor {
        // Create a Drawable object from the desired drawable icon
        val drawableIcon: Drawable? =
            ContextCompat.getDrawable(requireContext(), icon)
        // Convert the Drawable to a Bitmap
        val bitmapIcon: Bitmap = drawableIcon?.let {
            val canvas = Canvas()
            val bitmap =
                Bitmap.createBitmap(it.intrinsicWidth, it.intrinsicHeight, Bitmap.Config.ARGB_8888)
            canvas.setBitmap(bitmap)
            it.setBounds(0, 0, it.intrinsicWidth, it.intrinsicHeight)
            it.draw(canvas)
            bitmap
        } ?: Bitmap.createBitmap(
            1,
            1,
            Bitmap.Config.ARGB_8888
        )
        // val markerIcon = BitmapDescriptorFactory.fromBitmap(bitmapIcon)
        return BitmapDescriptorFactory.fromBitmap(bitmapIcon)
    }

}