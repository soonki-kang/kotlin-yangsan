package com.example.yangsan.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import com.example.yangsan.presentation.ScdlState
import kotlin.math.log


// onValueChange의 람다 함수를 작성 못해서 1개의 Dropdownmenu에만 적용되게 함.
// ToDo : onValueChange
@Composable
fun SkDropdownMenu(
    value: Int,
    onItemSelected: (Int) -> Unit,
    stateHolder: SkDropdownMenuStateHolder,
    label: String
) {
    var onSelectedIndex by remember {mutableIntStateOf(value) }

    Column {
        Box {
            OutlinedTextField(
                value = stateHolder.items[onSelectedIndex],
                onValueChange = {},
                label = { Text(label) },
                readOnly = true,
                textStyle = TextStyle.Default.copy(fontSize = 20.sp, color = Color.Black),
                shape = RoundedCornerShape(20.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.outline,
                    unfocusedBorderColor = MaterialTheme.colorScheme.primary
                ),
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = stateHolder.icon),
                        contentDescription = null,
                        Modifier.clickable {
                            stateHolder.onEnabled(!(stateHolder.enabled))
                        }
                    )
                },
                modifier = Modifier
                    .onGloballyPositioned {
                        stateHolder.onSize(it.size.toSize())
                    },


            )

            val DDHeight = when (stateHolder.items.count()) {
                1 -> 50.dp
                2 -> 100.dp
                3 -> 150.dp
                4 -> 200.dp
                else -> 250.dp
            }

//            MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(30.dp) )) {
            DropdownMenu(
                expanded = stateHolder.enabled,
                onDismissRequest = {
                    stateHolder.onEnabled(false)
                },
                properties = PopupProperties(focusable = false),
                modifier = Modifier
                    .width(with(LocalDensity.current)
                    { stateHolder.size.width.toDp() })
                    .requiredHeight(DDHeight)
            ) {
                //                Log.d("SCDL", "SkDropdownMenu: $onSelectedIndex")
                stateHolder.items.forEachIndexed { index, s ->
                    DropdownMenuItem(
                        text = { Text(s, style = TextStyle(color = Color.Black)) },
                        onClick = {
                            stateHolder.onSelectedIndex(index)
                            stateHolder.onEnabled(false)
                            onSelectedIndex = index
                            onItemSelected(index)
                            Log.d("SCDL", "item : $onSelectedIndex")
                        }
                    )
                }
            }
        }
    }
//    }
}