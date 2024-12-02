package com.example.chatapp.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.ui.theme.DarkGray
import com.example.chatapp.ui.theme.Purple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val channels = viewModel.channels.collectAsState()

    val addChannel = remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()
    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Purple)
                    .clickable {
                        addChannel.value = true
                    }
            ) {
                Text(
                    text = "Add Channel",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
        },
        containerColor = Color.Black
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
                .fillMaxSize()
        ) {

            LazyColumn(

            ) {
                //화면 상단에 제목 넣기, 로그아웃 넣기
                //아이템 하나 만들기
                item {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        //옆과 세로위치 동일하게... 중앙에 가게
                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        Text(
                            text = "Messages",
                            color = Color.Gray,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black
                            ),
                            //messages가 icon을 제외한 모든 공간을 다 차지하게 하려면
                            //  (= 아이콘이 가장 오른쪽으로 밀리고, message의 크기는 딱히 늘어나진 않음)
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {

                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = null,
                                //아이콘의 색 변경은 tint
                                tint = Color.Gray
                            )
                        }
                    }
                }

                items(channels.value) { channel ->
                    //여기 수정
                    ChannelItem(channel.name)
//
                }
            }
        }
        if (addChannel.value) {
            ModalBottomSheet(
                onDismissRequest = {},
                sheetState = sheetState
            ) {
                AddChannelDialog {
                    viewModel.addChannel(it)
                    addChannel.value = false
                }
            }
        }
    }
}

@Composable
fun ChannelItem(channelName: String) {
    //채널의 앞글자를 따서 동그랗게 아이콘을 만들고 채널 이름을 보여주게...
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkGray)
            .clickable {

            },
        // 박스 내의 채널이름 텍스트가 위쪽에 치우쳐져있었음... 그거 수정
        verticalAlignment = Alignment.CenterVertically
    ) {
        //텍스트 앞에 원이 있고, 그 안에 첫글자 넣는거
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(70.dp)
                .clip(CircleShape)
                .background(Color.Yellow.copy(alpha = 0.3f))
        ) {
            Text(
                //채널 이름의 첫번째 글자를 따고, 그거의 대문자 형태로
                text = channelName[0].uppercase(),
                color = Color.White,
                style = TextStyle(
                    fontSize = 35.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        //채팅방 이름 부분
        Text(
            text = channelName,
            modifier = Modifier.padding(8.dp),
            color = Color.White,
            style = TextStyle(
                fontSize = 20.sp
            )
        )
    }
}

@Composable
fun AddChannelDialog(
    onAddChannel: (String) -> Unit
) {
    var channelName by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Add Channel")
        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            value = channelName,
            onValueChange = { channelName = it },
            label = { Text(text = "Channel Name") },
            singleLine = true
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Button(
            onClick = { onAddChannel(channelName) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Add")
        }
    }
}