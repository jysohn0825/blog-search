package com.jysohn0825.blog.infra.channel.naver

import com.fasterxml.jackson.annotation.JsonFormat
import com.jysohn0825.blog.application.ContentSummary
import com.jysohn0825.blog.application.KeywordSearchResponse
import com.jysohn0825.blog.infra.channel.ChannelSearchByKeywordResponse
import com.jysohn0825.blog.infra.channel.ChannelType
import com.jysohn0825.support.domain.BasePageRequest
import com.jysohn0825.support.domain.BasePageResponse
import com.jysohn0825.support.utils.convertLocalDateTime
import java.time.LocalDate

data class NaverSearchByKeywordResponse(
    val total: Int = 0,
    val start: Int = 0,
    val display: Int = 0,
    val items: List<Documents> = emptyList()
) : ChannelSearchByKeywordResponse() {

    data class Documents(
        val title: String = "",
        @JsonFormat(pattern = "yyyyMMdd")
        val postdate: LocalDate = LocalDate.now(),
        val description: String = "",
        val bloggerlink: String = "",
        val bloggername: String = ""
    )

    override fun changeKeywordSearchResponse(request: BasePageRequest): KeywordSearchResponse =
        KeywordSearchResponse(
            BasePageResponse(start, items.size, (items.size != display)),
            items.map { ContentSummary(it.title, it.description, it.bloggerlink, it.postdate.convertLocalDateTime(), ChannelType.NAVER) }
        )
}
