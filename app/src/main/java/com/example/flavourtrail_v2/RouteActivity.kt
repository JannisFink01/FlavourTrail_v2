package com.example.flavourtrail_v2

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.flavourtrail_v2.data.AppDatabase
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.ImageBitmap
import java.io.InputStream

fun loadImageFromAssets(imageName: String, context: Context): ImageBitmap? {
    return try {
        val assetManager = context.assets
        val inputStream: InputStream = assetManager.open(imageName)
        val imageBitmap = android.graphics.BitmapFactory.decodeStream(inputStream)
        imageBitmap.asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

class RouteActivity : BaseActivity() {
    @Composable
    override fun Content() {
        RouteScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteScreen() {
    val context = LocalContext.current
    val numberOfStops = (context as? RouteActivity)?.intent?.getIntExtra("numberOfStops", 0) ?: 0

    // Determine selected route based on number of stops
    val defaultRoute = when (numberOfStops) {
        5 -> 1 // Route 1 for input 5
        4 -> 2 // Route 2 for input 4
        else -> 2 // Default to Route 2 for other values
    }
    val selectedRoute by remember { mutableStateOf(defaultRoute) }

    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val cameraPositionState = rememberCameraPositionState()
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

    val database = AppDatabase.getInstance(context)
    val routePlaceDao = database.routePlaceDao()
    val placeDao = database.placeDao()

    var placeNames by remember { mutableStateOf<List<String>>(emptyList()) }
    var placeTypes by remember { mutableStateOf<List<String>>(emptyList()) }
    val routeId1 = 1
    val routeId2 = 2

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    currentLocation = LatLng(location.latitude, location.longitude)
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(
                        currentLocation!!,
                        15f
                    )
                }
            }
        } else {
            currentLocation = LatLng(0.0, 0.0)
        }
    }

    LaunchedEffect(selectedRoute) {
        val allRoutePlaces = routePlaceDao.getAllRoutePlaces()
        val routeId = if (selectedRoute == 1) routeId1 else routeId2

        val placeIds = allRoutePlaces.filter { it.routeId == routeId }.map { it.placeId }

        val places = placeIds.mapNotNull { placeId ->
            placeDao.getPlaceById(placeId)
        }

        placeNames = places.map { it.name }
        placeTypes = places.map { it.type }
    }

    val filteredPlaceNames = placeNames.filter { place ->
        place.lowercase().contains(searchText.text.lowercase())
    }
    val filteredPlaceTypes = placeTypes.filterIndexed { index, _ ->
        filteredPlaceNames.contains(placeNames[index])
    }

    Scaffold(
        bottomBar = {
            ButtonBar(
                onSaveClick = { /* Handle Save Click */ },
                onNavigateClick = { /* Handle Navigate Click */ },
                onAddLocationClick = { /* Handle Add Location Click */ }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Search Bar with Buttons (Added back into the UI)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Search Bar
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newText -> searchText = newText },
                    label = { Text("Search") },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "Search Icon"
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .height(42.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )

                // Right Buttons
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(onClick = { /* Handle Right Button 1 Click */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fullscreen),
                            contentDescription = "Fullscreen Icon"
                        )
                    }

                    IconButton(onClick = { /* Handle Right Button 2 Click */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_share),
                            contentDescription = "Share Icon"
                        )
                    }

                    IconButton(onClick = { /* Handle Right Button 3 Click */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_download),
                            contentDescription = "Download Icon"
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Google Maps
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.LightGray)
            ) {
                if (currentLocation != null) {
                    GoogleMap(
                        modifier = Modifier.matchParentSize(),
                        cameraPositionState = cameraPositionState
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display List of Place Names with Images and Type
            if (filteredPlaceNames.isNotEmpty()) {
                LazyColumn {
                    items(filteredPlaceNames) { place ->
                        val placeIndex = placeNames.indexOf(place)
                        val imageBitmap = loadImageFromAssets("${place.split(" ")[0]}.jpg", context)
                        val placeType = filteredPlaceTypes[placeIndex]

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (imageBitmap != null) {
                                Image(
                                    bitmap = imageBitmap,
                                    contentDescription = "${place.split(" ")[0]} Image",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.Gray)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = "$place",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "$placeType",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Gray
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else {
                Text(text = "No places found for selected route.")
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ButtonBar(
    onSaveClick: () -> Unit,
    onNavigateClick: () -> Unit,
    onAddLocationClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = onSaveClick, modifier = Modifier.weight(1f)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = "Save Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Save", maxLines = 1)
        }

        Button(onClick = onNavigateClick, modifier = Modifier.weight(1f)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_navigate),
                contentDescription = "Navigate Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Navigate", maxLines = 1)
        }

        Button(onClick = onAddLocationClick, modifier = Modifier.weight(1f)) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add_location),
                contentDescription = "Add Location Icon",
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Add Location", maxLines = 1)
        }
    }
}