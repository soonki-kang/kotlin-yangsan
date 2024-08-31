package com.example.yangsan.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.yangsan.MainActivity.Companion.decFormatter
import com.example.yangsan.MainActivity.Companion.formatter
import com.example.yangsan.R
import com.example.yangsan.components.SkDropdownMenuStateHolder
import com.example.yangsan.components.SkTopBar0
import com.example.yangsan.components.rememberSkDropdownMenuStateHolder
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@SuppressLint("NewApi")
@Composable
fun ScdlsScreen(
    state: ScdlState,
    navController: NavController,
    onEvent: (ScdlsEvent) -> Unit,
    scdlTypeHolder: SkDropdownMenuStateHolder
) {

    Scaffold(
        modifier = Modifier.padding(top = 50.dp),
        topBar = {
            Box(
//                modifier = Modifier
//                .fillMaxSize()
            ) {
                SkTopBar0()
                Icon(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(80.dp)
                        .clickable {
                            navController.navigate("AddUserScreen")
                        }
                        .background(color = Color.White),

                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Register",
                )
            } },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                state.scdl_type.intValue = 0
                state.scdl_date.longValue = LocalDateTime.now()
                    .plusDays(14)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()?.toEpochMilli() ?: 0
                state.scdl_time.intValue = 0
                navController.navigate("AddScdlScreen")
            }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add new schedule")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            contentPadding = paddingValues,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state.scdls.size) { index ->
                ScdlItem(
                    state = state,
                    index = index,
                    onEvent = onEvent,
                    scdlTypeHolder = scdlTypeHolder
                )
            }
        }
    }
}

@Composable
fun ScdlItem(
    state: ScdlState,
    index: Int,
    onEvent: (ScdlsEvent) -> Unit,
    scdlTypeHolder: SkDropdownMenuStateHolder
) {
    Row(
        modifier = Modifier
//            .fillMaxSize()
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly
////        Column(
////            modifier = Modifier
////            .padding(20.dp)
//        ) {

        Text(
            text = formatter.format(
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(state.scdls[index].scdl_date),
                    ZoneId.systemDefault()
                )
            ),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        Text(
            text = "${decFormatter.format(state.scdls[index].scdl_time)} 시",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier
                .padding(10.dp)
        )

        Text(
            text = scdlTypeHolder.items[state.scdls[index].scdl_type],
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
        )

        IconButton(onClick = {
            onEvent(ScdlsEvent.DeleteScdl(state.scdls[index]))
        }) {
            Icon(
                imageVector = Icons.Rounded.Delete,
                contentDescription = "Delete Schedule",
                modifier = Modifier
                    .size(50.dp)
                    .padding(start = 10.dp),
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
//}