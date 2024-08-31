package com.example.yangsan.presentation

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yangsan.db.User
import com.example.yangsan.db.UserDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UsersViewModel(
    private val dao: UserDao
) : ViewModel() {

    private val isSortedByDateAdded = MutableStateFlow(true)

    private var users =
        dao.getUsers().stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val _state = MutableStateFlow(UserState())

    val state = combine(_state, users) { state, users ->
        state.copy(
            users = users
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserState())


    //----------
    //https://github.com/FractalCodeRicardo/programmingheadache-misc/blob/main/custom-validation-annotation/app/src/main/java/com/thisisthetime/customvalidationannotation/MainActivity.kt

    fun updateProperty(newState: UserState) {
        _state.update { newState }
    }

    //---------

    fun onEvent(event: UsersEvent) {
        when (event) {

            is UsersEvent.SaveUser -> {

                //Move
                val user = User(
                    user_id = state.value.user_id.value,
                    user_password = state.value.user_password.value,
                    id = 0,
                )

                Log.d("SCDL", "onEvent: ${state.value.user_id.value} ")
                //Save
                viewModelScope.launch {
                    dao.upsertUser(user)
                }

                //Clear
                _state.update {
                    it.copy(
                        user_id = mutableStateOf(""),
                        user_password = mutableStateOf("")
                    )
                }
            }
        }
    }
}