package com.jysohn0825.blog.infra.channel

import com.jysohn0825.support.domain.BasePageRequest

interface ChannelClient {
    fun checkPageRequestValid(request: BasePageRequest)
    fun searchByKeyword(keyword: String, request: BasePageRequest): ChannelSearchByKeywordResponse
}
