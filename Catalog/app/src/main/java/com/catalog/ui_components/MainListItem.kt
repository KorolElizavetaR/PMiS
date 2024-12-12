package com.catalog.ui_components

import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.catalog.MainViewModel
import com.catalog.utils.ListItem

/**
 *  Выбирает расположение элементов при обоих ориентациях
 */
@Composable
fun MainListItemRows(
    items: List<ListItem>,
    modifier: Modifier = Modifier,
    onClick: (ListItem) -> Unit,
) {
    val configuration = LocalConfiguration.current
    val columnCount = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 4

    LazyVerticalGrid(
        columns = GridCells.Fixed(columnCount),
        modifier = modifier
    ) {
        items(items) { item ->
            MainListItem(item = item, onClick = onClick)
        }
    }
}

/**
 *      Отвечает за отображение элементов пункта в главном меню.
 *
 *      При вертикальном отображении располагает книги в два ряда
 *      При горизонтальном отображении располагает книги в 4 рядf
 *
 */
@Composable
fun MainListItem(
    mainViewModel: MainViewModel = hiltViewModel(),
    item: ListItem,
    onClick: (ListItem) -> Unit
) {
   // val backgroundColor = if (isSystemInDarkTheme()) Color(MaterialTheme.colorScheme.background) else Color.White
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(5.dp)
            .clickable {
                onClick(item)
            },
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        shape = RectangleShape,
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary)
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter
        ) {
        }

        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (image, text, fav) = createRefs()
            AssetImage(
                imageName = item.imageName,
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
            )
            Text(
                text = item.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(5.dp)
                    .constrainAs(text) {
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
            IconButton(onClick = {
                mainViewModel.insertItem(item.copy(isfav = !item.isfav))
            },
                modifier = Modifier.constrainAs(fav) {
                    top.linkTo(parent.top)
                    end.linkTo(parent.end)
                })
            {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    tint = if (item.isfav) MaterialTheme.colorScheme.primary else Gray,
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.tertiary)
                        .padding(5.dp)
                )
            }
        }
    }
}

/**
 *      Получаем картинки из папки Assets.
 */
@Composable
fun AssetImage(
    imageName: String, contentDescription: String, modifier: Modifier
) {
    val context = LocalContext.current
    val assetManager = context.assets
    val inputStream = assetManager.open(imageName)
    val bitMap = BitmapFactory.decodeStream(inputStream)
    Image(
        bitmap = bitMap.asImageBitmap(),

        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}