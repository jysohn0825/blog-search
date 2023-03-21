package com.jysohn0825.blog.domain.popularsearchterm

import org.springframework.data.domain.Persistable
import javax.persistence.*

@Table(indexes = [Index(name = "idx_popular_search_term", columnList = "baseHour")])
@Entity
class PopularKeyword(

    @EmbeddedId
    val pk: PopularKeywordPk,

    count: Long = 0L,

    @Transient
    val newFlag: Boolean = false
) : Persistable<PopularKeywordPk> {

    @Column(nullable = false)
    var count: Long = count
        private set

    val keyword: String
        get() = pk.keyword

    fun addCount() {
        count++
    }

    override fun getId(): PopularKeywordPk = pk
    override fun isNew(): Boolean = newFlag
}
