package com.example.frontend.viewmodel

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InviteFriendViewModel : ViewModel() {

    private val _checkedStates = mutableStateMapOf<Long, Boolean>()
    private val _checkedStatesFlow = MutableStateFlow<Map<Long, Boolean>>(_checkedStates)

    val checkedStatesFlow = _checkedStatesFlow.asStateFlow()
    fun updateCheckedState(friendId: Long, isChecked: Boolean) {
        _checkedStates[friendId] = isChecked
        _checkedStatesFlow.value = _checkedStates.toMap()
    }
}
