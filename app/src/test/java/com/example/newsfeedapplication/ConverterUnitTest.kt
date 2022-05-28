package com.example.newsfeedapplication

import org.junit.Assert
import org.junit.Test
import java.time.Instant

class ConverterUnitTest {
    private val instant = Instant.ofEpochMilli(1653745160000L)

    @Test
    fun test_get_categories_string() {
        val categories = listOf("cat1", "cat2", "cat3")
        val prefix = "prefix: "

        Assert.assertEquals("prefix: cat1, cat2, cat3", Converter.getCategoriesString(categories, prefix))
    }

    @Test
    fun test_format_instant() {
        val expected = "28.05.2022, 16:39"

        Assert.assertEquals(expected, Converter.formatInstant(instant))
    }

    @Test
    fun test_get_instant() {
        Assert.assertEquals(instant, Converter.getInstant("Sat, 28 May 2022 13:39:20 GMT"))
    }
}