package com.example.newsfeedapplication.model

import com.example.newsfeedapplication.NewsXmlParser
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runInterruptible
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.*
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private const val RSS_URL = "https://www.androidpolice.com/feed/"

@Singleton
class NewsRepository @Inject constructor() {
    private val client = OkHttpClient.Builder()
        .build()

    var news: List<News>? = null

    suspend fun getByIndex(id: Int): News {
        if (news == null)
            getNews()

        return news!![id]
    }

    suspend fun getNews(): List<News> {
        val request = Request.Builder().url(RSS_URL).get().build()
        val response = client.newCall(request).await()

        if (!response.isSuccessful)
            throw Exception()

        news = runInterruptible(Dispatchers.IO) {
            response.body()!!.byteStream().use {
                val parser = NewsXmlParser()
                return@runInterruptible parser.parse(it)
            }
        }
        return news!!
    }

    internal suspend inline fun Call.await(): Response =
        suspendCancellableCoroutine { continuation ->
            val callback = object : Callback, CompletionHandler {
                override fun onFailure(call: Call, e: IOException) {
                    if (!call.isCanceled)
                        continuation.resumeWithException(e)
                }

                override fun onResponse(call: Call, response: Response) {
                    continuation.resume(response)
                }

                override fun invoke(cause: Throwable?) {
                    try {
                        this@await.cancel()
                    } catch (_: Throwable) {
                    }
                }
            }

            enqueue(callback)
            continuation.invokeOnCancellation(callback)
        }
}