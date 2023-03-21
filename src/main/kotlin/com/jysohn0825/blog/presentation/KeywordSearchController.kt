package com.jysohn0825.blog.presentation

import com.jysohn0825.blog.application.KeywordSearchService
import com.jysohn0825.blog.infra.channel.ChannelType
import com.jysohn0825.blog.support.domain.BasePageRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.web.bind.annotation.*

@RestController
class KeywordSearchController(
    private val keywordSearchService: KeywordSearchService
) {

    @Operation(
        summary = "Blog search by keyword",
        description = "Blog search by using Open API of KAKAO, NAVER",
        responses = [
            ApiResponse(responseCode = "200", description = "Success"),
            ApiResponse(
                responseCode = "400", description =
                "There are two blank when use Kakao search \n " +
                        "DB error when storing popular keyword \n " +
                        "Request valid check \n ",
                content = [Content(
                    schema = Schema(
                        example =
                        "{{\"httpStatus\":\"BAD_REQUEST\",\"message\":\"Bad search keyword\"} \n " +
                                "{{\"httpStatus\":\"BAD_REQUEST\",\"message\":\"Cannot store popular keyword\"} \n " +
                                "{{\"httpStatus\":\"BAD_REQUEST\",\"message\":\"Wrong size (it must be in N ~ M)\"}"
                    )
                )]
            ),
            ApiResponse(
                responseCode = "502", description = "Open API server error",
                content = [Content(schema = Schema(example = "{{\"httpStatus\":\"BAD_REQUEST\",\"message\":\"Open API server error\"}"))]
            )]
    )
    @GetMapping("/search")
    fun searchByKeyword(
        @RequestParam channel: ChannelType,
        @RequestParam keyword: String,
        @ModelAttribute pageRequest: BasePageRequest
    ) = keywordSearchService.searchByKeyword(channel, keyword, pageRequest)
}
