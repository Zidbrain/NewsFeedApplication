package com.example.newsfeedapplication

import com.example.newsfeedapplication.model.News
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File

val CORRECT_NEWS_LIST =
    listOf(
        News(
            title = "title1",
            description = "description1",
            imageSrc = "https://static1.anpoimages.com/wordpress/wp-content/uploads/2022/05/fuji-instax-mini-evo-with-photos.jpg",
            author = "Taylor Kerns1",
            creationTime = Converter.getInstant("Sat, 28 May 2022 12:30:14 GMT"),
            categories = listOf("Accessories", "Reviews"),
            content = "content1",
            link = "https://www.androidpolice.com/fuji-instax-mini-evo-hands-on/"
        ),
        News(
            title = "title2",
            description = "description2",
            imageSrc = "https://static1.anpoimages.com/wordpress/wp-content/uploads/2022/05/fuji-instax-mini-evo-with-photos1.jpg",
            author = "Taylor Kerns2",
            creationTime = Converter.getInstant("Sat, 28 May 2022 12:40:14 GMT"),
            categories = listOf("Accessories2", "Reviews2"),
            content = "content2",
            link = "https://www.androidpolice.com/fuji-instax-mini-evo-hands-on2/"
        )
    )

class DocumentNewsParserUnitTest {
    private lateinit var file: File

    @Before
    fun createFile() {
        val url = this.javaClass.getResource("/mock_response.xml")
        file = File(url!!.file)
    }

    @Test
    fun test_news_xml_parser() {
        val parser = DocumentNewsParser(NewsModule.provideDocumentBuilder())
        file.inputStream().use {
            Assert.assertEquals(CORRECT_NEWS_LIST, parser.parse(it))
        }
    }
}