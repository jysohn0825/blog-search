package com.jysohn0825.blog.application

import com.jysohn0825.blog.domain.popularsearchterm.PopularKeywordPk
import com.jysohn0825.blog.domain.popularsearchterm.PopularKeywordRepository
import com.jysohn0825.blog.domain.popularsearchterm.findByPkOrNew
import com.jysohn0825.blog.support.utils.getStringYyMMddHH
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service
import javax.persistence.EntityExistsException
import javax.transaction.Transactional

@Transactional
@Service
class PopularKeywordService(
    private val popularKeywordRepository: PopularKeywordRepository
) {

    fun findTop10PopularKeyword(baseHour: String?): PopularKeywordResponse {
        val list = popularKeywordRepository.findTop10ByPkBaseHourOrderByCountDesc(baseHour ?: getStringYyMMddHH())
        return PopularKeywordResponse.of(list)
    }

    fun findAndSave(pk: PopularKeywordPk, isRepeat: Boolean = false) {
        val popularKeyword = popularKeywordRepository.findByPkOrNew(pk)
        popularKeyword.addCount()
        runCatching { popularKeywordRepository.save(popularKeyword) }
            .getOrElse { checkRetryOrException(it, pk, isRepeat) }
    }

    private fun checkRetryOrException(exception: Throwable, pk: PopularKeywordPk, isRepeat: Boolean) {
        if (isRepeat) throw RuntimeException("Cannot store popular keyword")
        when (exception) {
            is DataIntegrityViolationException -> findAndSave(pk, true)
            is EntityExistsException -> findAndSave(pk, true)
            else -> throw exception
        }
    }
}
