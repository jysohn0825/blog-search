package com.jysohn0825.blog.infra.search.kakao

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime

data class KakaoSearchByKeywordResponse(
    val meta: Meta = Meta(),
    val documents: List<Documents> = emptyList()
) {
    data class Meta(
        val total_count: Int = 0,
        val pageable_count: Int = 0,
        val is_end: Boolean = true
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
