package estyle.base.entity

data class HttpEntity<D>(
    val errorCode: Int,
    val errorMessage: String,
    val data: D?
) {
    companion object {
        const val SUCCESS = 200
    }
}
