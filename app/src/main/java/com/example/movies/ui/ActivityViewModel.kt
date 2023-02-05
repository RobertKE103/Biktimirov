package com.example.movies.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movies.domain.usecase.CheckNetworkUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class ActivityViewModel @Inject constructor(
    checkNetworkUseCase: CheckNetworkUseCase
): ViewModel() {

    val statusNetwork = checkNetworkUseCase.invoke()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

}