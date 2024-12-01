package com.example.chatapp.feature.auth.signin

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatapp.R

//controller를 직접 넘겨받는 방식으로 구현
@Composable
fun SignInScreen(
    navController: NavController
) {
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    //UI 구성은 보통 scaffold로 구성함
    //scaffold에 padding(it) 또는 paddingValue -> 사용 안하면 붉은밑줄 뜸
    // : scaffold는 하나의 padding value를 받고 리턴값이 없는 paddingValue를 가지고 있음..
    //  그래서 padding(it)하면 paddingValue가 들어감
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            // verticalArr Center, horizonAli CenterHorizon 하면 가운데 중앙에 온다
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            //이미지 넣는 부분.
            //Image( painter) 사용. 아이콘이 아니니까.
            Image(
                painter = painterResource(id = R.drawable.chatimage),
                contentDescription = null,
                //이미지 크기 줄이는 법
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.size(32.dp))

            //
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                //텍스트 입력받는 부분의 너비... 위에서 주어진 패딩 내에서 채운다
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Email") }
            )

            Spacer(modifier = Modifier.size(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password") },
                //password 누를 때 안보이게 하려면
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.size(16.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(id = R.string.signin))
            }

            TextButton(
                onClick = {}
            ) {
                Text(text = stringResource(id = R.string.signintext))
            }
        }
    }
}