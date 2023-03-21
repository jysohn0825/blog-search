package com.jysohn0825.blog.application

import com.jysohn0825.blog.domain.popularsearchterm.PopularKeyword
import com.jysohn0825.blog.domain.popularsearchterm.PopularKeywordPk
import com.jysohn0825.blog.support.utils.getStringYyMMddHH

data class PopularKeywordResponse(
    val popularKeywords: List<PopularKeywordRank>
) {
    data class PopularKeywordRank(
        val rank: Int,
        val keyword: String,
        val count: Long
    )

    companion object {
        fun of(keywords: List<PopularKeyword>) = PopularKeywordResponse(
            keywords.mapIndexed { index, it ->
                PopularKeywordRank(index + 1, it.keyword, it.count)
            }
        )
    }
}

data class PopularKeywordEvent(
    val pk: PopularKeywordPk
) {
    constructor(keyword: String, baseHour: String = getStringYyMMddHH()) : this(PopularKeywordPk(baseHour, keyword))
}
