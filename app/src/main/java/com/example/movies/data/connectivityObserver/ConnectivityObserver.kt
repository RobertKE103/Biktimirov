package com.example.movies.data.connectivityObserver

import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {
    fun observe(): Flow<Status>
}

enum class Status {
    Available, Unavailable, Losing, Lost
}

