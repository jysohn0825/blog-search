package com.jysohn0825.blog.infra.search.kakao

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("search.kakao")
@ConstructorBinding
data class KakaoProperties(
    val uri: String,
    val path: String,
    val key: String
)
