package estyle.teabaike

import androidx.room.Database
import androidx.room.RoomDatabase
import estyle.teabaike.datasource.database.CollectionDao
import estyle.teabaike.config.Db
import estyle.teabaike.entity.ContentEntity

@Database(entities = [ContentEntity.DataEntity::class], version = Db.VERSION)
abstract class TeaBaikeDatabase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao
}