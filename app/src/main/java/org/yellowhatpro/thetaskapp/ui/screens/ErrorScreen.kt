package org.yellowhatpro.thetaskapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorScreen(error: String) {
    Column {
        Text(text = error)
    }
}