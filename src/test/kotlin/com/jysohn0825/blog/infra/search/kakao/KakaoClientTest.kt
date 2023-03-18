package com.jysohn0825.blog.infra.search.kakao

import com.jysohn0825.support.domain.BasePageRequest
import com.jysohn0825.support.domain.SortEnum
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
import java.util.Optional

class KakaoClientTest {

    private val properties = KakaoProperties("uri", "path", "key")
    private val restTemplate = mockk<RestTemplate>()
    private val client = KakaoClient(properties, restTemplate)

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

            val actual = client.searchByKeyword(KEYWORD, BasePageRequest(sort, 0, size))

            assertThat(actual.documents[0].title).isEqualTo("정확도순 ${size}위")
        }
    }

    private fun getMappedRequestAndResponseList(size: Int) = mapOf(
        SortEnum.ACCURACY to 0,
        SortEnum.RECENCY to size
    )

    private fun getResponse(
        size: Int,
        sortEnum: SortEnum = SortEnum.ACCURACY,
        isEnd: Boolean = false
    ): KakaoSearchByKeywordResponse {
        val meta = KakaoSearchByKeywordResponse.Meta(1000, 1000, isEnd)
        val documents = buildList {
            for (i in 0..size) {
                add(getDocuments(title = "정확도순 ${i}위", year = i))
            }
        }
        return when (sortEnum) {
            SortEnum.ACCURACY -> KakaoSearchByKeywordResponse(meta, documents.sortedBy { it.title })
            SortEnum.RECENCY -> KakaoSearchByKeywordResponse(meta, documents.sortedByDescending { it.datetime })
        }
    }

    private fun getDocuments(title: String, year: Int) = KakaoSearchByKeywordResponse.Documents(
        title, "내용", "URL", "이름", "thumbnail", LocalDateTime.of(year, 1, 1, 1, 1)
    )

    companion object {
        const val SIZE = 100
        const val KEYWORD = "키워드"
    }
}
