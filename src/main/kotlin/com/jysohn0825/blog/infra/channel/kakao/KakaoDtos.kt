package com.jysohn0825.blog.infra.channel.kakao

import com.fasterxml.jackson.annotation.JsonProperty
import com.jysohn0825.blog.application.ContentSummary
import com.jysohn0825.blog.application.KeywordSearchResponse
import com.jysohn0825.blog.infra.channel.ChannelSearchByKeywordResponse
import com.jysohn0825.support.domain.BasePageRequest
import com.jysohn0825.support.domain.BasePageResponse
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
//        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
//        val datetime: LocalDateTime = LocalDateTime.now(),
        val datetime: Date = Date(),
        val contents: String = "",
        val url: String = "",
        @JsonProperty("blogname") val blogName: String = "",
        val thumbnail: String = ""
    )

    override fun changeKeywordSearchResponse(request: BasePageRequest): KeywordSearchResponse =
        KeywordSearchResponse(
            meta.let { BasePageResponse(getPage(it, request), it.isEnd) },
            documents.map { ContentSummary(it.title, it.contents, it.url, it.thumbnail, it.datetime) }
        )

    private fun getPage(meta: Meta, request: BasePageRequest): Int =
        if (meta.isEnd) meta.pageableCount / request.size else request.page
}