package com.example.newsfeedapplication.model

import java.time.Instant

data class News(var title: String = "",
                var description: String = "",
                var imageSrc: String = "",
                var author: String = "",
                var creationTime: Instant = Instant.now(),
                var categories: List<String> = listOf(),
                var content: String = "",
                var link: String = "")