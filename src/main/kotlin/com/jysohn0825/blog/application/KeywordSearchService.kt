package com.jysohn0825.blog.application

import com.jysohn0825.blog.infra.channel.ChannelFactory
import com.jysohn0825.blog.infra.channel.ChannelType
import com.jysohn0825.support.domain.BasePageRequest
import org.springframework.stereotype.Service

@Service
class KeywordSearchService(
    private val channelFactory: ChannelFactory
) {

    fun searchByKeyword(channel: ChannelType, keyword: String, pageRequest: BasePageRequest): KeywordSearchResponse {
        return channelFactory.searchByKeyword(channel, keyword, pageRequest).changeKeywordSearchResponse(pageRequest)
    }
}
