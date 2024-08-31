package com.example.yangsan.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import com.example.yangsan.R

class SkDropdownMenuStateHolder(
    private val _items: List<String>
){
    val items = _items
    var enabled by mutableStateOf(false)
    var value by mutableStateOf("")
    private var selectedIndex by mutableIntStateOf(-1)
    var size by mutableStateOf(Size.Zero)
    val icon: Int
        @Composable get() = if (enabled) {
            R.drawable.baseline_arrow_drop_up_24
        } else {
            R.drawable.baseline_arrow_drop_down_24
        }

    fun onEnabled(newValue: Boolean){
        enabled = newValue
    }

    fun onSelectedIndex(newValue: Int) {
        selectedIndex = newValue
        value = items[selectedIndex]
    }

    fun onSize(newValue: Size) {
        size = Size(200f, 50f)
    }

}

@Composable
fun rememberSkDropdownMenuStateHolder(itemList: List<String>) = remember() {
    SkDropdownMenuStateHolder(itemList)
}