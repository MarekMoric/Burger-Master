package com.mendelu.xmoric.burgermaster

import androidx.activity.compose.setContent
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mendelu.xmoric.burgermaster.navigation.Destination
import com.mendelu.xmoric.burgermaster.navigation.NavigationRouterImpl
import com.mendelu.xmoric.burgermaster.ui.screens.CreationScreen
import com.mendelu.xmoric.burgermaster.ui.screens.ListScreen
import com.mendelu.xmoric.burgermaster.ui.screens.ProfileScreen
import com.mendelu.xmoric.burgermaster.ui.theme.BurgerMasterTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule()
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.mendelu.xmoric.burgermaster", appContext.packageName)
    }

    @Test
    fun listPresented(){
        composeRule.activity.setContent {
            BurgerMasterTheme() {
                ListScreen(navigation = NavigationRouterImpl(rememberNavController()))
            }
        }
        with(composeRule){
            waitForIdle()
            onNodeWithTag("BurgerTagList").assertExists(null)
            onNodeWithTag("Burger Row2").assertExists(null)
        }
    }

    @Test
    fun profilePresented(){
        composeRule.activity.setContent {
            BurgerMasterTheme() {
                ProfileScreen(navigation = NavigationRouterImpl(rememberNavController()))
            }
        }
        with(composeRule){
            waitForIdle()
            onNodeWithTag("Profile Name").performClick()
            onNodeWithTag("Profile Name").performTextInput("Meno")
            onNodeWithTag("Profile Name").assertTextContains("Meno")
        }
    }

    @Test
    fun profileCVCTest(){
        composeRule.activity.setContent {
            BurgerMasterTheme() {
                ProfileScreen(navigation = NavigationRouterImpl(rememberNavController()))
            }
        }
        with(composeRule){
            waitForIdle()
            onNodeWithTag("CVC Test").performClick()
            onNodeWithTag("CVC Test").performTextInput("1234")
            onNodeWithTag("CVC Test").assertTextContains("")
        }
    }

    @Test
    fun textfieldPresented(){
        composeRule.activity.setContent {
            BurgerMasterTheme() {
                ProfileScreen(navigation = NavigationRouterImpl(rememberNavController()))
            }
        }
        with(composeRule){
            waitForIdle()
            onNodeWithTag("Profile Name").performClick()
            onNodeWithTag("Profile Name").performTextInput("VA2")
            onNodeWithTag("Profile Name").assertHasClickAction()
        }
    }

    @Test
    fun saveBurgerTest(){
        composeRule.activity.setContent {
            BurgerMasterTheme() {
                CreationScreen(navigation = NavigationRouterImpl(rememberNavController()))
            }
        }
        with(composeRule){
            waitForIdle()
            onNodeWithTag("Save Test").performClick()
        }
    }
}

