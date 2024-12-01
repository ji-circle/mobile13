package com.example.chatapp.feature.auth.signin

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.chatapp.R

@Composable
fun SignInScreen(
    navController: NavController
) {
    val viewModel: SignInViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    //에러 처리
    // 어떤 값이 바뀌었을 때 수행하는 비동기 처리를 담당하는 함수들을 사용해야 함... 그게 LaunchedEffect
    //  key 값이 바뀔 때마다, recomposition이 될 때 마다 이게 먼저 수행된다
    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.value) {
        //uiState.value의 값에 따라서
        when (uiState.value) {
            is SignInState.Error -> {
                Toast.makeText(context, "Sign In Failed", Toast.LENGTH_SHORT).show()
            }

            else -> {}
//            SignInState.Loading -> TODO()
//            SignInState.Nothing -> TODO()
//            SignInState.Success -> TODO()
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.chatimage),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.size(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Email") }
            )

            Spacer(modifier = Modifier.size(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Spacer(modifier = Modifier.size(16.dp))

            if (uiState.value == SignInState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        viewModel.signIn(
                            email,
                            password
                        )
                    },
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
}