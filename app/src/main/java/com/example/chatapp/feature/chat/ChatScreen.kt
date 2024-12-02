package com.example.chatapp.feature.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.chatapp.R
import com.example.chatapp.feature.model.Message

@Composable
fun ChatScreen(
    navController: NavController,
    channelID: String,
    channelName: String
) {
    Scaffold(
        containerColor = Color.Black
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {


            //채팅 보내기 버튼
        }
    }

}

//하단의... 텍스트 필드와 보내기 버튼
@Composable
fun ChatMessages(
    channelName: String,
    messages: List<Message>
) {
    var sentence by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            //지금 만들 보내기 버튼을 제외하고 나머지를 위에가 다 차지해야 하므로
            modifier = Modifier.weight(1f)
        ) {
            items(messages) { msg ->
                Text(text = msg.message)
            }
        }

        //텍스트필드와 보내기 버튼
        Row(

        ) {
            TextField(
                value = sentence,
                onValueChange = { sentence = it }
            )
            IconButton(
                onClick = {

                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.send),
                    contentDescription = null
                )
            }
        }
    }
}