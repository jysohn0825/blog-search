package com.jysohn0825.blog.infra.search.kakao

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class KakaoSearchByKeywordResponseList(
    val kakaoSearchResponse: List<KakaoSearchByKeywordResponse> = emptyList()
)

data class KakaoSearchByKeywordResponse(
    val meta: Meta,
    val documents: Documents
) {
    data class Meta(
        val total_count: Int,
        val pageable_count: Int,
        val is_end: Boolean
    )

    data class Documents(
        val title: String,
        val contents: String,
        val url: String,
        val blogname: String,
        val thumbnail: String,
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.tz")
        val datetime: LocalDateTime
    )
}
