package com.jysohn0825.blog.domain.popularsearchterm

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository
import javax.persistence.LockModeType

fun PopularKeywordRepository.findByPkOrNew(pk: PopularKeywordPk): PopularKeyword =
    findByPk(pk) ?: PopularKeyword(pk = pk, newFlag = true)

@Repository
interface PopularKeywordRepository : JpaRepository<PopularKeyword, PopularKeywordPk> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    fun findTop10ByPkBaseHourOrderByCountDesc(baseHour: String): List<PopularKeyword>

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    fun findByPk(pk: PopularKeywordPk): PopularKeyword?
}
