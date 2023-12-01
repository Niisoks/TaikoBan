package com.example.taikoban

import android.app.AlertDialog
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.taikoban.ui.common.NoticeDialog
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class CommonUITest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun testConfirmButton() {
        var confirmationCalled = false

        rule.setContent {
            NoticeDialog(
                dialogTitle = "Test Title",
                dialogText = "Test Text",
                icon = R.drawable.donchan,
                onConfirmation = { confirmationCalled = true }
            )
        }

        rule.onNodeWithText("Confirm").performClick()

        assert(confirmationCalled)
    }

    @Test
    fun testDismissButton() {
        var dismissCalled = false

        rule.setContent {
            NoticeDialog(
                dialogTitle = "Test Title",
                dialogText = "Test Text",
                icon = R.drawable.donchan,
                onDismissRequest =  {dismissCalled = true},
                iconDescription = "Alert Dialog"
            )
        }

        rule.onNodeWithText("Dismiss").performClick()
        rule.onNodeWithContentDescription("Alert Dialog").assertExists()

        assert(dismissCalled)
    }
    @Test
    fun testOpenCVDialog(){
        rule.setContent {
            OpenCVDialog()
        }
        Thread.sleep(500)
        rule.onNodeWithText("initialize", ignoreCase = true)
        rule.onNodeWithText("").assertDoesNotExist()

    }

}