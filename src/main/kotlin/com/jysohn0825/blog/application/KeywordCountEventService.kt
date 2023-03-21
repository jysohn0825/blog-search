package com.jysohn0825.blog.application

import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class KeywordCountEventService(
    private val eventPublisher: ApplicationEventPublisher,
    private val popularKeywordService: PopularKeywordService
) {
    fun publish(any: Any) {
        eventPublisher.publishEvent(any)
    }

    @Async
    @EventListener
    fun addCountListener(event: PopularKeywordEvent){
        popularKeywordService.findAndSave(event.pk)
    }
}
