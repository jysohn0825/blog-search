package com.jysohn0825.blog.infra.channel

import com.jysohn0825.blog.application.KeywordSearchResponse
import com.jysohn0825.support.domain.BasePageRequest

enum class ChannelType {
    KAKAO, NAVER
}

abstract class ChannelSearchByKeywordResponse {
    abstract fun changeKeywordSearchResponse(request: BasePageRequest): KeywordSearchResponse
}
