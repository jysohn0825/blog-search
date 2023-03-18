package com.jysohn0825.blog.application

import com.jysohn0825.blog.infra.search.kakao.KakaoSearchByKeywordResponse
import com.jysohn0825.support.domain.BasePageRequest
import com.jysohn0825.support.domain.BasePageResponse
import java.time.LocalDateTime

data class KeywordSearchResponse(
    val pageResponse: BasePageResponse,
    val contents: List<ContentSummary>
) {
    data class ContentSummary(
        val title: String,
        val contents: String,
        val url: String,
        val thumbnail: String,
        val datetime: LocalDateTime
    )

    companion object {
        fun of(openApiResponse: KakaoSearchByKeywordResponse, request: BasePageRequest) = KeywordSearchResponse(
            openApiResponse.meta.let { BasePageResponse(getPage(it, request), it.isEnd) },
            openApiResponse.documents.map { ContentSummary(it.title, it.contents, it.url, it.thumbnail, it.datetime) }
        )

        private fun getPage(meta: KakaoSearchByKeywordResponse.Meta, request: BasePageRequest): Int =
            if (meta.isEnd) meta.pageableCount / request.size + 1 else request.page
    }
}
