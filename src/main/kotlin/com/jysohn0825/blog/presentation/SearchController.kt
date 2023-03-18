package com.jysohn0825.blog.presentation

import com.jysohn0825.blog.application.SearchService
import com.jysohn0825.support.domain.BasePageRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/search")
class SearchController(
    private val searchService: SearchService
) {

    @GetMapping
    fun searchByKeyword(
        @RequestParam keyword: String,
        @ModelAttribute pageRequest: BasePageRequest
    ) = searchService.searchByKeyword(keyword, pageRequest)
}
