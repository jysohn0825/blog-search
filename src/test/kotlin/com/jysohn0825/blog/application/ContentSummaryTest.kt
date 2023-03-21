package com.jysohn0825.blog.application

import com.jysohn0825.blog.application.ContentSummary.Companion.CONTENT_LIMIT_LENGTH
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class ContentSummaryTest {

    @Test
    fun `블로그 내용이 길 경우 절삭 처리 확인`() {
        val summary = ContentSummary(
            "title",
            """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam interdum dui lacus. 
            Nullam lacinia turpis at nisl auctor facilisis. Suspendisse non magna magna. Quisque feugiat feugiat lacus vitae feugiat. 
            Sed tortor urna, sollicitudin in tincidunt ut, ultrices id orci. Mauris consectetur, 
            ex non vestibulum porta, velit risus malesuada mi, in varius felis lectus vel sapien. Morbi in sodales leo.
            Nulla facilisi. Ut egestas viverra ullamcorper. Vivamus dui justo, fringilla a dolor et, posuere tempor purus.
            Vivamus volutpat laoreet mauris, vel aliquet magna. In laoreet elementum tincidunt.
            Interdum et malesuada fames ac ante ipsum primis in faucibus. 
            In nec lacus vestibulum, posuere nisl sed, condimentum ipsum. 
            Sed ut tempus quam. Donec vehicula nisi auctor scelerisque hendrerit. Sed ultrices diam magna. 
            Nulla eu vehicula urna, cursus interdum diam. Pellentesque accumsan arcu quis nulla eleifend imperdiet. 
            Maecenas dictum lorem sed mi dignissim auctor. Cras interdum vel ligula eget lacinia.
            Fusce sed nulla ligula. Fusce pulvinar urna nec semper vulputate. Maecenas eu felis at lorem cursus tempus et id felis. 
            Aliquam accumsan ullamcorper quam, et convallis tellus dictum ac. 
            Nunc efficitur lacinia lacinia. Praesent ac tincidunt dolor. Morbi at commodo nunc. Proin in odio eros. 
            Vestibulum sed euismod elit. Phasellus velit felis, euismod nec neque sit amet, mollis tincidunt sem. 
            Donec at consequat turpis, a suscipit leo.
            """.trimIndent(),
            "url",
            LocalDateTime.now(),
            "thumbnail"
        )

        assertThat(summary.contents.length).isEqualTo(CONTENT_LIMIT_LENGTH)
    }
}
