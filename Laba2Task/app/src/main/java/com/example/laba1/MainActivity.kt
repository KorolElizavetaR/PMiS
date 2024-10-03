package com.example.laba1


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.Button
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.laba1.ui.theme.Laba1Theme
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.compose.foundation.lazy.items

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laba1Theme {
                Greeting()
            }
        }
    }
}

@Composable
fun Greeting() {
    val navController = rememberNavController()
    Column(Modifier.padding(8.dp)) {
        NavHost(
            navController, startDestination = NavRoutes.Home.route, modifier
            = Modifier.weight(1f)
        ) {
            composable(NavRoutes.Home.route) { Home(modifier = Modifier.padding(8.dp)) }
            composable(NavRoutes.About.route) { About() }
        }
        BottomNavigationBar(navController = navController)
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        NavBarItems.BarItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.image,
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(text = navItem.title)
                }
            )
        }
    }
}

object NavBarItems {
    val BarItems = listOf(
        BarItem(
            title = "Главная",
            image = Icons.Filled.Home,
            route = "home"
        ),
        BarItem(
            title = "Что тут?",
            image = Icons.Filled.Info,
            route = "about"
        )
    )
}

data class BarItem(
    val title: String,
    val image: ImageVector,
    val route: String
)

@Composable
fun Home(modifier: Modifier = Modifier) {
    Column (modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        val MyName = stringResource(id = R.string.myName)
        val emptyValue = stringResource(id = R.string.emptyVal)

        var TextObject by rememberSaveable  { mutableStateOf("") }
        Text(
            text = TextObject,
            modifier = modifier
        )
        Row {
            Button(
                onClick =
                {
                    TextObject = "i am $MyName"
                }
            ) {
                Text(text = stringResource(id = R.string.mynameButton))
            }
            Button(
                onClick =
                {
                    TextObject = emptyValue
                }
            ) {
                Text(text = stringResource(id = R.string.XButton))
            }
        }
    }
    Column (modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom){
        Image(
            painter = painterResource(id = R.drawable.cat),
            contentDescription = "Cat",
            modifier = modifier
                .padding(horizontal = 4.dp)
                .padding(vertical = 4.dp)
        )
    }
}

@Composable
fun About() {
    val navController = rememberNavController()
    val verses = listOf(
        Poem(1, stringResource(id = R.string.author1), stringResource(id = R.string.verse1)),
        Poem(2, stringResource(id = R.string.author2), stringResource(id = R.string.verse2))
    )
    NavHost(navController, startDestination = VerseRoutes.Authors.route) {
        composable(VerseRoutes.Authors.route) { Author(verses, navController) }
        composable(VerseRoutes.Verses.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }))
        {
                stackEntry ->
            val userId = stackEntry.arguments?.getInt("id")
            Verse(userId, verses)
        }
    }
}

sealed class VerseRoutes(val route: String) {
    object Authors : VerseRoutes("author")
    object Verses : VerseRoutes("verse")
}

@Composable
fun Author(data: List<Poem>, navController: NavController){
    LazyColumn {
        items(data){
                u->
            Row(Modifier.fillMaxWidth()){
                Text(u.authorandname,
                    Modifier.padding(8.dp).clickable {
                        navController.navigate("verse/${u.id}") },
                    fontSize = 28.sp)
            }
        }
    }
}

@Composable
fun Verse(id:Int?, data: List<Poem>){
    val verse = data.find { it.id==id }
    if(verse!=null) {
        Column {
            Text("${verse.verse}", Modifier.padding(8.dp), fontSize = 20.sp)
        }
    }
    else{
        Text("Poem Not Found")
    }
}

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object About : NavRoutes("about")
}

data class Poem(val id: Int, val authorandname:String, val verse:String)