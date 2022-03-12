package com.example.newsfeedapplication

import com.example.newsfeedapplication.model.News
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

class NewsXmlParser {
    private val factory = DocumentBuilderFactory.newInstance()

    fun parse(inputStream: InputStream): List<News> {
        inputStream.use {

            val builder = factory.newDocumentBuilder()
            val doc = builder.parse(it)
            val element = doc.documentElement
            element.normalize()

            val ret = mutableListOf<News>()

            val list =  element.getElementsByTagName("item")
            for (i in 0 until (10)) {
                val node = list.item(i)
                val news = News()
                val categories = mutableListOf<String>()

                for (j in 0 until (node.childNodes.length - 1)){
                    val cnode = node.childNodes.item(j)
                    cnode.normalize()

                    when (cnode.nodeName){
                        "title" -> news.title = cnode.textContent
                        "link" -> news.link = cnode.textContent
                        "dc:creator" -> news.author = cnode.textContent
                        "enclosure" -> news.imageSrc = cnode.attributes.getNamedItem("url").textContent
                        "description" -> news.description = cnode.textContent
                        "content:encoded" -> news.content = cnode.textContent
                        "pubDate" -> news.creationTime = Converter.getInstant(cnode.textContent)
                        "guid" -> news.link = cnode.textContent
                        "category" -> categories.add(cnode.textContent)
                    }
                }
                news.categories = categories
                ret.add(news)
            }
            return ret
        }
    }
}