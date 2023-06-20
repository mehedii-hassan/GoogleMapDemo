package com.example.googlemapdemo.utils

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object LocationPermissionService {

    fun isLocationPermissionGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLocationPermission(launcher: ActivityResultLauncher<String>) {
        launcher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
}