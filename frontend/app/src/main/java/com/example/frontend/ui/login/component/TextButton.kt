package com.example.frontend.ui.login.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


private val enabledColor = Color(0xFF6200EE)

@Composable
fun TextButton(
    text: String,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
) {
    Text(
        text = text,
        style = TextStyle(
            fontSize = 24.sp,
            fontWeight = FontWeight(400),
            color = if (isEnabled) enabledColor else Color.Gray,
            textDecoration = if (isEnabled) TextDecoration.Underline else TextDecoration.None,
        ),
        modifier = Modifier
            .width(90.dp)
            .height(40.dp)
            .clickable { onClick() }
    )
}


@Preview
@Composable
fun TextButtonPreview() {
    TextButton(text = "Register", onClick = {})
}
