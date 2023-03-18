package com.jysohn0825.blog.application

import com.jysohn0825.blog.infra.search.kakao.KakaoClient
import com.jysohn0825.support.domain.BasePageRequest
import org.springframework.stereotype.Service

@Service
class KeywordSearchService(
    private val kakaoClient: KakaoClient
) {

    fun searchByKeyword(keyword: String, pageRequest: BasePageRequest): KeywordSearchResponse {
        return kakaoClient.searchByKeyword(keyword, pageRequest).changeKeywordSearchResponse(pageRequest)
    }
}
