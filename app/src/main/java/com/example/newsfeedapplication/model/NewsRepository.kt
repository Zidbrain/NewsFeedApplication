package com.example.newsfeedapplication.model

import android.util.Xml
import com.example.newsfeedapplication.NewsXmlParser
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NewsRepository {
    private val client = OkHttpClient.Builder()
        .retryOnConnectionFailure(true)
        .build()

    suspend fun getNews(): List<News> {
        val request = Request.Builder().url("https://www.androidpolice.com/feed/").header("Accept-Encoding", "identity").header("Connection", "close")
            .header("accept", "*/*").get().build()
        val response = client.newCall(request).await()

        if (!response.isSuccessful)
            throw Exception()

        return runInterruptible(Dispatchers.IO) {
            response.body()!!.byteStream().use {
                val parser = NewsXmlParser()
                return@runInterruptible parser.parse(it)
            }
        }
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