package com.start.laba4

import android.content.pm.PackageManager
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Picks()
        }
    }
}

@Composable
fun Picks(modifier: Modifier = Modifier) {
    var hasImage by rememberSaveable { mutableStateOf(false) }
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var currentUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            hasImage = uri != null
            imageUri = uri
        }
    )

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage = success
            if (success) {
                imageUri = currentUri
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                Toast.makeText(context, "Разрешение получено", Toast.LENGTH_SHORT).show()
                currentUri?.let { cameraLauncher.launch(it) }
            } else {
                Toast.makeText(context, "В разрешении отказано", Toast.LENGTH_SHORT).show()
            }
        }
    )
    if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
    {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(250.dp)
                    .padding(end = 16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Button(onClick = { imagePicker.launch("image/*") }) {
                    Text(text = "Выбрать изображение")
                }
                Button(
                    modifier = Modifier.padding(top = 16.dp),
                    onClick = {
                        currentUri = ComposeFileProvider.getImageUri(context)
                        val permissionCheckResult = ContextCompat.checkSelfPermission(
                            context,
                            android.Manifest.permission.CAMERA
                        )
                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(currentUri!!)
                        } else {
                            permissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                    }
                ) {
                    Text(text = "Сделать снимок")
                }
            }
            if (hasImage && imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    contentDescription = "Selected Image"
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))  // Empty space if no image
            }
        }
    }
    else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { imagePicker.launch("image/*") }) {
                Text(text = "Выбрать изображение")
            }
            Button(
                modifier = Modifier.padding(top = 16.dp),
                onClick = {
                    currentUri = ComposeFileProvider.getImageUri(context)
                    val permissionCheckResult = ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.CAMERA
                    )
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(currentUri!!)
                    } else {
                        permissionLauncher.launch(android.Manifest.permission.CAMERA)
                    }
                }
            ) {
                Text(text = "Сделать снимок")
            }
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            if (hasImage && imageUri != null) {
                AsyncImage(
                    model = imageUri,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = true),
                    contentDescription = "Selected Image"
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}
