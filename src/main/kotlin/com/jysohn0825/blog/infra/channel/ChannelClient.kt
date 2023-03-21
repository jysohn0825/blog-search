package com.jysohn0825.blog.infra.channel

import com.jysohn0825.blog.infra.channel.kakao.KakaoClient
import com.jysohn0825.support.domain.BasePageRequest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

interface ChannelClient<T : ChannelSearchByKeywordResponse> {

    fun extractKeyword(keyword: String): String

    fun checkPageRequestValid(request: BasePageRequest)
    fun searchByKeyword(keyword: String, request: BasePageRequest): ChannelSearchByKeywordResponse

    fun uriForGetMethod(params: Map<String, Any>): URI = UriComponentsBuilder
        .fromUriString(KakaoClient.URL)
        .path(KakaoClient.PATH)
        .apply { params.forEach { queryParam(it.key, it.value) } }
        .encode()
        .build()
        .toUri()

    fun requestEntityForGetMethod(uri: URI, headerMap: Map<String, String>): RequestEntity<Void> = RequestEntity
        .method(HttpMethod.GET, uri)
        .accept(MediaType.APPLICATION_JSON)
        .apply { headerMap.forEach { header(it.key, it.value) } }
        .build()

    fun Result<ResponseEntity<T>>.throwOrGetResponse() = onFailure { checkAndThrowException(it) }.getResponse()

    private fun checkAndThrowException(exception: Throwable) {
        when (exception) {
            is HttpClientErrorException.Unauthorized -> throw RuntimeException("잘못된 API Key")
            else -> throw RuntimeException("알 수 없는 에러", exception)
        }
    }

    private fun Result<ResponseEntity<T>>.getResponse(): T? = this.map { it.body }.getOrNull()
}
