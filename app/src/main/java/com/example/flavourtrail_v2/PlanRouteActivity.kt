@file:Suppress("DEPRECATION")

package com.example.flavourtrail_v2

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

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

    val transportModeOptions = listOf("Car", "Public Transport", "Bike", "By foot")
    val selectedTransportMode = remember { mutableStateOf("") }
    val numberOfStops = remember { mutableStateOf("") }
    var showDropdown by remember { mutableStateOf(false) }

    // Liste der Tags
    val staticPlaceTags = listOf(
        "Club",
        "Bar",
        "Biergarten",
        "Café",
        "Cocktails",
        "Mocktails",
        "Craft beer",
        "Rooftop",
        "Beach view",
        "Live music"
    )

    val selectedTags = remember { mutableStateListOf<String>() }

    val scrollState = rememberScrollState()

    FlavourTrail_v2Theme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxWidth().verticalScroll(scrollState)
            ) {
                // Überschrift oben
                Text(
                    text = "Plan your Route",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 25.dp)
                )

                // Erster Bereich: Stadt auswählen
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Choose your city",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
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
                            .padding(bottom = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Zweiter Bereich: Transportmodus und Stopps
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Choose Transport mode and amount of stops",
                        fontSize = 15.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            OutlinedTextField(
                                value = selectedTransportMode.value,
                                onValueChange = { newValue ->
                                    if (transportModeOptions.contains(newValue)) {
                                        selectedTransportMode.value = newValue
                                    }
                                },
                                label = { Text("Transport mode") },
                                modifier = Modifier.fillMaxWidth(),
                                readOnly = true, // Verhindert direkte Eingabe
                                trailingIcon = {
                                    IconButton(onClick = { showDropdown = !showDropdown }) {
                                        Icon(
                                            painter = painterResource(id = android.R.drawable.arrow_down_float),
                                            contentDescription = "Dropdown Icon"
                                        )
                                    }
                                }
                            )
                            DropdownMenu(
                                expanded = showDropdown,
                                onDismissRequest = { showDropdown = false }
                            ) {
                                transportModeOptions.forEach { mode ->
                                    DropdownMenuItem(
                                        text = { Text(mode) },
                                        onClick = {
                                            selectedTransportMode.value = mode
                                            showDropdown = false
                                        }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        OutlinedTextField(
                            value = numberOfStops.value,
                            onValueChange = { newValue ->
                                if (newValue.isEmpty() || (newValue.toIntOrNull() in 1..9)) {
                                    numberOfStops.value = newValue
                                }
                            },
                            label = { Text("Stops") },
                            modifier = Modifier.width(100.dp),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Dritter Bereich: Präferenzen wählen
                if (selectedTags.isNotEmpty()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        selectedTags.forEach { tag ->
                            Chip(tagName = tag, onRemove = { selectedTags.remove(tag) })
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.LightGray)
                        .padding(16.dp)
                ) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        item {
                            Text(
                                text = "Choose your preferences",
                                fontSize = 15.sp,
                                modifier = Modifier.padding(bottom = 16.dp)
                            )
                        }

                        items(staticPlaceTags) { tag ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Checkbox(
                                    checked = selectedTags.contains(tag),
                                    onCheckedChange = { isChecked ->
                                        if (isChecked) {
                                            selectedTags.add(tag)
                                        } else {
                                            selectedTags.remove(tag)
                                        }
                                    }
                                )
                                Text(
                                    text = tag,
                                    modifier = Modifier.padding(start = 8.dp),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons am Ende
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = { /* Trail AI Button Logic */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Trail AI")
                    }

                    Button(
                        onClick = { /* Let's Go Button Logic */ },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "Let's Go!")
                    }
                }

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

@Composable
fun Chip(tagName: String, onRemove: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = tagName,
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            IconButton(onClick = onRemove) {
                Icon(
                    painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                    contentDescription = "Remove Tag",
                    tint = Color.White
                )
            }
        }
    }
}
