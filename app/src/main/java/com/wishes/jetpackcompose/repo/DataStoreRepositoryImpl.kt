package com.wishes.jetpackcompose.repo

import com.wishes.jetpackcompose.data.LocalDataSource
import com.wishes.jetpackcompose.data.RemoteDataSource
import com.wishes.jetpackcompose.preferences.implimentation.DataStoreRepositoryImpl
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource,
    dataStore: DataStoreRepositoryImpl
) {
    val remote = remoteDataSource
    val local = localDataSource
    val dataStore = dataStore


}