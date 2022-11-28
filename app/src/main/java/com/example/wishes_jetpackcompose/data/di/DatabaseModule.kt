package com.green.china.di

import android.content.Context
import com.example.wishes_jetpackcompose.data.WallDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = WallDatabase.getDatabase(context)

    @Singleton
    @Provides
    fun provideDao(database: WallDatabase) = database.imageDao()

}