package com.jysohn0825.blog.application

import com.jysohn0825.blog.infra.search.kakao.KakaoClient
import com.jysohn0825.blog.infra.search.kakao.KakaoSearchByKeywordResponse
import com.jysohn0825.support.domain.BasePageRequest
import org.springframework.stereotype.Service

@Service
class SearchService(
    private val kakaoClient: KakaoClient
) {

    fun searchByKeyword(keyword: String, pageRequest: BasePageRequest): KakaoSearchByKeywordResponse {
        return kakaoClient.searchByKeyword(keyword, pageRequest)
    }
}
