package com.example.frontend.ui.settings

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import coil.compose.AsyncImagePainter.State.Empty.painter
import coil.compose.rememberImagePainter
import com.example.frontend.MapActivity
import com.example.frontend.MissionActivity
import com.example.frontend.R
import com.example.frontend.data.predefinedImages
import com.example.frontend.repository.UserContextRepository

import com.example.frontend.ui.settings.component.MissionCard
import com.example.frontend.ui.theme.FrontendTheme

class UserInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontendTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UserInfoUI("Android")
                }
            }
        }
    }
}

@Composable
fun UserInfoUI(name: String, modifier: Modifier = Modifier) {
    var context = LocalContext.current
    var selectedPredefinedImage = UserContextRepository(context).getSelectedPredefinedImage()
    var showDialog by rememberSaveable { mutableStateOf(false) }

    val currentUserName = UserContextRepository(context).getUserName() ?: "DefaultName"

    if (showDialog) {
        ProfileEditDialog(
            currentName = currentUserName,
            onClose = { showDialog = false },
            onProfileUpdated = { newName ->
                UserContextRepository(context).saveUserName(newName)
            }
        )
    }

    fun showImageSelectionDialog() {
        AlertDialog.Builder(context)
            .setTitle("이미지 선택")
            .setItems(
                arrayOf("Gray Cat with Sunglass", "Yellow Cat with Sunglass", "Dog with Sunglass", "Hamster")
            ) { _, which ->
                selectedPredefinedImage = predefinedImages[which]
                UserContextRepository(context).saveSelectedPredefinedImage(selectedPredefinedImage)
            }

            .show()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = Color(0xFFF3EDF7))

        )
        Row(
            modifier = Modifier
                .height(54.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier
                    .size(46.dp)
                    .clickable {
                        val nextIntent = Intent(context, MapActivity::class.java)
                        context.startActivity(nextIntent)
                        // finish current activity
                        if (context is Activity) {
                            context.finish()
                        }
                    }
            )
            Spacer(modifier = Modifier.width(8.dp))


            Text(
                modifier = Modifier,

                text = "내 정보",
                style = TextStyle(
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF1D1B20)
                )
            )
            Spacer(modifier = Modifier.weight(0.4f))
            Icon(
                imageVector = Icons.Outlined.Settings,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .clickable {
                        showDialog = true
                    }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 50.dp)
                .padding(top = 100.dp)
                .padding(end = 30.dp)

        ) {

            if (selectedPredefinedImage != null) {
                Image(
                    painter = painterResource(id = selectedPredefinedImage!!),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            showImageSelectionDialog()
                        }
                )
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
                tint=Color(0xFF6750A4),
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
                    text = UserContextRepository(context).getUserName()?: "김샤프",
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
            Row(){
                Text(
                    text = "이메일",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        letterSpacing = 0.1.sp,
                    ) ,
                    modifier = Modifier.padding(start = 30.dp)
                )

                Text(
                    text = UserContextRepository(context).getUserMail()?:"sha@snu.ac.kr",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 20.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000),
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.1.sp,
                    )  ,
                    modifier = Modifier.padding(start = 80.dp)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.Start),
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                    .width(344.dp)
                    .height(130.dp)
                    .clickable {
                        val nextIntent = Intent(context, MissionActivity::class.java)
                        context.startActivity(nextIntent)
                    }
            ) {
                MissionCard()
            }
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
        UserInfoUI("Android")
    }
}