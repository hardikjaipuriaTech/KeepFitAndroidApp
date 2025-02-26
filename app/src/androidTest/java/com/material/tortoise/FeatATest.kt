package com.material.tortoise

import android.content.res.Resources
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FeatATest {

    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var res: Resources
   // var colorList = listOf<ColorItem>()

    @Before
    fun setup() {
        res = InstrumentationRegistry.getInstrumentation().targetContext.resources
       // colorList = StaticData.GOAL_MAP.values.toList()
    }

    @Test
    fun forFeatAShown_whenInitialized_enterTitleIsDisplayed() {
        composeTestRule.setContent {
           // FeatABody(onLaunchClicked = {})
        }
      //  composeTestRule.onNodeWithText(res.getString(R.string.enter_a_color)).assertIsDisplayed()
    }
}
