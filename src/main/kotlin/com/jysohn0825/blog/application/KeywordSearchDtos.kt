package com.jysohn0825.blog.application

import com.jysohn0825.blog.infra.search.kakao.KakaoSearchByKeywordResponse
import com.jysohn0825.support.domain.BasePageRequest
import com.jysohn0825.support.domain.BasePageResponse
import java.time.LocalDateTime
import java.util.*

data class KeywordSearchResponse(
    val pageResponse: BasePageResponse,
    val contents: List<ContentSummary>
) {
    data class ContentSummary(
        val title: String,
        val contents: String,
        val url: String,
        val thumbnail: String,
        val datetime: Date //LocalDateTime
    )
}
