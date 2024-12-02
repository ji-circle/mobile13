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
import androidx.compose.runtime.LaunchedEffect
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
import kotlin.math.sign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val channels = viewModel.channels.collectAsState()
    val uiState = viewModel.state.collectAsState()

    val addChannel = remember {
        mutableStateOf(false)
    }

    val sheetState = rememberModalBottomSheetState()

    LaunchedEffect(key1 = uiState.value) {
        if (uiState.value == SignOutState.LoggedOut) {
            navController.navigate("login") {
                popUpTo("home") {
                    inclusive = true
                }
            }
        }
    }


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
                item {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Text(
                            text = "Messages",
                            color = Color.Gray,
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = {
                                viewModel.signOut()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    }
                }

                items(channels.value) { channel ->
                    //여기 수정
                    // 람다니까 첫번째 매개변수 그대로 두고, 람다는 밖으로 빼내도 됨
                    ChannelItem(channel.name) {
                        navController.navigate("chat/${channel.id}&${channel.name}")
                    }
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

//각각이 채널 하나씩임... 채팅방 하나씩.
@Composable
fun ChannelItem(
    channelName: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 2.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkGray)
            .clickable {
                //여기 수정
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .size(70.dp)
                .clip(CircleShape)
                .background(Color.Yellow.copy(alpha = 0.3f))
        ) {
            Text(
                text = channelName[0].uppercase(),
                color = Color.White,
                style = TextStyle(
                    fontSize = 35.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

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