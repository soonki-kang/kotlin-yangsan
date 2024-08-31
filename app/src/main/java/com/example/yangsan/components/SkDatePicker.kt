package com.example.yangsan.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun SkDatePicker(
    label: String,
    addDate: Int,
    onDateSelected: (LocalDateTime) -> Unit
) {
    var date by remember { mutableStateOf(LocalDateTime.now().plusDays(addDate.toLong())) }
    var isOpen by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            readOnly = true,
            onValueChange = {},
            value = date.format(DateTimeFormatter.ISO_DATE),
            label = { Text(label) },
            textStyle = TextStyle.Default.copy(fontSize = 20.sp, color = Color.Black),
            shape = RoundedCornerShape(20.dp),
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary
            ),
            trailingIcon = {
                IconButton(
                    onClick = { isOpen = true }
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Calendar"
                    )
                }
            }
        )
    }

    if (isOpen) {
        SkDatePickerDialog(
            onAccept = {
                isOpen = false

                if (it != null) {
                    date = Instant
                        .ofEpochMilli(it)
                        .atZone(ZoneId.systemDefault())
//                        .atZone(ZoneId.of("UTC"))
                        .toLocalDateTime()
                    onDateSelected(date)
                }
            },
            onCancel = {
                isOpen = false
            },
            date = date

        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkDatePickerDialog(
    onAccept: (Long?) -> Unit,
    onCancel: () -> Unit,
    date: LocalDateTime
) {
//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//    val strDate = date.format(formatter)
//    Log.d("SCDL", "SkDatePickerDialog: ${strDate}")
//    val localdatetime = LocalDateTime.now().plusDays(14)
//    val timlong = localdatetime.toEpochSecond(ZoneOffset.UTC) * 1000
//    val zoneId = ZoneId.systemDefault()
//    val epochMillis = date.atStartOfDay(zoneId).toEpochSecond() * 1000
//    val epochMillis = date.toEpochSecond(ZoneOffset.UTC) * 1000
    val epochMillis = date
        .atZone(ZoneId.systemDefault())
        .toInstant()?.toEpochMilli() ?: 0
    val state = rememberDatePickerState(epochMillis)
//    Log.d("SCDL", "SkDatePickerDialog: ${timlong}   ${epochMillis}")

    DatePickerDialog(
        onDismissRequest = {},
        confirmButton = {
            Button(
                onClick = {
                    onAccept(state.selectedDateMillis)

                }) {
                Text("선 택")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("취 소")
            }
        }
    ) {
        DatePicker(state = state)
    }
}

@Preview
@Composable
fun SkDatePickerPreview() {
    val date = LocalDateTime.now()
    var kk: LocalDateTime = date
    val onDateSelected: (LocalDateTime) ->
    Unit = { selectedDate -> kk = selectedDate }
    SkDatePicker("예약 일자", 14, onDateSelected)
}