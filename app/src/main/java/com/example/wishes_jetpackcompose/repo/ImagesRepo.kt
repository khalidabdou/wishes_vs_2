package com.example.wishes_jetpackcompose.repo

import com.example.wishes_jetpackcompose.data.LocalDataSource
import com.example.wishes_jetpackcompose.data.RemoteDataSource
import javax.inject.Inject

class ImagesRepo @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val remot = remoteDataSource
    val local = localDataSource



}