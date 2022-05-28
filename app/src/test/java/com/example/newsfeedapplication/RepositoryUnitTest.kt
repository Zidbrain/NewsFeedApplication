package com.example.newsfeedapplication

import com.example.newsfeedapplication.model.NewsRepository
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File

class RepositoryUnitTest {
    private lateinit var file: File
    private lateinit var webServer: MockWebServer
    private lateinit var baseUrl: HttpUrl

    @Before
    fun setupFile() {
        val url = this.javaClass.getResource("/mock_response.xml")
        file = File(url!!.file)

        webServer = MockWebServer()
        webServer.enqueue(MockResponse().setBody(file.readText()))

        webServer.start()
        baseUrl = webServer.url("/news")
    }

    @Test
    fun test_get_news() = runBlocking {
        val repository = NewsRepository(
            NewsModule.provideOkHttpClient(),
            Request(baseUrl),
            NewsModule.provideNewsXmlParser(NewsModule.provideDocumentBuilder())
        )

        Assert.assertEquals(CORRECT_NEWS_LIST, repository.getNews())
    }

    @Test
    fun test_get_news_cached() = runBlocking {
        val repository = NewsRepository(
            NewsModule.provideOkHttpClient(),
            Request(baseUrl),
            NewsModule.provideNewsXmlParser(NewsModule.provideDocumentBuilder())
        )

        Assert.assertEquals(CORRECT_NEWS_LIST[1], repository.getByIndex(1))
    }
}