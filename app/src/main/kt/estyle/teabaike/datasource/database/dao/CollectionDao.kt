package estyle.teabaike.datasource.database.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import estyle.teabaike.config.Db
import estyle.teabaike.entity.ContentEntity
import io.reactivex.Single

@Dao
interface CollectionDao {

    @Insert
    fun insert(content: ContentEntity): Single<Long>

    @Delete
    fun delete(contents: List<ContentEntity>): Single<Int>

    @Query("SELECT * FROM ${Db.TABLE_COLLECTION} ORDER BY ${Db.PRIMARY_KEY} DESC")
//    fun queryAll(): Observable<List<ContentListEntity>>
    fun queryAll(): DataSource.Factory<Int, ContentEntity>
}
