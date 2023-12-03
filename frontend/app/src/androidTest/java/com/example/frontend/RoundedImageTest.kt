package com.example.frontend

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.frontend.ui.component.RoundedImage
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RoundedImageTest {
    private val imageUrl = "https://i.namu.wiki/i/GQMqb8jtiqpCo6_US7jmWDO30KfPB2MMvbdURVub61Rs6ALKqbG-nUATj-wNk7bXXWIDjiLHJxWYkTELUgybkA.webp"
    private val contentDescription = "Translated description of what the image contains"

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun 제대로된_Description이_존재한다() {
        composeTestRule.setContent {
            RoundedImage(imageUrl, contentDescription)
        }

        composeTestRule.onNodeWithContentDescription(contentDescription).assertIsDisplayed()
    }

    @Test
    fun 틀린_description은_존재하지_않는다() {
        val incorrectContentDescription = "Incorrect description"

        composeTestRule.setContent {
            RoundedImage(imageUrl, contentDescription)
        }

        composeTestRule.onNodeWithContentDescription(incorrectContentDescription).assertDoesNotExist()
    }
}