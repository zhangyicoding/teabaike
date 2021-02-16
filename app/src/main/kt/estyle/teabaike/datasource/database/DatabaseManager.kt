package estyle.teabaike.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import estyle.teabaike.config.Db
import estyle.teabaike.datasource.database.dao.CollectionDao
import estyle.teabaike.entity.ContentEntity

@Database(entities = [ContentEntity::class], version = Db.VERSION)
abstract class DatabaseManager : RoomDatabase() {

    /**
     * 收藏列表
     */
    abstract fun collectionDao(): CollectionDao

    companion object {
        lateinit var INSTANCE: DatabaseManager

        fun init(context: Context) {
            INSTANCE = Room.databaseBuilder(context, DatabaseManager::class.java, Db.NAME)
                .build()
        }
    }
}