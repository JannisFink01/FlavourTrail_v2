@file:Suppress("DEPRECATION")

package com.example.flavourtrail_v2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import java.util.Locale
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlanRouteActivity : BaseActivity() {
    @Composable
    override fun Content() {
        PlanRouteScreen(this)
    }
}

@Composable
fun PlanRouteScreen(context: Context) {
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val cityName = remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    // Launcher to request location permission
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    FlavourTrail_v2Theme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Choose your city",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(
                    onClick = {
                        locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        coroutineScope.launch {
                            fetchCurrentLocation(context, fusedLocationClient, cityName)
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.location_pin),
                        contentDescription = "Location Icon",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "My Location")
                }

                OutlinedTextField(
                    value = cityName.value,
                    onValueChange = { cityName.value = it },
                    label = { Text("Enter city") },
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@SuppressLint("MissingPermission")
suspend fun fetchCurrentLocation(
    context: Context,
    fusedLocationClient: FusedLocationProviderClient,
    cityName: MutableState<String>
) {
    try {
        val locationRequest = com.google.android.gms.location.LocationRequest.create().apply {
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 500 // 1 Sekunde
            fastestInterval = 300
        }

        // Listener für Standortupdates
        val locationCallback = object : com.google.android.gms.location.LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                val location = locationResult.lastLocation
                if (location != null) {
                    Log.d("LocationDebug", "Updated Location: Lat=${location.latitude}, Long=${location.longitude}")
                    cityName.value = getCityNameFromCoordinates(context, location.latitude, location.longitude)
                }
            }
        }

        // Standortupdates starten
        withContext(Dispatchers.IO) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }

    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Error fetching location: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}

private fun getCityNameFromCoordinates(context: Context, latitude: Double, longitude: Double): String {
    return try {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (!addresses.isNullOrEmpty()) {
            addresses[0].locality ?: "Unknown City"
        } else {
            "City not found"
        }
    } catch (e: Exception) {
        Log.e("GeocoderError", "Error fetching city name: ${e.message}")
        "Error fetching city"
    }
}
