package com.example.yangsan.presentation

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yangsan.db.Scdl
import com.example.yangsan.db.ScdlDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScdlsViewModel(
    private val dao: ScdlDao
) : ViewModel() {

    /**
       1. MutableStateFlow(true)를 사용하여 생성된 상태 Flow입니다.
       2. 초깃값은 true로, 일정 목록이 추가된 날짜 순으로 정렬되어 있음을 나타냅니다.
       3. 이 Flow의 값이 변경되면, scdls Flow가 재평가되어 일정 목록을 다시 가져옵니다.
     */
    private val isSortedByDateAdded = MutableStateFlow(true)

//    private val scdls =
//        dao.getScdlsOrderedByDate()
//        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    /**
       1. isSortedByDateAdded Flow를 flatMapLatest 연산자로 변환하여 생성된 Flow입니다.
       2. flatMapLatest는 upstream Flow (여기서는 isSortedByDateAdded)가 새 값을 방출할 때마다
          downstream Flow를 취소하고 새 Flow를 생성합니다.
       3. 즉, isSortedByDateAdded 값이 변경될 때마다 (정렬 기준이 변경될 때마다) 데이터베이스에서
          일정 목록을 다시 가져옵니다.
       4. 주석 처리된 부분은 정렬 기준에 따라 다른 쿼리를 사용할 수 있도록 예시를 보여줍니다.
       5. .stateIn 연산자를 사용하여 viewModelScope 내에서 Flow를 StateFlow로 변환하고,
          초기값으로 빈 리스트(emptyList())를 설정합니다.
       6. SharingStarted.WhileSubscribed(5000)는 구독자가 있을 때 Flow를 시작하고,
          마지막 구독자가 취소된 후 5초 동안 Flow를 유지합니다.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private var scdls = isSortedByDateAdded.flatMapLatest { sort ->
        dao.getScdlsOrderedByDate()
//        if (sort) {
//            dao.getScdlsOrderedByDate()
//        } else {
//            dao.getScdlsOrderedByDate()
//        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    /**
       1. MutableStateFlow(ScdlState())를 사용하여 생성된 상태 Flow입니다.
       2. ScdlState는 UI 상태를 나타내는 데이터 클래스로 추정됩니다.
       3. 이 Flow는 UI 상태의 변경 사항을 나타냅니다.
     */

    val _state = MutableStateFlow(ScdlState())

    /**
       1. combine 연산자를 사용하여 _state, isSortedByDateAdded,
          scdls 세 개의 Flow를 결합하여 생성된 StateFlow입니다.
       2. combine은 모든 upstream Flow에서 새 값을 방출할 때마다 downstream Flow에 새 값을 방출합니다.
       3. 람다 함수 { state, _, scdls -> ... }는 세 개의 Flow에서 방출된 값을 받아 새로운 ScdlState 객체를 생성합니다.
       4. state.copy(scdls = scdls)는 기존 state 객체를 복사하고 scdls 속성만 업데이트합니다.
       5. .stateIn 연산자를 사용하여 viewModelScope 내에서 Flow를 StateFlow로 변환하고, 초기값으로 ScdlState()를 설정합니다.
       6. SharingStarted.WhileSubscribed(5000)는 구독자가 있을 때 Flow를 시작하고,
          마지막 구독자가 취소된 후 5초 동안 Flow를 유지합니다.
     */

    val state = combine(_state, isSortedByDateAdded, scdls) { state, _, scdls ->
        state.copy(
            scdls = scdls
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ScdlState())

    //----------
    //https://github.com/FractalCodeRicardo/programmingheadache-misc/blob/main/custom-validation-annotation/app/src/main/java/com/thisisthetime/customvalidationannotation/MainActivity.kt

    fun updateProperty(newState: ScdlState) {
        _state.update { newState}
    }

    //---------

    fun onEvent(event: ScdlsEvent) {
        when (event) {
            is ScdlsEvent.DeleteScdl -> {
                viewModelScope.launch {
                    dao.deleteScdl(event.scdl)
                }
            }

            is ScdlsEvent.SaveScdl -> {

                //Move
                val scdl = Scdl(
                    scdl_type = state.value.scdl_type.intValue,
                    scdl_date = state.value.scdl_date.longValue,
//                    scdl_date = state.value.scdl_date.value.toEpochSecond(ZoneOffset.UTC) * 1000,
                    scdl_time = state.value.scdl_time.intValue
                )

                Log.d("SCDL", "onEvent: ${state.value.scdl_type.intValue} ")
                //Save
                viewModelScope.launch {
                    dao.upsertScdl(scdl)
                }

                //Clear
                _state.update {
                    it.copy(
                        scdl_type = mutableIntStateOf(0),
                        scdl_date = mutableLongStateOf(0L) ,
                        scdl_time = mutableIntStateOf(0)
                    )
                }
            }

            //ToDo SortScdls 삭제 검토

            ScdlsEvent.SortScdls -> {
                isSortedByDateAdded.value = !isSortedByDateAdded.value
            }

        }
    }
}