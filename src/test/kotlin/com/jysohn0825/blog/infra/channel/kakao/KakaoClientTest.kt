package com.jysohn0825.blog.infra.channel.kakao

import com.jysohn0825.blog.infra.channel.kakao.KakaoClient.Companion.PAGE_MAX_LIMIT
import com.jysohn0825.blog.infra.channel.kakao.KakaoClient.Companion.SIZE_MAX_LIMIT
import com.jysohn0825.blog.support.domain.BasePageRequest
import com.jysohn0825.blog.support.domain.SortEnum
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import java.time.LocalDateTime
import java.util.*

class KakaoClientTest {

    private val restTemplate = mockk<RestTemplate>()
    private val client = KakaoClient(restTemplate)

    @Test
    fun `페이지 유효성 체크`() {
        assertThrows<IllegalArgumentException> {
            client.checkPageRequestValid(BasePageRequest(page = PAGE_MAX_LIMIT + 1))
        }
    }

    @Test
    fun `사이즈 유효성 체크`() {
        assertThrows<IllegalArgumentException> {
            client.checkPageRequestValid(BasePageRequest(page = SIZE_MAX_LIMIT + 1))
        }
    }

    @Test
    fun `API Key 오류 확인`() {
        every {
            restTemplate.exchange(any(), KakaoSearchByKeywordResponse::class.java)
        } throws HttpClientErrorException(HttpStatus.UNAUTHORIZED)

        assertThrows<RuntimeException> { client.searchByKeyword(KEYWORD, BasePageRequest(size = SIZE)) }
    }

    @Test
    fun `응답 중 body가 없을 경우 emptyList 확인`() {
        every {
            restTemplate.exchange(any(), KakaoSearchByKeywordResponse::class.java)
        } returns ResponseEntity.of(Optional.ofNullable(null))

        val actual = client.searchByKeyword(KEYWORD, BasePageRequest(size = SIZE))

        assertThat(actual.documents).isEmpty()
    }

    @Test
    fun `정렬에 따른 정상 응답 확인`() {
        getMappedRequestAndResponseList(SIZE).forEach { (sort, size) ->
            every {
                restTemplate.exchange(any(), KakaoSearchByKeywordResponse::class.java)
            } returns ResponseEntity.of(Optional.of(getResponse(size, sort)))

            val actual = client.searchByKeyword(KEYWORD, BasePageRequest(sort, 1, size))

            assertThat(actual.documents[0].title).isEqualTo("정확도순 ${size}위")
        }
    }

    @Test
    fun `키워드에 공백이 있을 경우 2번째가 키워드임을 확인`() {
        val keyword = "http://kakao.com 카카오"

        val actual = client.extractKeyword(keyword)

        assertThat(actual).isEqualTo("카카오")
    }

    @Test
    fun `키워드에 공백이 없을 경우 키워드 확인`() {
        val keyword = "http://kakao.com카카오"

        val actual = client.extractKeyword(keyword)

        assertThat(actual).isEqualTo("http://kakao.com카카오")
    }

    @Test
    fun `키워드에 공백이 2개 이상일 경우 에러 확인`() {
        val keyword = "http://kakao.com 카카오 톡"

        assertThrows<IllegalArgumentException> { client.extractKeyword(keyword) }
    }

    private fun getMappedRequestAndResponseList(size: Int) = mapOf(
        SortEnum.ACCURACY to 1,
        SortEnum.RECENCY to size
    )

    private fun getResponse(
        size: Int,
        sortEnum: SortEnum = SortEnum.ACCURACY,
        isEnd: Boolean = false
    ): KakaoSearchByKeywordResponse {
        val meta = KakaoSearchByKeywordResponse.Meta(1000, 1000, isEnd)
        val documents = buildList {
            for (i in 1..size) {
                add(getDocuments(title = "정확도순 ${i}위", year = i))
            }
        }
        return when (sortEnum) {
            SortEnum.ACCURACY -> KakaoSearchByKeywordResponse(meta, documents.sortedBy { it.title })
            SortEnum.RECENCY -> KakaoSearchByKeywordResponse(meta, documents.sortedByDescending { it.datetime })
        }
    }

    private fun getDocuments(title: String, year: Int) = KakaoSearchByKeywordResponse.Documents(
        title, LocalDateTime.of(year, 1, 1, 1, 1)
    )

    companion object {
        const val SIZE = 30
        const val KEYWORD = "키워드"
    }
}
