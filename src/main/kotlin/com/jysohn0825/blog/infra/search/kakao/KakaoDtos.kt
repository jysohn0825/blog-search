package com.jysohn0825.blog.infra.search.kakao

import com.jysohn0825.support.domain.BasePageResponse

data class KakaoSearchByKeywordResponseList(
    val kakaoSearchResponse: List<KakaoSearchByKeywordResponse> = emptyList()
) {
    data class KakaoSearchByKeywordResponse(
        val meta : BasePageResponse,
        val title: String,
        val content: String,
        val datetime: String
    )
}
