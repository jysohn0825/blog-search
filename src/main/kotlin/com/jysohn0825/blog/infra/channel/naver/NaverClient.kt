package com.jysohn0825.blog.infra.channel.naver

import com.jysohn0825.blog.infra.channel.ChannelClient
import com.jysohn0825.blog.infra.channel.kakao.KakaoClient
import com.jysohn0825.support.domain.BasePageRequest
import com.jysohn0825.support.domain.SortEnum
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class NaverClient(
    private val restTemplate: RestTemplate
) : ChannelClient<NaverSearchByKeywordResponse> {

    @Value("\${search.naver.key.public}")
    private val publicKey = ""

    @Value("\${search.naver.key.private}")
    private val privateKey = ""

    override fun extractKeyword(keyword: String): String = keyword

    override fun checkPageRequestValid(request: BasePageRequest) {
        require(request.page in PAGE_MIN_LIMIT..PAGE_MAX_LIMIT) { "올바르지 않은 페이지 (${KakaoClient.PAGE_MIN_LIMIT} ~ ${KakaoClient.PAGE_MAX_LIMIT} 가 아님)." }
        require(request.size in SIZE_MIN_LIMIT..SIZE_MAX_LIMIT) { "올바르지 않은 사이즈 (${KakaoClient.SIZE_MIN_LIMIT} ~ ${KakaoClient.SIZE_MAX_LIMIT} 가 아님)." }
    }

    override fun searchByKeyword(keyword: String, request: BasePageRequest): NaverSearchByKeywordResponse {

        checkPageRequestValid(request)
        val paramMap = mapOf(
            "query" to keyword,
            "sort" to (if (request.sort == SortEnum.RECENCY) "date" else "sim"),
            "start" to request.page,
            "display" to request.size
        )
        val uri = uriForGetMethod(URL, PATH, paramMap)
        val requestEntity =
            requestEntityForGetMethod(uri, mapOf("X-Naver-Client-Id" to publicKey, "X-Naver-Client-Secret" to privateKey))

        return runCatching { restTemplate.exchange(requestEntity, NaverSearchByKeywordResponse::class.java) }
            .throwOrGetResponse() ?: NaverSearchByKeywordResponse()
    }

    companion object {
        const val URL = "https://openapi.naver.com"
        const val PATH = "/v1/search/blog.json"
        const val PAGE_MIN_LIMIT = 1
        const val PAGE_MAX_LIMIT = 100
        const val SIZE_MIN_LIMIT = 1
        const val SIZE_MAX_LIMIT = 100
    }
}
