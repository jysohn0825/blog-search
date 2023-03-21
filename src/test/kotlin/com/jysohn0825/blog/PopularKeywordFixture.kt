package com.jysohn0825.blog

import com.jysohn0825.blog.domain.popularsearchterm.PopularKeyword
import com.jysohn0825.blog.domain.popularsearchterm.PopularKeywordPk

fun getPopularKeywordPk(
    baseHour: String = "23031810",
    keyword: String = "키워드"
) = PopularKeywordPk(baseHour, keyword)

fun getPopularKeyword(
    pk: PopularKeywordPk = getPopularKeywordPk(),
    count: Long = 0L,
    newFlag: Boolean = false
) = PopularKeyword(pk, count, newFlag)
