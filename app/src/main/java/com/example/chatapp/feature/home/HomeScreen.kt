package com.example.chatapp.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatapp.ui.theme.Purple

@Composable
fun HomeScreen(
    navController: NavController
) {
    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    //둥근 사각형 만들기
                    .clip(RoundedCornerShape(16.dp))
                    .background(Purple)
                    //클릭할 수 있게
                    .clickable {

                    }
            ) {
                Text(
                    text = "Add Channel",
                    //텍스트 밖으로 패딩 주기
                    modifier = Modifier.padding(16.dp),
                    //글자 색
                    color = Color.White
                )
            }
        },
        //배경 색 수정
        containerColor = Color.Black
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {

        }
    }
}