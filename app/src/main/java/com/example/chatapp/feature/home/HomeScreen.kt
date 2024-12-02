package com.example.chatapp.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatapp.ui.theme.Purple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val addChannel = remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()
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
                        //클릭 시 addChannel 을 true로 바꾼다
                        addChannel.value = true
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
        //추가함
        if (addChannel.value) {
            ModalBottomSheet(
                onDismissRequest = {},
                sheetState = sheetState
            ) {

            }
        }
    }
}
//하단의 모달 창 안에 넣을 것들을 여기에...
@Composable
fun AddChannelDialog(){

}