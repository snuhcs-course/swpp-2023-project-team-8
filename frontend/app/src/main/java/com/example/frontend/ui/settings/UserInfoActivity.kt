package com.example.frontend.ui.settings

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.frontend.MissionActivity
import com.example.frontend.R
import com.example.frontend.data.predefinedImages
import com.example.frontend.repository.UserContextRepository
import com.example.frontend.ui.component.CustomButton
import com.example.frontend.ui.component.MyTopAppBar
import com.example.frontend.ui.login.LoginActivity
import com.example.frontend.ui.settings.component.MissionCard
import com.example.frontend.ui.theme.FrontendTheme
import com.example.frontend.usecase.ChangeImageIdUseCase
import com.example.frontend.usecase.LogOutUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class UserInfoActivity : ComponentActivity() {
    @Inject
    lateinit var changeImageIdUseCase: ChangeImageIdUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserInfoUI(onImageIdChanged = { changeImageIdUseCase.execute(it) })
                }
            }
        }
    }
}

@Composable
fun UserInfoUI(onImageIdChanged: suspend (imageId: Int) -> Unit = {}) {
    var context = LocalContext.current
    var selectedPredefinedImage by rememberSaveable { mutableStateOf(0) }
    selectedPredefinedImage = UserContextRepository.ofContext(context).getSelectedPredefinedImage() ?: 0
    var showDialog by rememberSaveable { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    val currentUserName = UserContextRepository.ofContext(context).getUserName() ?: "DefaultName"
    if (showDialog) {
        ProfileEditDialog(
            currentName = currentUserName,
            onClose = { showDialog = false },
            onProfileUpdated = { newName ->
                UserContextRepository.ofContext(context).saveUserName(newName)
            }
        )
    }
    fun showImageSelectionDialog() {
        val builder = AlertDialog.Builder(context)
        val inflater = LayoutInflater.from(context)

        val dialogView = inflater.inflate(R.layout.dialog_image, null)
        builder.setView(dialogView)

        val alertDialog = builder.create()

        val imageGrayCat = dialogView.findViewById<ImageView>(R.id.imageGrayCat)
        val imageYellowCat = dialogView.findViewById<ImageView>(R.id.imageYellowCat)
        val imageHam = dialogView.findViewById<ImageView>(R.id.imageHam)
        val imageDog = dialogView.findViewById<ImageView>(R.id.imageDog)

        val images = listOf(imageGrayCat, imageYellowCat, imageHam, imageDog)
        val predefinedImages = listOf(R.drawable.cat, R.drawable.cat_sunglass, R.drawable.dog_sunglass, R.drawable.hamster)

        images.forEachIndexed { index, imageView ->
            imageView.setImageResource(predefinedImages[index])
            imageView.setOnClickListener {
                selectedPredefinedImage = predefinedImages[index]
                UserContextRepository.ofContext(context).saveSelectedPredefinedImage(selectedPredefinedImage)

                coroutineScope.launch {
                    withContext(Dispatchers.IO) {
                        onImageIdChanged(index)
                    }
                }

                alertDialog.dismiss()
            }
        }

        alertDialog.show()
    }

//    fun showImageSelectionDialog() {
//        val builder = AlertDialog.Builder(context)
//        val inflater = LayoutInflater.from(context)
//
//        val dialogView = inflater.inflate(R.layout.dialog_image, null)
//        builder.setView(dialogView)
//
//        val alertDialog = builder.create()
//
//        val imageGrayCat = dialogView.findViewById<ImageView>(R.id.imageGrayCat)
//        val imageYellowCat = dialogView.findViewById<ImageView>(R.id.imageYellowCat)
//        val imageHam = dialogView.findViewById<ImageView>(R.id.imageHam)
//        val imageDog = dialogView.findViewById<ImageView>(R.id.imageDog)
//
//        imageGrayCat.setOnClickListener {
//            selectedPredefinedImage = predefinedImages[0]  // Adjust as needed
//            UserContextRepository.ofContext(context).saveSelectedPredefinedImage(selectedPredefinedImage)
//
//            coroutineScope.launch {
//                withContext(Dispatchers.IO) {
//                    onImageIdChanged(0)  // Adjust as needed
//                }
//            }
//
//            alertDialog.dismiss()
//        }
//
//
//        alertDialog.show()
//    }


    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        MyTopAppBar(
            title = "내 정보",
            actions = {
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = null,
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 50.dp)
                .padding(top = 100.dp)
                .padding(end = 30.dp)

        ) {
            if (selectedPredefinedImage != 0) {
                selectedPredefinedImage.let { image ->
                    Image(
                        painter = painterResource(id = image ?: 0),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .clickable {
                                showImageSelectionDialog()
                            }
                    )
                }
            } else {
                Icon(
                    imageVector = Icons.Outlined.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            showImageSelectionDialog()
                        }
                )
            }

            Icon(// 프로필 사진 수정
                imageVector = Icons.Outlined.AddCircle,
                contentDescription = null,
                tint = Color(0xFF6750A4),
                modifier = Modifier
                    .size(30.dp)
                    .offset(x = 60.dp, y = (-90).dp)
                    .clickable {
                        showImageSelectionDialog()
                    }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "이름",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        letterSpacing = 0.1.sp,
                    ),
                    modifier = Modifier.padding(start = 30.dp)
                )

                Text(
                    text = UserContextRepository.ofContext(context).getUserName() ?: "김샤프",
                    //text = "김사프",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    ),
                    modifier = Modifier.padding(start = 93.dp)
                )
            }
            Row() {
                Text(
                    text = "이메일",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        letterSpacing = 0.1.sp,
                    ),
                    modifier = Modifier.padding(start = 30.dp)
                )

                Text(
                    text = UserContextRepository.ofContext(context).getUserMail() ?: "sha@snu.ac.kr",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    ),
                    modifier = Modifier.padding(start = 80.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clickable {
                        val nextIntent = Intent(context, MissionActivity::class.java)
                        context.startActivity(nextIntent)
                    }
            ) {
                MissionCard()
            }

            CustomButton(
                buttonText = "로그아웃",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 30.dp),
                onClickHandler = {
                    LogOutUseCase(context).execute()
                    val nextIntent = Intent(context, LoginActivity::class.java)
                    context.startActivity(nextIntent)
                    // finish current activity
                    if (context is Activity) {
                        context.finish()
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditDialog(
    currentName: String,
    onClose: () -> Unit,
    onProfileUpdated: (newName: String) -> Unit
) {
    var newName by rememberSaveable { mutableStateOf(currentName) }

    Dialog(
        onDismissRequest = onClose,
    ) {
        // Content of the dialog
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            Text("Edit Profile", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = newName,
                onValueChange = { newName = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        // Save changes and close the dialog
                        onProfileUpdated(newName)
                        onClose()
                    }
                ) {
                    Text("Save")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = onClose) {
                    Text("Cancel")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    FrontendTheme {
        UserInfoUI()
    }
}
