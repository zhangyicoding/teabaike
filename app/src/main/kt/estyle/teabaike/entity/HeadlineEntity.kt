package estyle.teabaike.entity

data class HeadlineEntity(val data: List<DataEntity>) : BaseEntity() {

    data class DataEntity(
        val id: String?,
        val title: String?,
        val link: String?,
        val image: String?
    )
}
