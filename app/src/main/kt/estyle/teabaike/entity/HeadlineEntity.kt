package estyle.teabaike.entity

import estyle.base.entity.NetEntity

data class HeadlineEntity(val data: List<DataEntity>) : NetEntity() {

    data class DataEntity(
        val id: String?,
        val title: String?,
        val link: String?,
        val image: String?
    )
}
