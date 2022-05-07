package com.example.newsfeedapplication

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

private const val RSS_URL = "https://www.androidpolice.com/feed/"

@Module
@InstallIn(SingletonComponent::class)
object NewsModule {
    @Provides
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .build()

    @Provides
    fun provideRequest(): Request =
        Request.Builder().url(RSS_URL).get().build()

    @Provides
    fun provideDocumentBuilderFactory(): DocumentBuilder =
        DocumentBuilderFactory.newInstance().newDocumentBuilder()

    @Provides
    fun provideNewsXmlParser(builder: DocumentBuilder): NewsXmlParser =
        DocumentNewsParser(builder)
}