package com.example.frontend.ui.component

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage


/*
 * 둥근 모서리를 가진 이미지를 표시하는 컴포저블입니다.
 */
@Composable
fun RoundedImage(
    imageUrl: String,
    contentDescription: String? = null,
    size: Int = 200
) {
    return AsyncImage(
        model = imageUrl,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(size.dp)
            .clip(CircleShape)
    )
}

@Composable
@Preview
private fun RoundedImagePreview() {
    RoundedImage(
        imageUrl = "https://i.namu.wiki/i/GQMqb8jtiqpCo6_US7jmWDO30KfPB2MMvbdURVub61Rs6ALKqbG-nUATj-wNk7bXXWIDjiLHJxWYkTELUgybkA.webp",
        contentDescription = "Translated description of what the image contains"
    )
}

