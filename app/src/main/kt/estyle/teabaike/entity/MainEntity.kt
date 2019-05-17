package estyle.teabaike.entity

import estyle.base.entity.NetEntity

data class MainEntity(val data: List<DataEntity>) : NetEntity() {

    data class DataEntity(
        val id: String?,
        val title: String?,
        val source: String?,
        val wap_thumb: String?,
        val create_time: String?,
        val nickname: String?
    )
}
