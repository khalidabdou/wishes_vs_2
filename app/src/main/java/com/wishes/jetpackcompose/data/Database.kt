package com.wishes.jetpackcompose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wishes.jetpackcompose.data.entities.Category
import com.wishes.jetpackcompose.data.entities.Image
import com.wishes.jetpackcompose.utlis.Const.Companion.DATABASE_NAME


@Database(
    entities = [
        Image::class,
        Category::class,
    ],
    version = 16,
    exportSchema = false
)
@TypeConverters(imageTypeConverter::class)
abstract class WallDatabase : RoomDatabase() {
    abstract fun imageDao(): IDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: WallDatabase? = null

        fun getDatabase(context: Context): WallDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WallDatabase::class.java,
                    DATABASE_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}

