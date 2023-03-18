package com.jysohn0825.blog.presentation

import com.jysohn0825.blog.application.KeywordSearchService
import com.jysohn0825.support.domain.BasePageRequest
import org.springframework.web.bind.annotation.*

@RestController
class KeywordSearchController(
    private val keywordSearchService: KeywordSearchService
) {

    @GetMapping("/search")
    fun searchByKeyword(
        @RequestParam keyword: String,
        @ModelAttribute pageRequest: BasePageRequest
    ) = keywordSearchService.searchByKeyword(keyword, pageRequest)
}
