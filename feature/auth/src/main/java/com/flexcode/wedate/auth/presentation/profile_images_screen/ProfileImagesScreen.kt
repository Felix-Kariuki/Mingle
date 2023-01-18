package com.flexcode.wedate.auth.presentation.profile_images_screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.flexcode.wedate.auth.data.models.User
import com.flexcode.wedate.auth.domain.model.Response
import com.flexcode.wedate.auth.domain.model.Response.Success
import com.flexcode.wedate.common.R
import com.flexcode.wedate.common.composables.BasicButton
import com.flexcode.wedate.common.composables.ScreenTitlesText
import com.flexcode.wedate.common.ext.basicButton
import com.flexcode.wedate.common.ext.fieldModifier

@Composable
fun ProfileImagesScreen(
    modifier: Modifier = Modifier,
    openAndPopUp: (String,String) -> Unit,
    viewModel: ProfileImagesViewModel = hiltViewModel()
) {

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        ScreenTitlesText(
            text = R.string.profile_image,
            modifier = modifier.align(Alignment.CenterHorizontally)
        )

        Row(
            modifier = modifier
                .fillMaxWidth()
                .fieldModifier(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0..2){
                val clicked = "profileImage1"
                when(i){
                    0 -> "profileImage1"
                    1 -> "profileImage2"
                    2 -> "profileImage3"
                }
                ClickableToGalleryImage(clicked,viewModel)
            }
        }

        Row(
            modifier = modifier
                .fillMaxWidth()
                .fieldModifier(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (i in 0..2){
                val clicked = "profileImage4"
                when(i){
                    0 -> "profileImage4"
                    1 -> "profileImage5"
                    2 -> "profileImage6"
                }
                ClickableToGalleryImage(clicked,viewModel)

            }
        }


        Row(
            modifier = modifier
                .weight(1f)
                .padding(bottom = 30.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            BasicButton(
                text = R.string.register,
                modifier = modifier
                    .basicButton()
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                viewModel.onContinueClicked(openAndPopUp)
            }
        }

        AddImageToStorage(addImageToDatabase = {
            viewModel.addImageToFirestoreDatabase(it, User())
        })

    }

}


@Composable
fun AddImageToStorage(
    viewModel: ProfileImagesViewModel = hiltViewModel(),
    addImageToDatabase: (downloadUrl: Uri) -> Unit
) {
    when(val addImageToStorageResponse = viewModel.addImageToStorageResponse) {
        //is Loading -> ProgressBar()
        is Success -> addImageToStorageResponse.data?.let { downloadUrl ->
            LaunchedEffect(downloadUrl) {
                addImageToDatabase(downloadUrl)
            }
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            print(addImageToStorageResponse.e)
        }
        else -> {}
    }
}

@Composable
fun ClickableToGalleryImage(clicked: String, viewModel: ProfileImagesViewModel) {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        println(imageUri)
        imageUri = uri
        imageUri?.let { viewModel.addImageToStorage(it,clicked) }
    }
    Box(
        modifier = Modifier.clip(shape = CircleShape)
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = imageUri)
                    .placeholder(R.drawable.ic_add)
                    .build()
            ),
            contentDescription = null,
            modifier = Modifier
                .clickable { launcher.launch("image/*") }
                .size(100.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}

