package com.example.yangsan.presentation

import android.annotation.SuppressLint
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
fun AddUserScreen(
    state: UserState,
    navController: NavController,
    onEvent: (UsersEvent) -> Unit,
) {

    Scaffold(
        modifier = Modifier.padding(top = 50.dp),
        topBar = { SkTopBar0() },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(
                    UsersEvent.SaveUser(
                        user_id = state.user_id.value,
                        user_password = state.user_password.value,
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


                SkOutlinedTextField(
                    value = state.user_id.value,
                    label = "User ID : ",
                    readOnly = false,
                    onValueChange = {
                        state.user_id.value = it
                    },
                )

                SkOutlinedTextField(
                    value = state.user_password.value,
                    label = "Password",
                    readOnly = false,
                    onValueChange = {
                        state.user_password.value = it
                    },
                )

            }
        }
    }
}
