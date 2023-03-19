package com.jysohn0825.blog.application

import com.jysohn0825.blog.infra.search.kakao.KakaoClient
import com.jysohn0825.blog.infra.search.kakao.KakaoClientTest.Companion.KEYWORD
import com.jysohn0825.blog.infra.search.kakao.KakaoSearchByKeywordResponse
import com.jysohn0825.support.domain.BasePageRequest
import com.jysohn0825.support.domain.SortEnum
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.util.*

class KeywordSearchServiceTest {
    private val kakaoClient = mockk<KakaoClient>()
    val service = KeywordSearchService(kakaoClient)

    private val list = listOf(
        KakaoSearchByKeywordResponse.Documents("정확도9", Date(2000)), //LocalDateTime.of(2000, 1, 1, 1, 1)),
        KakaoSearchByKeywordResponse.Documents("정확도8", Date(2001)), //LocalDateTime.of(2001, 1, 1, 1, 1)),
        KakaoSearchByKeywordResponse.Documents("정확도7", Date(2002)), //LocalDateTime.of(2002, 1, 1, 1, 1)),
        KakaoSearchByKeywordResponse.Documents("정확도6", Date(2003)), //LocalDateTime.of(2003, 1, 1, 1, 1)),
        KakaoSearchByKeywordResponse.Documents("정확도5", Date(2004)), //LocalDateTime.of(2004, 1, 1, 1, 1)),
        KakaoSearchByKeywordResponse.Documents("정확도4", Date(2005)), //LocalDateTime.of(2005, 1, 1, 1, 1)),
        KakaoSearchByKeywordResponse.Documents("정확도3", Date(2006)), //LocalDateTime.of(2006, 1, 1, 1, 1)),
        KakaoSearchByKeywordResponse.Documents("정확도2", Date(2007)), // LocalDateTime.of(2007, 1, 1, 1, 1)),
        KakaoSearchByKeywordResponse.Documents("정확도1", Date(2008)), //LocalDateTime.of(2008, 1, 1, 1, 1)),
        KakaoSearchByKeywordResponse.Documents("정확도0", Date(2009)), //LocalDateTime.of(2009, 1, 1, 1, 1))
    )

    @Test
    fun `정확도순 정렬을 확인한다`() {
        val sort = SortEnum.ACCURACY
        val page = 1
        val size = 1
        val pageRequest = BasePageRequest(sort, page, size)
        val response = getResponse(sort, page, size)

        every { kakaoClient.searchByKeyword(any(), any()) } returns response

        val actual = service.searchByKeyword(KEYWORD, pageRequest)

        assertThat(actual.contents.first().title).isEqualTo(list.first().title)
    }

    @Test
    fun `최신순 정렬을 확인한다`() {
        val sort = SortEnum.RECENCY
        val page = 1
        val size = 1
        val pageRequest = BasePageRequest(sort, page, size)
        val response = getResponse(sort, page, size)

        every { kakaoClient.searchByKeyword(any(), any()) } returns response

        val actual = service.searchByKeyword(KEYWORD, pageRequest)

        assertThat(actual.contents.last().title).isEqualTo(list.last().title)
    }

    @Test
    fun `페이징을 확인한다`() {
        val sort = SortEnum.ACCURACY
        val page = 2
        val size = 3
        val pageRequest = BasePageRequest(sort, page, size)
        val response = getResponse(sort, page, size)

        every { kakaoClient.searchByKeyword(any(), any()) } returns response

        val actual = service.searchByKeyword(KEYWORD, pageRequest)

        assertThat(actual.contents.first().title).isEqualTo(list[page * size - size].title)
        assertThat(actual.contents.last().title).isEqualTo(list[page * size - 1].title)
    }

    @Test
    fun `마지막 페이지를 확인한다`() {
        val sort = SortEnum.ACCURACY
        val page = 1
        val size = list.size + 1
        val pageRequest = BasePageRequest(sort, page, size)
        val response = getResponse(sort, page, size, true)

        every { kakaoClient.searchByKeyword(any(), any()) } returns response

        val actual = service.searchByKeyword(KEYWORD, pageRequest)

        assertThat(actual.pageResponse.page).isEqualTo(page)
        assertThat(actual.pageResponse.isEnd).isEqualTo(true)
    }


    private fun getResponse(
        sort: SortEnum,
        page: Int,
        size: Int,
        isEnd: Boolean = false
    ): KakaoSearchByKeywordResponse {
        val meta = KakaoSearchByKeywordResponse.Meta(list.size * 2, size, isEnd)
        val documents = list.let { response ->
            when (sort) {
                SortEnum.ACCURACY -> response.sortedByDescending { it.title }
                SortEnum.RECENCY -> response.sortedByDescending { it.datetime }
            }
        }.let {
            if(isEnd) it.slice(meta.pageableCount / size + 1 until meta.pageableCount / size + list.size - 1)
            else it.slice(page * size - size until page * size)
        }
        return KakaoSearchByKeywordResponse(meta, documents)
    }
}
