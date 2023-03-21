package com.jysohn0825.blog.infra.channel.kakao

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.jysohn0825.blog.application.ContentSummary
import com.jysohn0825.blog.application.KeywordSearchResponse
import com.jysohn0825.blog.infra.channel.ChannelSearchByKeywordResponse
import com.jysohn0825.blog.infra.channel.ChannelType
import com.jysohn0825.support.domain.BasePageRequest
import com.jysohn0825.support.domain.BasePageResponse
import java.time.LocalDateTime
import java.util.*

data class KakaoSearchByKeywordResponse(
    val meta: Meta = Meta(),
    val documents: List<Documents> = emptyList()
) : ChannelSearchByKeywordResponse() {

    data class Meta(
        @JsonProperty("total_count") val totalCount: Int = 0,
        @JsonProperty("pageable_count") val pageableCount: Int = 0,
        @JsonProperty("is_end") val isEnd: Boolean = true
    )

    data class Documents(
        val title: String = "",
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "Asia/Seoul")
        val datetime: LocalDateTime = LocalDateTime.now(),
        val contents: String = "",
        val url: String = "",
        val blogName: String = "",
        val thumbnail: String = ""
    )

    override fun changeKeywordSearchResponse(request: BasePageRequest): KeywordSearchResponse =
        KeywordSearchResponse(
            meta.let { BasePageResponse(getPage(it, request), documents.size, it.isEnd) },
            ChannelType.KAKAO,
            documents.map { ContentSummary(it.title, it.contents, it.url, it.datetime, it.thumbnail) }
        )

    private fun getPage(meta: Meta, request: BasePageRequest): Int =
        if (meta.isEnd)request.page - (request.page * request.size - meta.pageableCount) / request.size
        else request.page
}
