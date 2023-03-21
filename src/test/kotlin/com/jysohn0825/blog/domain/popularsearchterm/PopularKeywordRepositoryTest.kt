package com.jysohn0825.blog.domain.popularsearchterm

import com.jysohn0825.blog.application.PopularKeywordService
import com.jysohn0825.blog.getPopularKeyword
import com.jysohn0825.blog.getPopularKeywordPk
import com.jysohn0825.blog.infra.channel.kakao.KakaoClientTest.Companion.KEYWORD
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Transactional
@DataJpaTest
class PopularKeywordRepositoryTest @Autowired constructor(
    private val repository: PopularKeywordRepository,
    private val entityManager: TestEntityManager,
) {

    val service = PopularKeywordService(repository)

    @Test
    fun `findByPkOrNew 호출 시 데이터 없을 경우 New 확인`() {
        val baseHour = "23122509"
        val keyword = "크리스마스"
        val pk = getPopularKeywordPk(baseHour, keyword)

        val actual = repository.findByPkOrNew(pk)

        assertThat(actual.newFlag).isTrue
    }

    @Test
    fun `findByPkOrNew 호출 시 데이터 있을 경우 데이터 확인`() {
        val baseHour = "23122509"
        val keyword = "크리스마스"
        val pk = getPopularKeywordPk(baseHour, keyword)
        entityManager.persistAndFlush(getPopularKeyword(pk))

        val actual = repository.findByPkOrNew(pk)

        assertThat(actual.newFlag).isTrue
    }

    @Test
    fun `상위 10개 인기 검색어 정렬 확인`() {
        val baseHour = "23031810"
        val min = 1L
        val max = 11L
        for (count in min..max) {
            entityManager.persistAndFlush(
                getPopularKeyword(getPopularKeywordPk(baseHour, count.toString()), count)
            )
        }

        val actual = repository.findTop10ByPkBaseHourOrderByCountDesc(baseHour)

        assertThat(actual.size).isEqualTo(10)
        assertThat(actual.first().count).isEqualTo(max)
        assertThat(actual.last().count).isEqualTo(min + (max - 10L))
    }

    @Test
    fun `입력 시간에 따른 검색어 확인`() {
        val baseHour1 = "23031810"
        val baseHour2 = "23031811"
        val min = 1L
        val max = 10L
        val data = mapOf(
            baseHour1 to min..max / 2,
            baseHour2 to max / 2..max
        )
        data.forEach {
            for (count in it.value) {
                entityManager.persistAndFlush(getPopularKeyword(getPopularKeywordPk(it.key, KEYWORD + count), count))
            }
        }

        val actual = repository.findTop10ByPkBaseHourOrderByCountDesc(baseHour1)

        assertThat(actual.size).isEqualTo(data[baseHour1]!!.last - data[baseHour1]!!.first + 1L)
    }

//    @Test
//    fun `Lock 테스트`() {
//        val pk = getPopularKeywordPk()
//        entityManager.persistAndFlush(getPopularKeyword(pk))
//
//        val count = 100
//        val executor = Executors.newFixedThreadPool(10)
//        val latch = CountDownLatch(count)
//
//        for (i in 0 until count) {
//            executor.execute {
//                service.findAndSave(pk)
//                latch.countDown()
//            }
//        }
//        latch.await()
//
//        val actual = entityManager.find(PopularKeyword::class.java, pk)
//
//        assertThat(actual.count).isEqualTo(count + 1)
//    }
}

