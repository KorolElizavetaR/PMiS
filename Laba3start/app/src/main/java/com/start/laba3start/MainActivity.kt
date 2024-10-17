package com.start.laba3start

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Content1()
            // Content2()
            // Content3()
            // Content4()
            // Content5()
            // Content6()
            // animation()
            // animateCircle()
            // changeColor()
            rotate()
        }
    }
}

@Composable
fun Content1()
{
    val langs = listOf("Kotlin", "Java", "JavaScript", "Python", "C#",
        "C++", "Rust", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
        "o", "p", "q")
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(5.dp)
    ){
        item { Text("Языки программирования", fontSize = 29.sp) }
        itemsIndexed(langs){index,lang -> Text(lang, fontSize = 23.sp,
            modifier=Modifier.background(
                if(index%2==0) Color(0xffdddddd) else Color.Transparent
            ).padding(8.dp).fillMaxWidth())}
    }
}

@Composable
fun Content2()
{
    val langs = listOf(Language("Kotlin", 0xff16a085),
        Language("Java", 0xff2980b9),
        Language("JavaScript", 0xffd35400),
        Language("Python", 0xff2c3e50)
    )
    LazyRow {
        items(langs) { lang ->
            Column(
                Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(Modifier.size(100.dp).background(Color(lang.hexColor)))
                Text(lang.name, fontSize = 24.sp, modifier =
                Modifier.padding(8.dp))
            }
        }
    }
}

@Composable
fun Content3()
{
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(state=listState) {
        item{Text("В конец",
            Modifier.padding(8.dp).background(Color.DarkGray).padding(5.dp).clickable {
                coroutineScope.launch() {
                    listState.animateScrollToItem(28)
                }
            }, fontSize = 28.sp, color = Color.White)
        }
        items(29){
            Text("Item $it", Modifier.padding(8.dp), fontSize = 28.sp)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Content4()
{
    val phones = listOf("Apple iPhone 15 Pro", "Realme 11 PRO",
        "Google Pixel 5", "Samsung Galaxy S24 Ultra", "Google Pixel 6",
        "Samsung Galaxy S21 FE", "Apple iPhone 15 Pro Max", "Xioami Redmi Note 12",
        "Xiaomi Redmi 12",
        "Apple iPhone 13", "Google Pixel 6", "Apple iPhone 14",
        "Realme C30s", "Realme Note 50")
    val groups = phones.groupBy { it.substringBefore(" ") }
    LazyColumn(
        contentPadding = PaddingValues(5.dp)
    ){
        groups.forEach { (brand, models) ->
            stickyHeader {
                Text(
                    text = brand,
                    fontSize = 28.sp,
                    color = Color.White,
                    modifier =
                    Modifier.background(Color.Gray).padding(5.dp).fillMaxWidth()
                )
            }
            items(models) { model ->
                Text(model, Modifier.padding(5.dp), fontSize = 28.sp)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Content5()
{
    val people = listOf(
        Person("Tom", "Microsoft"), Person("Alice", "Microsoft"),
        Person("Bob", "Google"), Person("Sam", "JetBrains"),
        Person("Kate", "Google"), Person("Mark", "Google"),
        Person("Bill", "Microsoft"), Person("Sandra", "JetBrains"),
        Person("Lisa", "Apple"), Person("Alex", "Apple")
    )
// создаем группы
    val groups = people.groupBy { it.company }
    LazyColumn(
        contentPadding = PaddingValues(5.dp)
    ){
        groups.forEach { (company, employees) ->
            stickyHeader {
                Text(text = company,
                    fontSize = 28.sp,
                    color = Color.White,
                    modifier =
                    Modifier.background(Color.Gray).padding(5.dp).fillMaxWidth()
                )
            }
            items(employees) { employee ->
                Text(employee.name, Modifier.padding(5.dp), fontSize =
                28.sp)
            }
        }
    }
}

@Composable
fun Content6()
{
    Column (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {
        Image(
            painter = BitmapPainter(ImageBitmap.imageResource(R.drawable.capybara)),
            contentScale = ContentScale.Crop,
            contentDescription = "capybara",
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(15))
        )
    }
}

@Composable
fun animation()
{
    val startOffset = 0
    val boxWidth = 150
    val endOffset = LocalConfiguration.current.screenWidthDp-boxWidth

    var boxState by remember { mutableStateOf(startOffset) }
    val offset by animateDpAsState(targetValue = boxState.dp)
    Column(Modifier.fillMaxWidth()) {
        Box(Modifier.padding(start=offset)
            .size(boxWidth.dp)
            .background(Color.DarkGray))
        Button ({boxState = if (boxState==startOffset){endOffset} else {startOffset}},
            Modifier.padding(10.dp)) {Text("Move", fontSize = 25.sp) }
    }
}

@Composable
fun animateCircle()
{
    val circleHeight = 150
    val startOffset = 10 // начальный отступ
    val endOffset = LocalConfiguration.current.screenHeightDp - circleHeight
    var circleOffset by remember { mutableStateOf(startOffset)}
    val offset: Dp by animateDpAsState(
        targetValue = circleOffset.dp,
        animationSpec =if (circleOffset==endOffset) {
            spring(dampingRatio = Spring.DampingRatioHighBouncy) // сильный отскок
        }
        else {
            spring(dampingRatio = Spring.DampingRatioNoBouncy) // отсутствие отскока
        }
    )
    Row (Modifier.fillMaxSize()) {
        Button({circleOffset =
            if (circleOffset==startOffset) endOffset else startOffset },
            Modifier.padding(10.dp)) {Text("Start", fontSize = 22.sp)}
        Box(Modifier.padding(top=offset)
            .size(circleHeight.dp)
            .clip(CircleShape)
            .background(Color.DarkGray))
    }
}

@Composable
fun changeColor()
{
    var colorState by remember { mutableStateOf(Color.DarkGray) }
    val animatedColor: Color by animateColorAsState(
        targetValue = colorState,
        animationSpec = keyframes {
            durationMillis = 3000
            Color.Blue at 500
            Color.Green at 1500
            Color.Yellow at 2500
        }
    )
    Column(Modifier.fillMaxWidth(),
        horizontalAlignment= Alignment.CenterHorizontally) {
        Box(Modifier.padding(20.dp)
            .size(200.dp)
            .background(animatedColor))
        Button(
            {colorState = if (colorState == Color.Red) {Color.DarkGray}
            else {Color.Red}},
            Modifier.padding(10.dp))
        {
            Text("Изменить цвет", fontSize = 22.sp)
        }
    }
}

@Composable
fun rotate()
{
    var rotated by remember { mutableStateOf(false) }
    val angle by animateFloatAsState(
        targetValue = if (rotated) 360f else 0f,
        animationSpec = tween(4000)
    )
    Column(Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.padding(20.dp)
            .size(200.dp)
                .background(Color.LightGray)
                .rotate(angle),
            Alignment.Center){
            Box(Modifier.size(width=200.dp,height=20.dp)
                .background(Color.DarkGray))}
        Button({rotated = !rotated},
            Modifier.padding(10.dp)) {
            Text(text = "Повернуть")
        }
    }
}

data class Language(val name:String, val hexColor: Long)
data class Person(val name:String, val company: String)