package com.example.composealarmprepare

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.composealarmprepare.ui.theme.ComposealarmprepareTheme
import java.time.LocalTime

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposealarmprepareTheme {
                PrepareTimePicker()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PrepareTimePicker() {
    var timePickerState by remember { mutableStateOf(LocalTime.now()) }
    var showPicker by remember { mutableStateOf(false) }

    Column(Modifier.statusBarsPadding()) {
        Text(
            "${
                timePickerState.hour.toString().padStart(2, '0')
            }:${timePickerState.minute.toString().padStart(2, '0')}"
        )
        Button(onClick = {
            showPicker = !showPicker
        }) {
            Text("Open Picker")
        }
        if (showPicker)
            TimePicker(dismiss = { showPicker = false }) { time ->
                timePickerState = time
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TimePicker(dismiss: () -> Unit, confirm: (LocalTime) -> Unit) {
    var timePickerState = rememberTimePickerState(
        initialMinute = LocalTime.now().minute,
        initialHour = LocalTime.now().hour,
        is24Hour = true
    )
    AlertDialog(
        onDismissRequest = dismiss,
        confirmButton = {
            TextButton(onClick = {
                confirm(LocalTime.of(timePickerState.hour, timePickerState.minute))
                dismiss()
            }) { Text("Ok") }
        },
        dismissButton = {
            TextButton(onClick = dismiss) { Text("Cancel") }
        }, text = {
            androidx.compose.material3.TimePicker(state = timePickerState)
        }
    )
}