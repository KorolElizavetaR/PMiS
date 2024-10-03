package com.start.laba2start

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable //вот тут мб проблемы
import com.start.laba2start.ui.theme.Laba2StartTheme
import androidx.navigation.compose.NavHost // и тут

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Greeting()
        }
    }
}

@Composable
fun Greeting() {
    val navController = rememberNavController()
    Column(Modifier.padding(8.dp)) {
        NavBar(navController = navController)
        NavHost(navController, startDestination = NavRoutes.Home.route) {
            composable(NavRoutes.Home.route) { Home() }
            composable(NavRoutes.Contacts.route) { Contacts() }
            composable(NavRoutes.About.route) { About() }
        }
    }
}

@Composable
fun NavBar(navController: NavController){
    Row(
        Modifier.fillMaxWidth().padding(bottom = 8.dp)){
        Text("Home",
            Modifier
                .weight(0.33f)
                .clickable { navController.navigate(NavRoutes.Home.route) },
            fontSize = 22.sp, color= Color(0xFF6650a4)
        )
        Text("Contacts",
            Modifier
                .weight(0.33f)
                .clickable { navController.navigate(NavRoutes.Contacts.route) },
            fontSize = 22.sp, color= Color(0xFF6650a4))
        Text("About",
            Modifier
                .weight(0.33f)
                .clickable { navController.navigate(NavRoutes.About.route) },
            fontSize = 22.sp, color= Color(0xFF6650a4))
    }
}

@Composable
fun Home(){
    Text("Home Page", fontSize = 30.sp)
}
@Composable
fun Contacts(){
    Text("Contact Page", fontSize = 30.sp)
}
@Composable
fun About(){
    Text("About Page", fontSize = 30.sp)
}
sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Contacts : NavRoutes("contact")
    object About : NavRoutes("about")
}
