package com.jysohn0825.blog.infra.channel.kakao

import com.jysohn0825.blog.infra.channel.ChannelClient
import com.jysohn0825.blog.support.domain.BasePageRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class KakaoClient(
    private val restTemplate: RestTemplate
) : ChannelClient<KakaoSearchByKeywordResponse> {

    @Value("\${search.kakao.key}")
    private val key = ""

    override fun extractKeyword(keyword: String): String {
        val checkBlank = keyword.split(" ")
        return when (checkBlank.size) {
            2 -> checkBlank[1]
            1 -> checkBlank[0]
            else -> throw IllegalArgumentException("잘못된 검색어 입니다.")
        }
    }

    override fun checkPageRequestValid(request: BasePageRequest) {
        require(request.page in PAGE_MIN_LIMIT..PAGE_MAX_LIMIT) { "올바르지 않은 페이지 ($PAGE_MIN_LIMIT ~ $PAGE_MAX_LIMIT 가 아님)." }
        require(request.size in SIZE_MIN_LIMIT..SIZE_MAX_LIMIT) { "올바르지 않은 사이즈 ($SIZE_MIN_LIMIT ~ $SIZE_MAX_LIMIT 가 아님)." }
    }

    override fun searchByKeyword(keyword: String, request: BasePageRequest): KakaoSearchByKeywordResponse {

        checkPageRequestValid(request)

        val paramMap = mapOf(
            "query" to keyword,
            "sort" to request.sort,
            "page" to request.page,
            "size" to request.size
        )
        val uri = uriForGetMethod(URL, PATH, paramMap)
        val requestEntity = requestEntityForGetMethod(uri, mapOf(HttpHeaders.AUTHORIZATION to key))

        return runCatching { restTemplate.exchange(requestEntity, KakaoSearchByKeywordResponse::class.java) }
            .throwOrGetResponse() ?: KakaoSearchByKeywordResponse()
    }

    companion object {
        const val URL = "https://dapi.kakao.com"
        const val PATH = "/v2/search/web"
        const val PAGE_MIN_LIMIT = 1
        const val PAGE_MAX_LIMIT = 50
        const val SIZE_MIN_LIMIT = 1
        const val SIZE_MAX_LIMIT = 50
    }
}
