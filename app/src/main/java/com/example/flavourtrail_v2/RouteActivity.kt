package com.example.flavourtrail_v2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.flavourtrail_v2.data.AppDatabase
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.example.flavourtrail_v2.data.dao.RoutePlaceDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RouteActivity : BaseActivity() {
    @Composable
    override fun Content() {
        RouteScreen()
    }
}

@SuppressLint("DiscouragedApi")
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
            else -> 2 // Default to Route 2 for other values
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

    LaunchedEffect(routeId) {
        val route = routeDao.getRouteById(routeId)
        routeName = route?.name ?: "Unknown Route"
        routeDescription = route?.description ?: "No description available."

        val allRoutePlaces = routePlaceDao.getAllRoutePlaces()
        //val routeId = if (selectedRoute == 1) routeId1 else routeId2

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
            // Search Bar with Buttons
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
                    itemsIndexed(filteredPlaceNames) { index, place ->
                        val placeIndex = placeNames.indexOf(place)
                        val imageFileName = place.replace(" ", "_")      // Replace spaces with underscores
                            .lowercase()          // Convert to lowercase
                            .replace("é", "e")     // Replace accented 'é' with 'e'
                            .replace(".", "_")     // Replace periods with underscores
                            .replace("__", "_")    // Ensure no double underscores
                            .trim('_')             // Remove trailing underscores if any
                        val drawableId = context.resources.getIdentifier(imageFileName, "drawable", context.packageName)

                        // Check if the image exists in drawable folder
                        val imageBitmap = if (drawableId != 0) {
                            painterResource(id = drawableId)
                        } else {
                            painterResource(id = R.drawable.placeholder)
                        }

                        val placeType = filteredPlaceTypes[placeIndex]

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navigateToDetailsActivity(context, routePlaceDao, routeId, index)
                                }

                        ) {
                            // Display Image
                            imageBitmap?.let {
                                Image(
                                    painter = it,
                                    contentDescription = "$place Image",
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color.Gray)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // Display Name and Type
                            Column(modifier = Modifier.weight(1f)) {
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

                            Spacer(modifier = Modifier.weight(1f))

                            // Display Number with Frame on the right
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(30.dp)  // Adjust the size of the frame
                                    .clip(RoundedCornerShape(50))  // Round the frame
                                    .border(2.dp, Color.Black, RoundedCornerShape(50))  // Border around the frame
                                    .padding(4.dp)  // Padding inside the frame
                            ) {
                                Text(
                                    text = (index + 1).toString(),
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Black),
                                    textAlign = TextAlign.Center
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

fun navigateToDetailsActivity(
    context: Context,
    routePlaceDao: RoutePlaceDao,
    routeId: Int,
    clickedIndex: Int
) {
    CoroutineScope(Dispatchers.IO).launch {
        // Retrieve place IDs based on current route
        val placeIds = routePlaceDao.getAllRoutePlaces()
            .filter { it.routeId == routeId }
            .map { it.placeId }

        // Start intent on main-thread
        withContext(Dispatchers.Main) {
            val intent = Intent(context, DetailsActivity::class.java).apply {
                putIntegerArrayListExtra("PLACE_IDS", ArrayList(placeIds))
                putExtra("CLICKED_INDEX", clickedIndex)
            }
            context.startActivity(intent)
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