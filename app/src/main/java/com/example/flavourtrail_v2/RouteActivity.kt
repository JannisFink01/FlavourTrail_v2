package com.example.flavourtrail_v2

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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

    // Safely get route_id from the Intent
    val routeIdFromIntent = (context as? RouteActivity)?.intent?.getIntExtra("ROUTE_ID", -1) ?: -1

    // Safely get numberOfStops from the Intent
    val numberOfStops = (context as? RouteActivity)?.intent?.getIntExtra("numberOfStops", 0) ?: 0

    // Determine the route_id based on numberOfStops only if routeIdFromIntent is not set
    val routeId = if (routeIdFromIntent != -1) {
        routeIdFromIntent
    } else {
        when (numberOfStops) {
            5 -> 1 // Route 1 for 5 stops
            4 -> 2 // Route 2 for 4 stops
            else -> 2 // Default to Route 2
        }
    }

    var searchText by remember { mutableStateOf(TextFieldValue("")) }

    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val cameraPositionState = rememberCameraPositionState()
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

    val database = AppDatabase.getInstance(context)
    val routePlaceDao = database.routePlaceDao()
    val placeDao = database.placeDao()
    val routeDao = database.routeDao()

    var placeNames by remember { mutableStateOf<List<String>>(emptyList()) }
    var placeTypes by remember { mutableStateOf<List<String>>(emptyList()) }
    var routeName by remember { mutableStateOf("") }
    var routeDescription by remember { mutableStateOf("") }

    // Fetch current location
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
            currentLocation = LatLng(0.0, 0.0) // Default location if permissions are missing
        }
    }

    // Fetch route and place data based on the determined routeId
    LaunchedEffect(routeId) {
        val route = routeDao.getRouteById(routeId)
        routeName = route?.name ?: "Unknown Route"
        routeDescription = route?.description ?: "No description available."

        val allRoutePlaces = routePlaceDao.getAllRoutePlaces()
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
        topBar = {
            TopAppBar(title = { Text(routeName) })
        },
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
            // Display route description
            Text(
                text = routeDescription,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Search bar with buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedTextField(
                    value = searchText,
                    onValueChange = { newText -> searchText = newText },
                    label = { Text("Search") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp)
                        .height(42.dp),
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Display Google Map
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

            // Display list of places
            if (filteredPlaceNames.isNotEmpty()) {
                LazyColumn {
                    items(filteredPlaceNames) { place ->
                        val placeIndex = placeNames.indexOf(place)
                        val placeType = filteredPlaceTypes[placeIndex]

                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(
                                text = place,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = placeType,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Gray
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            } else {
                Text("No places found for this route.")
            }
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