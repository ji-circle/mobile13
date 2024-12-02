package com.example.chatapp.feature.home

import androidx.lifecycle.ViewModel
import com.example.chatapp.feature.model.Channel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    //데이터베이스와 연동시키는 방법.
    // 앞으로는 이 변수를 통해 firebase database와 연결함
    private val firebaseDatabase = Firebase.database

    //여기의 channel은 따로 만든 data class임
    val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()

    init {
        getChannel()
    }

    //파이어베이스에 있는 채널을 받아오는 부분.
    // 외부에서 직접 사용하지는 못하고, homeviewmodel이 불러와질 때 getChannel이 init 안에서 실행되게.
    private fun getChannel() {
        //우리는 "channel"이라고 이름 붙인 레퍼런스에 접근하겠다는 뜻
        firebaseDatabase.getReference("channel").get()
            // 이건 비동기 함수임. 그래서 아래처럼 결과처리해야함
            .addOnSuccessListener {
                //빈 리스트 만들기
                val list = mutableListOf<Channel>()
                // it = 얻은 것. children이라고 하면 하위 목록들을 말함
                it.children.forEach { data ->
                    //목록의 각각을 channel로 만들것임. Channel이라고 하는 데이터 클래스로 만든다
                    //  key는 channel 하단에 만들었던 Test Test 중 첫번째 꺼 말하는 것.
                    val channel = Channel(data.key!!, data.value.toString())
                    list.add(channel)
                }
                _channels.value = list

            }
    }

    //외부에서도 사용할 수 있게
    fun addChannel(name: String){
        //key를 얻어오는 것.
        // children에 있는 리스트에 하나를 넣어주기 위해 key를 생성, 전달함
        val key = firebaseDatabase.getReference("channel").push().key
        firebaseDatabase.getReference("channel").child(key!!).setValue(name)
        //이것도 database 관련이라 비동기함수임...
            .addOnSuccessListener {
                //업데이트한다
                getChannel()
            }
    }

}