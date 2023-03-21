package com.jysohn0825.blog.infra.channel

import com.jysohn0825.blog.infra.channel.kakao.KakaoClient
import com.jysohn0825.blog.infra.channel.naver.NaverClient
import com.jysohn0825.support.domain.BasePageRequest
import org.springframework.stereotype.Component

@Component
class ChannelFactory(
    private val kakaoClient: KakaoClient,
    private val naverClient: NaverClient
) {

    fun searchByKeyword(
        channel: ChannelType,
        keyword: String,
        request: BasePageRequest
    ): ChannelSearchByKeywordResponse {
        return when (channel) {
            ChannelType.KAKAO -> kakaoClient.searchByKeyword(keyword, request)
            ChannelType.NAVER -> naverClient.searchByKeyword(keyword, request)
        }
    }

    fun extractKeyword(channel: ChannelType, keyword: String): String {
        return when (channel) {
            ChannelType.KAKAO -> kakaoClient.extractKeyword(keyword)
            ChannelType.NAVER -> naverClient.extractKeyword(keyword)
        }
    }
}
