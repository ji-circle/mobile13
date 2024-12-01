package com.example.chatapp.feature.auth.signup

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
fun SignUpScreen(
    navController: NavController
) {
    val viewModel: SignUpViewModel = hiltViewModel()
    val uiState = viewModel.state.collectAsState()

    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirm by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = uiState.value) {
        when (uiState.value) {
            SignUpState.Success -> {
                //TODO 이부분 수정해야 하는 거 아닌가?
                navController.navigate("home") {
                    popUpTo("login") {
                        inclusive = true
                    }
                }
            }

            is SignUpState.Error -> {
                Toast.makeText(context, "Sign Up Failed", Toast.LENGTH_SHORT).show()
            }

            else -> {}
//            SignUpState.Loading -> TODO()
//            SignUpState.Nothing -> TODO()
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
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Name") }
            )
            Spacer(modifier = Modifier.size(8.dp))

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
            Spacer(modifier = Modifier.size(8.dp))

            OutlinedTextField(
                value = confirm,
                onValueChange = { confirm = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Confirm Password") },
                visualTransformation = PasswordVisualTransformation(),

                //위의 password와 이 칸에 입력한 게 일치할 경우에만 ~~ 하게 할거면
                // 두 칸 다 비어있지 않은데 입력된 게 서로 다르면 에러니까!! 테두리가 붉은색이 됨
                isError = password.isNotEmpty() && confirm.isNotEmpty() && confirm != password
            )
            Spacer(modifier = Modifier.size(16.dp))

            if (uiState.value == SignUpState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        viewModel.SignUp(
                            email,
                            password
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),

                    //모든 값들이 다 입력되어야 sign up 버튼이 활성화되게 하려면
                    enabled = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty() && password == confirm
                ) {
                    Text(text = stringResource(id = R.string.signUp))
                }

                TextButton(
                    onClick = {}
                ) {
                    Text(text = stringResource(id = R.string.signuptext))
                }
            }
        }
    }
}