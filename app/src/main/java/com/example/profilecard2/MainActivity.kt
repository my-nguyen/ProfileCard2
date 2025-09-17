package com.example.profilecard2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.compose.AsyncImage
import com.example.profilecard2.ui.theme.ProfileCard2Theme
import com.example.profilecard2.ui.theme.lightGreen
import com.example.profilecard2.ui.theme.teal

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProfileCard2Theme {
                Application()
            }
        }
    }
}

@Composable
fun Application(profiles: List<UserProfile> = userProfiles) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main_screen") {
        composable("main_screen") {
            MainScreen(profiles, navController)
        }
        composable(
            route = "detail_screen/{userId}",
            arguments = listOf(navArgument("userId") { type = NavType.IntType })
        ) { backstackEntry ->
            DetailScreen(backstackEntry.arguments!!.getInt("userId"))
        }
    }
}

@Composable
fun MainScreen(profiles: List<UserProfile>, navController: NavHostController?) {
    Scaffold(topBar = { AppBar() }) { padding ->
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
            LazyColumn {
                items(items = profiles) { profile ->
                    ProfileCard(profile = profile) {
                        navController?.navigate("detail_screen/${profile.id}")
                    }
                }
            }
        }
    }
}

@Composable
fun DetailScreen(userId: Int) {
    val profile = userProfiles.first { it.id == userId }
    Scaffold(topBar = { AppBar() }) { padding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                ProfilePicture(profile.imageUrl, profile.isOnline, 240.dp)
                ProfileContent(profile.name, profile.isOnline, Alignment.CenterHorizontally)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar() {
    TopAppBar(
        title = { Text("User profiles") },
        navigationIcon = {
            Icon(
                Icons.Default.Home,
                contentDescription = "Home",
                Modifier.padding(horizontal = 12.dp)
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = teal, // Set the background color of the TopAppBar
            titleContentColor = Color.White, // Set the color of the title text
            actionIconContentColor = Color.White, // Set the color of action icons
            navigationIconContentColor = Color.White // Set the color of the navigation icon
        )
    )
}

@Composable
fun ProfileCard(profile: UserProfile, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(top = 4.dp, bottom = 4.dp, start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .clickable(onClick = { onClick.invoke() }),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = CutCornerShape(topEnd = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture(profile.imageUrl, profile.isOnline, 72.dp)
            ProfileContent(profile.name, profile.isOnline, Alignment.Start)
        }
    }
}

@Composable
fun ProfilePicture(imageUrl: String, isOnline: Boolean, size: Dp) {
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 2.dp,
            color = if (isOnline) MaterialTheme.colorScheme.lightGreen else Color.Red
        ),
        modifier = Modifier
            .padding(16.dp)
            .size(size),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // this doesn't load images
        AsyncImage(
            model = imageUrl,
            contentDescription = "Profile image",
        )
    }
}

@Composable
fun ProfileContent(username: String, isOnline: Boolean, alignment: Alignment.Horizontal) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = alignment
    ) {
        Text(
            text = username, style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.alpha(if (isOnline) 1f else 0.5f)
        )
        Text(
            text = if (isOnline) "Active now" else "Offline",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(0.5f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailPreview() {
    ProfileCard2Theme {
        DetailScreen(userId = 0)
    }
}

@Preview(showBackground = true)
@Composable
fun MainPreview() {
    ProfileCard2Theme {
        MainScreen(userProfiles, null)
    }
}