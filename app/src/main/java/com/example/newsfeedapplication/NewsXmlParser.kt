package com.example.newsfeedapplication

import com.example.newsfeedapplication.model.News
import java.io.InputStream

interface NewsXmlParser {
    fun parse(inputStream: InputStream) : List<News>
}