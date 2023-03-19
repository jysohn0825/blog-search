package com.jysohn0825.blog.application

import com.jysohn0825.support.domain.BasePageResponse
import java.util.Date

data class KeywordSearchResponse(
    val pageResponse: BasePageResponse,
    val contents: List<ContentSummary>
)

data class ContentSummary(
    val title: String,
    val contents: String,
    val url: String,
    val thumbnail: String,
    val datetime: Date // LocalDateTime
)
