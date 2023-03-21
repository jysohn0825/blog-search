package com.jysohn0825.blog.domain.popularsearchterm

import com.jysohn0825.blog.application.ContentSummary
import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Embeddable

@Embeddable
class PopularKeywordPk(
    @Column(nullable = false, length = 8)
    val baseHour: String,

    @Column(nullable = false, length = ContentSummary.CONTENT_LIMIT_LENGTH)
    val keyword: String
) : Serializable
