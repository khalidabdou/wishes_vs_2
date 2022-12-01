package com.wishes.jetpackcompose.repo

import com.wishes.jetpackcompose.data.LocalDataSource
import com.wishes.jetpackcompose.data.RemoteDataSource
import javax.inject.Inject

class ImagesRepo @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remot = remoteDataSource
    val local = localDataSource


}