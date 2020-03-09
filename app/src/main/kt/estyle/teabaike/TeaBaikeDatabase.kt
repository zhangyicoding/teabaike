package estyle.teabaike

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import estyle.teabaike.datasource.database.CollectionDao
import estyle.teabaike.config.Db
import estyle.teabaike.entity.ContentEntity

@Database(entities = [ContentEntity.DataEntity::class], version = Db.VERSION)
abstract class TeaBaikeDatabase : RoomDatabase() {

    abstract fun collectionDao(): CollectionDao

    companion object {
        lateinit var INSTANCE: TeaBaikeDatabase

        fun init(context: Context) {
            INSTANCE = Room.databaseBuilder(context, TeaBaikeDatabase::class.java, Db.NAME)
                .build()
        }
    }
}