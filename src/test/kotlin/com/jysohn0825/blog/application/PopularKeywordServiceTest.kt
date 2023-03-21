package com.jysohn0825.blog.application

import com.jysohn0825.blog.domain.popularsearchterm.PopularKeyword
import com.jysohn0825.blog.domain.popularsearchterm.PopularKeywordPk
import com.jysohn0825.blog.domain.popularsearchterm.PopularKeywordRepository
import com.jysohn0825.blog.domain.popularsearchterm.findByPkOrNew
import com.jysohn0825.blog.getPopularKeyword
import com.jysohn0825.blog.getPopularKeywordPk
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.dao.DataIntegrityViolationException
import javax.persistence.EntityExistsException

class PopularKeywordServiceTest {
    private val repository = mockk<PopularKeywordRepository>()
    private val service = PopularKeywordService(repository)

    @Test
    fun `인기 검색어 조회 확인`() {
        val baseHour = "23031810"
        val response = listOf(
            PopularKeyword(PopularKeywordPk(baseHour, "키워드1"), 2),
            PopularKeyword(PopularKeywordPk(baseHour, "키워드2"), 1)
        )
        every { repository.findTop10ByPkBaseHourOrderByCountDesc(any()) } returns response

        val actual = service.findTop10PopularKeyword(baseHour)

        assertThat(actual.popularKeywords).containsExactly(
            PopularKeywordResponse.PopularKeywordRank(1, "키워드1", 2),
            PopularKeywordResponse.PopularKeywordRank(2, "키워드2", 1)
        )
    }

    @Test
    fun `DB 동시성 이슈로 발생한 에러일 경우 재 수행 여부 확인`() {
        every { repository.findByPkOrNew(any()) } returns getPopularKeyword()
        every { repository.save(any()) } throws DataIntegrityViolationException("DB 동시성 이슈")
        assertThrows<RuntimeException> { service.findAndSave(getPopularKeywordPk()) }
    }

    @Test
    fun `엔티티 동시성 이슈로 발생한 에러일 경우 재 수행 여부 확인`() {
        every { repository.findByPkOrNew(any()) } returns getPopularKeyword()
        every { repository.save(any()) } throws EntityExistsException("엔티티 동시성 이슈")
        assertThrows<RuntimeException> { service.findAndSave(getPopularKeywordPk()) }
    }
}
