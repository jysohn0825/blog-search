package com.jysohn0825.blog.infra.channel

import com.jysohn0825.blog.infra.channel.kakao.KakaoClient
import com.jysohn0825.blog.infra.channel.kakao.KakaoClientTest
import com.jysohn0825.blog.infra.channel.kakao.KakaoSearchByKeywordResponse
import com.jysohn0825.blog.infra.channel.naver.NaverClient
import com.jysohn0825.blog.infra.channel.naver.NaverClientTest
import com.jysohn0825.blog.infra.channel.naver.NaverSearchByKeywordResponse
import com.jysohn0825.blog.support.domain.BasePageRequest
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

class ChannelFactoryTest {
    private val kakao = mockk<KakaoClient>()
    private val naver = mockk<NaverClient>()
    private val factory = ChannelFactory(kakao, naver)

    @Test
    fun `Kakao 채널로 분기하여 조회한다`() {
        every { kakao.searchByKeyword(any(), any()) } returns KakaoSearchByKeywordResponse()

        factory.searchByKeyword(ChannelType.KAKAO, KakaoClientTest.KEYWORD, BasePageRequest())

        verify(exactly = 1) { kakao.searchByKeyword(KakaoClientTest.KEYWORD, BasePageRequest()) }
        verify(exactly = 0) { naver.searchByKeyword(KakaoClientTest.KEYWORD, BasePageRequest()) }
    }

    @Test
    fun `Naver 채널로 분기하여 조회한다`() {
        every { naver.searchByKeyword(any(), any()) } returns NaverSearchByKeywordResponse()

        factory.searchByKeyword(ChannelType.NAVER, NaverClientTest.KEYWORD, BasePageRequest())

        verify(exactly = 0) { kakao.searchByKeyword(NaverClientTest.KEYWORD, BasePageRequest()) }
        verify(exactly = 1) { naver.searchByKeyword(NaverClientTest.KEYWORD, BasePageRequest()) }
    }
}
