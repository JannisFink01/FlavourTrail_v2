package com.example.flavourtrail_v2

import androidx.compose.runtime.collectAsState
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flavourtrail_v2.data.AppDatabase
import com.example.flavourtrail_v2.data.entity.Route
import com.example.flavourtrail_v2.data.repository.RouteRepository
import com.example.flavourtrail_v2.ui.CustomNavigationBar
import com.example.flavourtrail_v2.ui.TopBar
import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * MainActivity for the FlavourTrail app, serving as the entry point for the user interface.
 *
 * This activity initializes the application theme, sets up the top and bottom bars, and
 * hosts the main content layout, including buttons, map, and a list of favorite routes.
 */
class MainActivity : ComponentActivity() {
    /**
     * Called when the activity is first created. Configures the app theme and sets up the UI.
     *
     * @param savedInstanceState If the activity is being re-initialized, this contains the
     *                            most recent data supplied in `onSaveInstanceState`.
     *                            Otherwise, it is null.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlavourTrail_v2Theme {
                var selectedTab by remember { mutableStateOf("") }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopBar(
                            userName = "John Doe",
                            profileImageRes = R.drawable.profile_user
                        )
                    },
                    bottomBar = {
                        CustomNavigationBar(
                            onItemSelected = { selectedTab = it }
                        )
                    }
                ) { innerPadding ->
                    MainContent(
                        modifier = Modifier.padding(innerPadding),
                        onPlanRouteClick = {
                            // Launch PlanRouteActivity
                            val intent = Intent(this, PlanRouteActivity::class.java)
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

/**
 * ViewModel responsible for managing and providing route data.
 *
 * @property routeRepository Repository used to fetch route data from the database.
 */
class RouteViewModel(private val routeRepository: RouteRepository) : ViewModel() {
    private val _routes = MutableStateFlow<List<Route>>(emptyList())

    /** A state flow containing the list of routes. */
    val routes: StateFlow<List<Route>> = _routes

    init {
        loadRoutes()
    }

    /**
     * Loads all routes from the repository and updates the state.
     */
    private fun loadRoutes() {
        viewModelScope.launch {
            _routes.value = routeRepository.getAllRoutes()
        }
    }
}

/**
 * Composable function displaying the main content of the app.
 *
 * @param modifier Modifier for customizing the layout appearance.
 * @param onPlanRouteClick Lambda invoked when the "Plan Your Route" button is clicked.
 */
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    onPlanRouteClick: () -> Unit
) {
    val routeViewModel = rememberRouteViewModel()
    val routes by routeViewModel.routes.collectAsState(initial = emptyList())

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onPlanRouteClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Text(text = "Plan Your Route")
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                painter = painterResource(id = R.drawable.routes),
                contentDescription = "Route Icon",
                modifier = Modifier.size(20.dp)
            )
        }

        MapSection(title = "Map")
        Spacer(modifier = Modifier.height(24.dp))
        FavoritesSection(routes)
    }
}

/**
 * Creates and remembers a RouteViewModel instance.
 *
 * @return An instance of RouteViewModel initialized with a RouteRepository.
 */
@Composable
fun rememberRouteViewModel(): RouteViewModel {
    val context = LocalContext.current
    val routeDao = AppDatabase.getInstance(context).routeDao()
    val routeRepository = RouteRepository(routeDao)
    return remember { RouteViewModel(routeRepository) }
}

/**
 * Composable function for displaying a map section.
 *
 * @param title The title of the map section.
 */
@Composable
fun MapSection(title: String) {
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }
    val cameraPositionState = rememberCameraPositionState()
    var currentLocation by remember { mutableStateOf<LatLng?>(null) }

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

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 15.sp)
            Icon(
                painter = painterResource(id = R.drawable.map),
                contentDescription = "Map Icon",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            if (currentLocation != null) {
                GoogleMap(
                    modifier = Modifier.matchParentSize(),
                    cameraPositionState = cameraPositionState
                )
            }
        }
    }
}

/**
 * Composable function for displaying a list of favorite routes.
 *
 * @param routes List of routes to be displayed in the favorites section.
 */
@Composable
fun FavoritesSection(routes: List<Route>) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Favorites", fontSize = 15.sp)
            Icon(
                painter = painterResource(id = R.drawable.favorite),
                contentDescription = "Favorites Icon",
                modifier = Modifier.size(24.dp),
                tint = Color.Unspecified
            )
        }

        if (routes.isEmpty()) {
            Text(
                text = "No Favorites available",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items = routes, key = { it.route_id }) { route ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .clickable {
                                val intent = Intent(context, RouteActivity::class.java).apply {
                                    putExtra("ROUTE_ID", route.route_id)
                                }
                                context.startActivity(intent)
                            }
                            .padding(16.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = route.name,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Black
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_share),
                                    contentDescription = "Share Icon",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = route.description,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Preview function for MainContent composable.
 */
@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
    FlavourTrail_v2Theme {
        MainContent(
            onPlanRouteClick = { /* No action in preview */ }
        )
    }
}
