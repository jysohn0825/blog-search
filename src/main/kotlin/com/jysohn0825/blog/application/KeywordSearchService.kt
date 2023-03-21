package com.jysohn0825.blog.application

import com.jysohn0825.blog.infra.channel.ChannelFactory
import com.jysohn0825.blog.infra.channel.ChannelType
import com.jysohn0825.support.domain.BasePageRequest
import com.jysohn0825.support.utils.getStringYyMMddHH
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class KeywordSearchService(
    private val channelFactory: ChannelFactory,
    private val eventPublisher: ApplicationEventPublisher
) {

    fun searchByKeyword(channel: ChannelType, keyword: String, pageRequest: BasePageRequest): KeywordSearchResponse {
        countKeyword(keyword)
        val portalResponse = channelFactory.searchByKeyword(channel, keyword, pageRequest)
        return portalResponse.changeKeywordSearchResponse(pageRequest)
    }

    private fun countKeyword(keyword: String) {
        eventPublisher.publishEvent(PopularKeywordEvent(keyword))
    }
}
