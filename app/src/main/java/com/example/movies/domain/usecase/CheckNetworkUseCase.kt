package com.example.movies.domain.usecase

import com.example.movies.data.connectivityObserver.NetworkConnectivityObserver
import com.example.movies.domain.entity.Status
import javax.inject.Inject

class CheckNetworkUseCase @Inject constructor(
    private val networkConnectivityObserver: NetworkConnectivityObserver
) {

    operator fun invoke(): kotlinx.coroutines.flow.Flow<Status>{
        return networkConnectivityObserver.observe()
    }
}