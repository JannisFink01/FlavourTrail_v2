package com.example.flavourtrail_v2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flavourtrail_v2.ui.theme.FlavourTrail_v2Theme
import com.example.flavourtrail_v2.ui.TopBar

class DetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            FlavourTrail_v2Theme {
                DetailScreen()
            }
        }
    }
}

@Composable
fun DetailScreen() {
    // Create a scrollable state for vertical scrolling
    val scrollState = rememberScrollState() // State to control scrolling
    Scaffold(
        topBar = {
            TopBar(
                userName = "Max Mustermann", // Beispiel-Benutzername
                profileImageRes = R.drawable.profile_user
            )
        },
        bottomBar = {
            NavigationBar{
                BottomNavigationBar()
            }
        }
    )
    { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(innerPadding)
//                .padding(horizontal = 0.dp)
//        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(scrollState) // Enable scrolling
            ) {
                InteractionBar()
                ImageSection()
                TitleSection(modifier = Modifier.padding(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    StarRatingSection()
                    PriceInformationSection()
                }
                Box(
                    Modifier.align(Alignment.Start)
                ) {
                    ViewReviewsButton()
                }
                BookNowButton("Book Now")
                Row(modifier = Modifier.padding(16.dp)) {
                    TimeInformationSection()
                    Spacer(modifier = Modifier.width(16.dp))
                    RateDestinationButton()
                }
                DetailSection()
            }
        //}
    }
}


@Composable
fun BottomNavigationBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Absolute.Center
    ) {
        Row() {
            val stopCounter = 1
            Icon(
                imageVector = Icons.Filled.KeyboardArrowLeft, // Use a built-in Material Icon
                contentDescription = "Stop Count", // Provide a description for accessibility
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* Handle click */ }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text= "$stopCounter/5")
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight, // Use a built-in Material Icon
                contentDescription = "Stop Count", // Provide a description for accessibility
                modifier = Modifier
                    .size(24.dp)
                    .clickable { /* Handle click */ }
            )
        }
    }
}

//Definitionen der einzelnen Composables
@Composable
fun InteractionBar(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Use a built-in Material Icon
            contentDescription = "ArrowBack Icon",
            modifier = Modifier
                .size(48.dp) // Optional: Size of the icon
                .clickable { /* Handle click */ }
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.Filled.Share, // Use a built-in Material Icon
            contentDescription = "Share Icon",
            modifier = Modifier.size(48.dp) // Optional: Size of the icon
        )
        HeartToggleIcon(
            iconSize = 48.dp,
            contentDescription = "Toggle Heart Icon"
        )
    }
}

@Composable
fun ImageSection() {
    Image(
        painter = painterResource(id = R.drawable.destination_placeholder),
        contentDescription = "Image",
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TitleSection(modifier: Modifier = Modifier) {
    Text(
        text = "Title",
        fontSize = 24.sp,
        modifier = modifier
    )
}

@Composable
fun StarRatingSection(modifier: Modifier = Modifier) {
    Text(
        text = "Star Rating",
        fontSize = 20.sp,
        color = Color.Yellow,
        modifier = modifier
    )
}

@Composable
fun PriceInformationSection(modifier: Modifier = Modifier) {
    Text(
        text = "35,99€",
        fontSize = 20.sp,
        modifier = modifier
    )
}

@Composable
fun ViewReviewsButton(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Star Icon",
            modifier = Modifier.size(20.dp)
        )
        TextAsButton(
            text = "View Reviews",
            onClick = { /* Handle button click */ }
        )
    }
}

@Composable
fun BookNowButton(buttonText: String) {
    Button(
        onClick = { /* Handle button click */ },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            //containerColor = Color.Blue,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = buttonText,
            fontSize = 20.sp
        )
    }
}

@Composable
fun TimeInformationSection(){
    Column{
        Row{
            Icon(
                painter = painterResource(id = R.drawable.clock_icon), // Use a built-in Material Icon
                contentDescription = "Clock Icon", // Provide a description for accessibility
                modifier = Modifier.size(24.dp), // Adjust size if needed
                tint = Color.Black // Optional: Set a tint color
            )
            Text(
                text = "Opening Hours",
                fontSize = 20.sp
            )
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.hourglass_icon), // Use a custom icon
                contentDescription = "Hourglass Icon", // Provide a description for accessibility
                modifier = Modifier.size(24.dp), // Adjust size if needed
            )
            Text(
                text = "Time spent",
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun RateDestinationButton() {
    Button(
        onClick = { /* Handle button click */ },
        modifier = Modifier.width(200.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.LightGray,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Star, // Use a built-in Material Icon
            contentDescription = "Star Icon", // Provide a description for accessibility
            modifier = Modifier.size(24.dp), // Adjust size if needed
            tint = Color.DarkGray // Optional: Set a tint color
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Rate Destination",
            fontSize = 20.sp
        )
    }
}

@Composable
fun DetailSection() {


    // Box to hold the content with a background color
    Box(
        modifier = Modifier
            .fillMaxSize() // Occupies the entire screen
            .background(Color.LightGray) // Background color
    ) {
        // Text content inside the Box
        Text(
            text = """
                Here is some interesting text, which describes the destination 
                and makes the user want to visit it. Lorem ipsum dolor sit amet, 
                consectetur adipiscing elit. Curabitur porttitor lorem at odio 
                efficitur, id efficitur lorem consectetur. Phasellus bibendum 
                nisl eu ex gravida, sit amet fermentum nisl suscipit.
                
                Add as much text as you like here. The box will scroll if content exceeds the screen height.
            """.trimIndent(),
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp) // Padding around the text
        )
    }
}

@Composable
fun TextAsButton(
    onClick: () -> Unit,
    text: String = "Clickable Text",
    fontSize: androidx.compose.ui.unit.TextUnit = 20.sp
) {
    Text(
        text = text,
        fontSize = fontSize,
        modifier = Modifier.clickable {
            onClick()  // Trigger the navigation or action
        }
    )
}

@Composable
fun HeartToggleIcon(
    iconSize: Dp = 48.dp, // Parameter to control the size of the icon
    contentDescription: String = "Toggle Heart Icon" // Parameter for accessibility
){
    // MutableState to track the heart's state
    var isHeartSelected by remember { mutableStateOf(false) }

    // Clickable Icon
    Icon(
        imageVector = if (isHeartSelected) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
        contentDescription = contentDescription, // Use parameter
        modifier = Modifier
            .size(iconSize) // Use parameter for size
            .clickable { isHeartSelected = !isHeartSelected }, // Toggle the state on click
        tint = if (isHeartSelected) Color.Red else Color.Black // Optional: Change tint color
    )
}