package estyle.teabaike.entity

data class MainEntity(val data: List<DataEntity>) : BaseEntity() {

    data class DataEntity(
        val id: String?,
        val title: String?,
        val source: String?,
        val wap_thumb: String?,
        val create_time: String?,
        val nickname: String?
    )
}
