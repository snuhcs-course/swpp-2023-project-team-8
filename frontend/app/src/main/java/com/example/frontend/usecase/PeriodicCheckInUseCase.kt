package com.example.frontend.usecase

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.frontend.utilities.PeriodicTask
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class PeriodicCheckInUseCase(private val context: Context) {
    private val checkInUseCase = CheckInUseCase(context)
    private val locationClient = LocationServices.getFusedLocationProviderClient(context)

    fun execute() {

        PeriodicTask(
            command = { recordLocation() },
            intervalInMinutes = INTERVAL_IN_MINUTES
        ).execute()
    }

    private fun recordLocation() {
        Log.i(TAG, "execute recordLocation")
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.e(TAG, "No location permission")
            return
        }

        locationClient.getCurrentLocation(PRIORITY, null).addOnSuccessListener { location ->
            location?.let {
                Log.i(TAG, "Location: $location")
                checkInUseCase.execute(location.latitude, location.longitude)
            }
        }
    }

    companion object {
        private const val TAG = "PeriodicCheckInUseCase"
        private const val PRIORITY = Priority.PRIORITY_HIGH_ACCURACY
        private const val INTERVAL_IN_MINUTES = 5L
    }
}
