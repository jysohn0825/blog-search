package com.jysohn0825.blog.infra.search.kakao

import com.jysohn0825.support.domain.BasePageRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException.Unauthorized
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class KakaoClient(
    private val properties: KakaoProperties,
    private val restTemplate: RestTemplate
) {

    fun searchByKeyword(keyword: String, request: BasePageRequest): KakaoSearchByKeywordResponseList {

        val uri = UriComponentsBuilder
            .fromUriString(properties.uri)
            .path(properties.path)
            .queryParam("query", keyword)
            .queryParam("sort", request.sort)
            .queryParam("page", request.page)
            .queryParam("size", request.size)
            .encode()
            .build()
            .toUri()

        val requestEntity = RequestEntity
            .get(uri)
            .accept(MediaType.APPLICATION_JSON)
            .header(HttpHeaders.AUTHORIZATION, properties.key)
            .build()

        return runCatching { restTemplate.exchange(requestEntity, KakaoSearchByKeywordResponseList::class.java) }
            .onFailure {
                when (it) {
                    is Unauthorized -> throw RuntimeException("잘못된 API Key")
                    else -> throw RuntimeException("알 수 없는 에러", it)
                }
            }.getOrNull()
            .let { it?.body ?: KakaoSearchByKeywordResponseList() }
    }
}
