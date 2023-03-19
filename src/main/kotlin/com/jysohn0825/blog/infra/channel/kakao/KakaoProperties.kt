package com.jysohn0825.blog.infra.channel.kakao

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("search.kakao")
@ConstructorBinding
data class KakaoProperties(
    val uri: String,
    val path: String,
    val key: String,
    val pageLimit: Int,
    val sizeLimit: Int
)
