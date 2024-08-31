package com.example.yangsan.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.yangsan.R

@Composable
fun SkTopBar0() {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .clip(RoundedCornerShape(15.dp)),
        shadowElevation = 3.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .height(200.dp)
                    .width(400.dp)
                    .padding(top = 20.dp, start = 10.dp, end = 10.dp, bottom = 0.dp),
                painter = painterResource(id = R.drawable.logo),

                contentDescription = "YangSan",
                contentScale = ContentScale.FillBounds
            )
        }
    }
}
