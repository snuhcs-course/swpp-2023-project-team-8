package com.example.frontend.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.frontend.ui.theme.LightPurple
import com.example.frontend.ui.theme.Purple80

@Composable
fun CustomButton(
    buttonText: String,
    onClickHandler: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(

        colors = ButtonDefaults.buttonColors(Purple80),
        onClick = onClickHandler,
        modifier = modifier
            .shadow(
                elevation = 4.dp,
                spotColor = LightPurple,
                ambientColor = LightPurple
            )
            .width(318.dp)
            .height(55.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = buttonText, fontSize = 20.sp)
    }
}

@Composable
@Preview
fun PreviewCustomButton() {
    CustomButton(buttonText = "Login", onClickHandler = {})
}