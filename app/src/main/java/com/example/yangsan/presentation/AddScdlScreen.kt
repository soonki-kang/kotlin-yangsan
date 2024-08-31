package com.example.yangsan.presentation
// https://proandroiddev.com/improving-the-compose-dropdownmenu-88469b1ef34

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.yangsan.components.SkDatePicker
import com.example.yangsan.components.SkDropdownMenu
import com.example.yangsan.components.SkDropdownMenuStateHolder
import com.example.yangsan.components.SkOutlinedTextField
import com.example.yangsan.components.SkTopBar0
import java.time.LocalDateTime
import java.time.ZoneId

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddScdlScreen(
    state: ScdlState,
    navController: NavController,
    onEvent: (ScdlsEvent) -> Unit,
    scdlTypeHolder: SkDropdownMenuStateHolder,
) {

//    val pattern = remember { Regex("^\\d+\$") }

    scdlTypeHolder.onSelectedIndex(state.scdl_type.intValue)
    //

    Scaffold(
        modifier = Modifier.padding(top = 50.dp),
        topBar = { SkTopBar0() },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(
                    ScdlsEvent.SaveScdl(
                        scdl_type = state.scdl_type.intValue,
                        scdl_date = state.scdl_date.longValue,
                        scdl_time = state.scdl_time.intValue
                    )
                )
                navController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Rounded.Check,
                    contentDescription = "Save Schedule"
                )
            }
        }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .padding(top = 200.dp, start = 40.dp, end = 40.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(
                contentColor = Color.Black,
                containerColor = Color.LightGray
            )

        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                val onDateSelected: (LocalDateTime) ->
                   Unit = { selectedDate -> state.scdl_date.longValue = selectedDate
                    .atZone(ZoneId.systemDefault())
                    .toInstant()?.toEpochMilli() ?: 0
                   }

                SkDatePicker(
                    label = "예약 일자",
                    addDate = 14,
                    onDateSelected =  onDateSelected
                )

                SkOutlinedTextField(
                    value = state.scdl_time.intValue.toString(),
                    label = "예약 시간",
                    readOnly = false,
                    onValueChange = {
                        state.scdl_time.intValue = it.toIntOrNull() ?: 0
                                    },
                )

                SkDropdownMenu(
                    value = state.scdl_type.intValue,
                    onItemSelected = { index -> state.scdl_type.intValue = index },
                    stateHolder = scdlTypeHolder,
                    label = "예약 형태"
                )

            }
        }
    }
}
