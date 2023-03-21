package com.jysohn0825.blog.application

import com.jysohn0825.blog.domain.popularsearchterm.PopularKeyword
import com.jysohn0825.blog.domain.popularsearchterm.PopularKeywordPk
import com.jysohn0825.support.utils.getStringYyMMddHH
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class PopularKeywordResponseTest{

    @Test
    fun `DB 응답을 API 응답 치환 시 랭킹 확인`(){
        val list = listOf(
            PopularKeyword(PopularKeywordPk(getStringYyMMddHH(),"키워드1"), 3),
            PopularKeyword(PopularKeywordPk(getStringYyMMddHH(),"키워드2"), 2),
            PopularKeyword(PopularKeywordPk(getStringYyMMddHH(),"키워드3"), 1),
        )
        val response = PopularKeywordResponse.of(list)

        assertThat(response.popularKeywords).contains(
            PopularKeywordResponse.PopularKeywordRank(1, "키워드1", 3),
            PopularKeywordResponse.PopularKeywordRank(2, "키워드2", 2),
            PopularKeywordResponse.PopularKeywordRank(3, "키워드3", 1),
        )
    }
}

