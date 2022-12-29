package com.green.china.di

import android.content.Context
import com.wishes.jetpackcompose.data.WallDatabase
import com.wishes.jetpackcompose.preferences.implimentation.DataStoreRepositoryImpl
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


    @Singleton
    @Provides
    fun provideDataStoreRepository(
        @ApplicationContext app: Context
    ): DataStoreRepositoryImpl = DataStoreRepositoryImpl(app)

}