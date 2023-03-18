package com.jysohn0825.support.domain

enum class SortEnum {
    ACCURACY, RECENCY
}

open class BasePageRequest(
    open val sort: SortEnum = SortEnum.ACCURACY,
    open val page: Int = 1,
    open val size: Int = 10
)

data class BasePageResponse(
    val total: Int,
    val page: Int,
    val isEnd: Boolean
)
