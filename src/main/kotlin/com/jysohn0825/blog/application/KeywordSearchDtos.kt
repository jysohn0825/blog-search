package com.jysohn0825.blog.application

import com.jysohn0825.blog.infra.channel.ChannelType
import com.jysohn0825.blog.support.domain.BasePageResponse
import java.time.LocalDateTime

data class KeywordSearchResponse(
    val pageResponse: BasePageResponse,
    val channel: ChannelType,
    val contents: List<ContentSummary>
)

data class ContentSummary(
    val title: String,
    var contents: String,
    val url: String,
    val dateTime: LocalDateTime,
    val thumbnail: String = ""
) {
    init {
        if (contents.length > CONTENT_LIMIT_LENGTH) contents = contents.substring(0 until CONTENT_LIMIT_LENGTH)
    }

    companion object {
        const val CONTENT_LIMIT_LENGTH = 1000
    }
}
