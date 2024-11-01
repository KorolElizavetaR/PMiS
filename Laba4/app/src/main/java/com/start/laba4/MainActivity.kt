package com.start.laba4

import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    var hasImage by remember {mutableStateOf(false) }
    var imageUri by remember {mutableStateOf<Uri?>(null)}
    var currentUri by remember {mutableStateOf<Uri?>(null)}
    val context = LocalContext.current
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult={ uri: Uri? ->
            hasImage = uri != null
            imageUri = uri}
    )
    Box( modifier = modifier) {
        if (hasImage && imageUri != null) {
            AsyncImage(
                model = imageUri,
                modifier = Modifier.fillMaxWidth(),
                contentDescription = "Selected Image",
            )
        }
        Column(modifier=Modifier.align(Alignment.BottomCenter).padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,) {
            Button(onClick = { imagePicker.launch("image/*") },) {
                Text(text = "Выбрать изображение")
            }
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
                onResult ={ isGranted ->
                    if (isGranted) {
                        Toast.makeText(context, "Разрешение получено", Toast.LENGTH_SHORT).show()
                        currentUri?.let { cameraLauncher.launch(it) }
                    } else {
                        Toast.makeText(context,"В разрешении отказано",Toast.LENGTH_SHORT).show()
                    }
                }
            )
            Button(modifier = Modifier.padding(top = 16.dp),
                onClick = { currentUri = ComposeFileProvider.getImageUri(context)
                    val permissionCheckResult = ContextCompat.checkSelfPermission(context,android.Manifest.permission.CAMERA)
                    if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                        cameraLauncher.launch(currentUri!!) }
                    else {permissionLauncher.launch(android.Manifest.permission.CAMERA) }
                },
            ){
                Text(text = "Сделать снимок")
            }
        }
    }
}
