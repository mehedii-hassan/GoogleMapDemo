package com.example.googlemapdemo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

class MapMarkerIcon(private val context: Context) {

    fun markerIcon(icon: Int): BitmapDescriptor {
        // Create a Drawable object from the desired drawable icon
        val drawableIcon: Drawable? =
            ContextCompat.getDrawable(context, icon)
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