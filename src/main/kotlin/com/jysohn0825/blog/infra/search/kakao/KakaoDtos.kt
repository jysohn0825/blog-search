package com.jysohn0825.blog.infra.search.kakao

import com.fasterxml.jackson.annotation.JsonProperty
import com.jysohn0825.blog.application.KeywordSearchResponse
import com.jysohn0825.support.domain.BasePageRequest
import com.jysohn0825.support.domain.BasePageResponse
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class KakaoSearchByKeywordResponse(
    val meta: Meta = Meta(),
    val documents: List<Documents> = emptyList()
) {

    data class Meta(
        @JsonProperty("total_count") val totalCount: Int = 0,
        @JsonProperty("pageable_count") val pageableCount: Int = 0,
        @JsonProperty("is_end") val isEnd: Boolean = true
    )

    data class Documents(
        val title: String = "",
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
        val datetime: LocalDateTime = LocalDateTime.now(),
        val contents: String = "",
        val url: String = "",
        @JsonProperty("blogname") val blogName: String = "",
        val thumbnail: String = ""
    )

    fun changeKeywordSearchResponse(request: BasePageRequest): KeywordSearchResponse = KeywordSearchResponse(
        meta.let { BasePageResponse(getPage(it, request), it.isEnd) },
        documents.map { KeywordSearchResponse.ContentSummary(it.title, it.contents, it.url, it.thumbnail, it.datetime) }
    )

    private fun getPage(meta: Meta, request: BasePageRequest): Int =
        if (meta.isEnd) meta.pageableCount / request.size + 1 else request.page
}
