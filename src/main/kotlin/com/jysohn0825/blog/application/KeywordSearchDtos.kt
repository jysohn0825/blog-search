package com.jysohn0825.blog.application

import com.jysohn0825.support.domain.BasePageResponse
import java.time.LocalDateTime

data class KeywordSearchResponse(
    val pageResponse: BasePageResponse,
    val contents: List<ContentSummary>
)

data class ContentSummary(
    val title: String,
    var contents: String,
    val url: String,
    val thumbnail: String,
    val dateTime: LocalDateTime
) {
    init {
        if (contents.length > CONTENT_LIMIT_LENGTH) contents = contents.substring(0 until CONTENT_LIMIT_LENGTH)
    }

    companion object {
        const val CONTENT_LIMIT_LENGTH = 1000
    }
}
