package com.jysohn0825.blog.infra.channel.kakao

import com.jysohn0825.blog.infra.channel.ChannelClient
import com.jysohn0825.support.domain.BasePageRequest
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

    override fun checkPageRequestValid(request: BasePageRequest) {
        require(request.page <= PAGE_LIMIT) { "최대 페이지 수를 넘겼습니다." }
        require(request.size <= SIZE_LIMIT) { "최대 문서 수를 넘겼습니다." }
    }

    override fun searchByKeyword(keyword: String, request: BasePageRequest): KakaoSearchByKeywordResponse {

        checkPageRequestValid(request)

        val uri = uriForGetMethod(
            mapOf(
                "query" to keyword,
                "sort" to request.sort,
                "page" to request.page,
                "size" to request.size
            )
        )
        val requestEntity = requestEntityForGetMethod(uri, mapOf(HttpHeaders.AUTHORIZATION to key))

        return runCatching { restTemplate.exchange(requestEntity, KakaoSearchByKeywordResponse::class.java) }
            .throwOrGetResponse() ?: KakaoSearchByKeywordResponse()
    }

    companion object {
        const val URL = "https://dapi.kakao.com"
        const val PATH = "/v2/search/web"
        const val PAGE_LIMIT = 50
        const val SIZE_LIMIT = 50
    }
}
