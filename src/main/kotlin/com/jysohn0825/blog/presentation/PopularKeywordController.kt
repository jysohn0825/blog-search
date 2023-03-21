package com.jysohn0825.blog.presentation

import com.jysohn0825.blog.application.PopularKeywordService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class PopularKeywordController(
    private val popularKeywordService: PopularKeywordService
) {

    @Operation(
        summary = "Search popular keyword",
        description = "Find popular keyword by YYMMDDHH(default is current hour)",
        responses = [ApiResponse(responseCode = "200", description = "Success")]
    )
    @GetMapping("/popular-keyword")
    fun searchByKeyword(
        @RequestParam baseHour: String?
    ) = popularKeywordService.findTop10PopularKeyword(baseHour)
}
