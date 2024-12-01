package com.example.chatapp.feature.auth.signup

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

//힐트뷰모델 적어주기
@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<SignUpState>(SignUpState.Nothing)

    val state = _state.asStateFlow()

    fun SignUp(
        email: String,
        password: String
    ) {
        //SignUp을 시도하는 순간 state를 loading으로 바꿀것임
        _state.value = SignUpState.Loading

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.value = SignUpState.Success
                } else {
                    _state.value = SignUpState.Error
                }

            }
    }
}

sealed class SignUpState {
    object Nothing : SignUpState()
    object Loading : SignUpState()
    object Success : SignUpState()
    object Error : SignUpState()
}