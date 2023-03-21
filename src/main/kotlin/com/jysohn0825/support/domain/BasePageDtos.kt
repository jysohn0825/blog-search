package com.jysohn0825.support.domain

enum class SortEnum {
    ACCURACY, RECENCY
}

data class BasePageRequest(
    val sort: SortEnum = SortEnum.ACCURACY,
    val page: Int = 1,
    val size: Int = 10
)

data class BasePageResponse(
    val page: Int,
    val size: Int,
    val isEnd: Boolean
)
