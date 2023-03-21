package com.jysohn0825.blog.infra.channel.naver

import com.jysohn0825.blog.infra.channel.naver.NaverClient.Companion.PAGE_MAX_LIMIT
import com.jysohn0825.blog.infra.channel.naver.NaverClient.Companion.SIZE_MAX_LIMIT
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
import java.time.LocalDate
import java.util.*

class NaverClientTest {

    private val restTemplate = mockk<RestTemplate>()
    private val client = NaverClient(restTemplate)

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
            restTemplate.exchange(any(), NaverSearchByKeywordResponse::class.java)
        } throws HttpClientErrorException(HttpStatus.UNAUTHORIZED)

        assertThrows<RuntimeException> { client.searchByKeyword(KEYWORD, BasePageRequest(size = SIZE)) }
    }

    @Test
    fun `응답 중 body가 없을 경우 emptyList 확인`() {
        every {
            restTemplate.exchange(any(), NaverSearchByKeywordResponse::class.java)
        } returns ResponseEntity.of(Optional.ofNullable(null))

        val actual = client.searchByKeyword(KEYWORD, BasePageRequest(size = SIZE))

        assertThat(actual.items).isEmpty()
    }

    @Test
    fun `정렬에 따른 정상 응답 확인`() {
        getMappedRequestAndResponseList(SIZE).forEach { (sort, size) ->
            every {
                restTemplate.exchange(any(), NaverSearchByKeywordResponse::class.java)
            } returns ResponseEntity.of(Optional.of(getResponse(size, sort)))

            val actual = client.searchByKeyword(KEYWORD, BasePageRequest(sort, 1, size))

            assertThat(actual.items[0].title).isEqualTo("정확도순 ${size}위")
        }
    }

    @Test
    fun `키워드에 공백이 없을 경우 키워드 확인`() {
        val keyword = "http://kakao.com카카오"

        val actual = client.extractKeyword(keyword)

        assertThat(actual).isEqualTo("http://kakao.com카카오")
    }

    private fun getMappedRequestAndResponseList(size: Int) = mapOf(
        SortEnum.ACCURACY to 1,
        SortEnum.RECENCY to size
    )

    private fun getResponse(
        size: Int,
        sortEnum: SortEnum = SortEnum.ACCURACY
    ): NaverSearchByKeywordResponse {
        val items = buildList {
            for (i in 1..size) {
                add(getDocuments(title = "정확도순 ${i}위", year = i))
            }
        }
        return when (sortEnum) {
            SortEnum.ACCURACY -> NaverSearchByKeywordResponse(size * 2, size, size, items.sortedBy { it.title })
            SortEnum.RECENCY -> NaverSearchByKeywordResponse(size * 2, size, size, items.sortedByDescending { it.postdate })
        }
    }

    private fun getDocuments(title: String, year: Int) = NaverSearchByKeywordResponse.Documents(
        title, LocalDate.of(year, 1, 1)
    )

    companion object {
        const val SIZE = 30
        const val KEYWORD = "키워드"
    }
}
