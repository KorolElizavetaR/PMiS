package com.example.laba1


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp

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
        NotBottomNavigationBar(navController = navController)
        NavHost(
            navController, startDestination = NavRoutes.Home.route, modifier
            = Modifier.weight(1f)
        ) {
            composable(NavRoutes.Home.route) { Home(modifier = Modifier.padding(8.dp)) }
            composable(NavRoutes.Poetry.route) { Poetry() }
            composable(NavRoutes.ADHD.route) { GetReal() }
            composable(NavRoutes.Capybara.route) { Capybara() }
        }

    }
}

@Composable
fun GetReal()
{
    var imageState by rememberSaveable { mutableStateOf(R.drawable.gordon) }
    Column(
        Modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            Modifier.padding(20.dp)
                .size(300.dp)
        ) {
            Crossfade (targetState = imageState) { currentImage ->
                Image(
                    painter = painterResource(id = currentImage),
                    contentDescription = "Get real!!",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Button(
            onClick = {
                imageState = if (imageState == R.drawable.gordon) R.drawable.waltuh else R.drawable.gordon
            },
            Modifier.padding(10.dp)
        ) {
            Text("get real", fontSize = 22.sp)
        }
    }
}

@Composable
fun Capybara()
{
    val startOffset = 0
    val pictureSize = 200
    val endOffset = LocalConfiguration.current.screenWidthDp - pictureSize

    var capybaraState by rememberSaveable { mutableStateOf(startOffset)}

    val offset by animateDpAsState(targetValue = capybaraState.dp)
    Column(Modifier.fillMaxWidth()) {
        Box(Modifier.padding(start=offset).size(pictureSize.dp)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.capybara),
                contentDescription = "capybrr"
            )
        }
        Button ({capybaraState = if (capybaraState==startOffset){endOffset} else {startOffset}},
            Modifier.padding(10.dp)) {Text("brrrr", fontSize = 25.sp) }
    }
}


@Composable
fun NotBottomNavigationBar(navController: NavController) {
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
            route = NavRoutes.Home.route
        ),
        BarItem(
            title = "Стишки!!",
            image = Icons.Filled.Info,
            route = NavRoutes.Poetry.route
        ),
        BarItem(
            title = "Get real",
            image = Icons.Filled.Menu,
            route = NavRoutes.ADHD.route
        ),
        BarItem(
            title = "Капибара",
            image = Icons.Filled.FavoriteBorder,
            route = NavRoutes.Capybara.route
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
fun Poetry() {
    val navController = rememberNavController()

        var id = 1;
        val verses = listOf(
            Poem(id++, stringResource(id = R.string.author1), "Совесть", stringResource(id = R.string.verse1)),
            Poem(id++, stringResource(id = R.string.author1), "Жук", stringResource(id = R.string.verse2)),
            Poem(id++, stringResource(id = R.string.author3), "Студенты", stringResource(id = R.string.verse3)),
        )
    NavHost(navController, startDestination = NavRoutes.Poetry.route) {
        composable(NavRoutes.Poetry.route) { VerseListScreen(navController, verses) }
        composable(NavRoutes.Verses.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType }))
        {
                stackEntry ->
            val userId = stackEntry.arguments?.getInt("id")
            Verse(userId, verses)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun VerseListScreen(navController: NavController, poems: List<Poem>) {
    val groupPoems = poems.groupBy { it.author }

    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        groupPoems.forEach { (author, verses) ->
            stickyHeader {
                Text(
                    text = author,
                    fontSize = 28.sp,
                    color = Color.White,
                    modifier = Modifier
                        .background(Color.Gray)
                        .padding(5.dp)
                        .fillMaxWidth()
                )
            }
            items(verses) { verse ->
                Text(
                    text = verse.name,
                    fontSize = 28.sp,
                    modifier = Modifier
                        .padding(5.dp)
                        .clickable {
                            navController.navigate(NavRoutes.Verses.route+"/${verse.id}")
                        }
                )
            }
        }
    }
}

@Composable
fun Verse(id:Int?, data: List<Poem>){
    val verse = data.find { it.id==id }
    if(verse!=null) {
        Column(modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState())) {
            Text(text = verse.name, fontSize = 32.sp)
            Text(text = verse.verse, fontSize = 20.sp)
        }
    }
    else{
        Text("Poem Not Found")
    }
}

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("home")
    object Poetry : NavRoutes("poetry")
    object Verses : NavRoutes("verse")
    object ADHD : NavRoutes("adhd")
    object Capybara : NavRoutes("capybere")
}

data class Poem(val id: Int, val author:String, val name:String, val verse:String)