package com.jysohn0825.blog.infra.channel

import com.jysohn0825.blog.infra.channel.kakao.KakaoClient
import com.jysohn0825.support.domain.BasePageRequest
import org.springframework.stereotype.Component

@Component
class ChannelFactory(
    private val kakaoClient: KakaoClient
) {

    fun searchByKeyword(channel: ChannelType, keyword: String, request: BasePageRequest): ChannelSearchByKeywordResponse {
        return when (channel) {
            ChannelType.KAKAO -> kakaoClient.searchByKeyword(keyword, request)
            ChannelType.NAVER -> kakaoClient.searchByKeyword(keyword, request) // TODO NAVER API 로 변경
        }
    }
}
