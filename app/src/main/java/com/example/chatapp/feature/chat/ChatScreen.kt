package com.example.chatapp.feature.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.R
import com.example.chatapp.feature.home.ChannelItem
import com.example.chatapp.feature.model.Message
import com.example.chatapp.ui.theme.DarkGray
import com.example.chatapp.ui.theme.Purple
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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
            //여기 추가
            val viewModel: ChatViewModel = hiltViewModel()
            //key1 = true로 하면
            // 처음에 chatScreen이 만들어졌을 때에만 이 안의 함수를 호출하게 됨.
            // key1 = true는 바뀌지 않기 때문에
            LaunchedEffect(key1 = true) {
                //메세지를 받아와서, 뷰모델 안에 있는 메시지에 입력된다.
                viewModel.listenForMessages(channelID)
            }

            val messages = viewModel.messages.collectAsState()
            ChatMessages(
                channelName = channelName,
                messages = messages.value,
                //여기 수정
                onSendMessage = { message ->
                    //메시지 하나를 받아서...
                    viewModel.sendMessage(channelID, message)
                },
                onClickBackArrow = {
                    navController.popBackStack()
                }
            )
        }
    }

}

//하단의... 텍스트 필드와 보내기 버튼
// onSendMessage 추가
@Composable
fun ChatMessages(
    channelName: String,
    messages: List<Message>,
    onSendMessage: (String) -> Unit,
    onClickBackArrow: () -> Unit,
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
            //채널 이름과 함께 뒤로가기 버튼 만들기
            item {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            onClickBackArrow()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    ChannelItem(channelName = channelName) {

                    }
                }
            }
            items(messages) { msg ->
                //여기에 chat bubble 만들것임
                ChatBubble(message = msg)
            }
        }

        //텍스트필드와 보내기 버튼
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DarkGray)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = sentence,
                onValueChange = { sentence = it },
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = {
                    onSendMessage(sentence)
                    //보내기 버튼 누른 뒤에는 입력된 걸 없앤다
                    sentence = ""
                }
            ) {
                //여기 icon이 아니라 image임!!
                Image(
                    painter = painterResource(R.drawable.send),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {
    //다른 사람이 보냈을 땐 왼쪽, 내가 보냈을 떈 오른쪽에
    //이게 내가 보낸것인지... boolean
    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid
    val bubbleColor = if (isCurrentUser) {
        Purple
    } else {
        DarkGray
    }
    Box(
        //버블이 max가 아니고, 해당 차지하는 공간을 먼저 만들어야 하니까.. box가 max
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        //왼쪽인지 오른쪽인지를 currentUser 인지 아닌지를 기준으로
        val alignment = if (!isCurrentUser) Alignment.CenterStart else Alignment.CenterEnd

        Row(
            modifier = Modifier
                .padding(8.dp)
                .background(
                    color = bubbleColor,
                    shape = RoundedCornerShape(8.dp)
                )
                //위에서 정의한 alignment를 줄것임
                .align(alignment),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (!isCurrentUser) {
                Image(
                    painter = painterResource(R.drawable.person),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

            }
            Text(
                //trim : 양 끝에 있는 것들을 trim해냄
                text = message.message.trim(),
                color = Color.White,
                modifier = Modifier
                    .background(
                        color = bubbleColor,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(16.dp)
            )

        }
    }
}