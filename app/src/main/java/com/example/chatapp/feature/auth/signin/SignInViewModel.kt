package com.example.chatapp.feature.auth.signin

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

//힐트뷰모델 적어주기
@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {
    //nothing으로부터 시작해서...
    private val _state = MutableStateFlow<SignInState>(SignInState.Nothing)

    //읽을수는 있지만 쓸 수는 없는.
    val state = _state.asStateFlow()

    fun signIn(
        email: String,
        password: String
    ) {
        //signIn을 시도하는 순간 state를 loading으로 바꿀것임
        _state.value = SignInState.Loading

        //signInWithEmailAndPassword는 비동기함수임.
        // 이게 끝나고 나서 뭘 할건지를 이 바로 밑 if에서 해주면 안 됨.
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.value = SignInState.Success
                } else {
                    _state.value = SignInState.Error
                }

            }
    }


}

//상태를 나타내는 클래스 만들기
// sealed class = 이 SignInState의 자식 객체를 몇 가지 제한함...
//  이 안에서 제한한 것만 자식 객체로 인정한다는 것. 여기선 4가지.
sealed class SignInState {
    object Nothing : SignInState()
    object Loading : SignInState()
    object Success : SignInState()
    object Error : SignInState()
}