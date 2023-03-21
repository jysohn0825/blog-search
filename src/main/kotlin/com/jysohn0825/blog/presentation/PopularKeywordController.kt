package com.jysohn0825.blog.presentation

import com.jysohn0825.blog.application.PopularKeywordService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PopularKeywordController(
    private val popularKeywordService: PopularKeywordService
) {

    @GetMapping("/popular-keyword")
    fun searchByKeyword(
        @RequestParam baseHour: String?
    ) = popularKeywordService.findTop10PopularKeyword(baseHour)
}
