package com.jysohn0825.blog.application

import com.jysohn0825.blog.infra.channel.ChannelFactory
import com.jysohn0825.blog.infra.channel.ChannelType
import com.jysohn0825.support.domain.BasePageRequest
import org.springframework.stereotype.Service

@Service
class KeywordSearchService(
    private val channelFactory: ChannelFactory,
    private val keywordCountEventService: KeywordCountEventService
) {

    fun searchByKeyword(channel: ChannelType, keyword: String, pageRequest: BasePageRequest): KeywordSearchResponse {
        countKeyword(channel, keyword)
        val portalResponse = channelFactory.searchByKeyword(channel, keyword, pageRequest)
        return portalResponse.changeKeywordSearchResponse(pageRequest)
    }

    private fun countKeyword(channel: ChannelType, keyword: String) {
        val realKeyword = channelFactory.extractKeyword(channel, keyword)
        keywordCountEventService.publish(PopularKeywordEvent(realKeyword))
    }
}
