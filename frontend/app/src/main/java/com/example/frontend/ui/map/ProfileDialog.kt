package com.example.frontend.ui.map

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.frontend.data.predefinedImages

/*
 * 유저의 프로필을 보여주는 Dialog
 */
@Composable
internal fun ProfileDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit = {},
    painter: Painter,
) {
    return AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onDismiss() },
        title = {
            Row(
                modifier = Modifier.padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Image(
                    painter = painter,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(40.dp))
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(start = 16.dp),
                )
            }
        },
        text = {
            HorizontalDivider()
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 16.dp),
            )
        },
        confirmButton = {
            Text(
                text = "닫기",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable { onDismiss() },
            )
        },
    )
}


@Composable
@Preview
private fun ProfileDialogPreview() {
    ProfileDialog(
        title = "호에엥맨",
        description = "큭큭",
        painter = painterResource(id = predefinedImages[0]),
    )
}
