package com.example.movies.data.connectivityObserver

import com.example.movies.domain.entity.Status
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Status>
}


