package com.example.newsfeedapplication

import com.example.newsfeedapplication.model.News
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

private const val NEWS_NODE_NAME = "item"
private const val URL_NODE_NAME = "url"
private const val TITLE_STR = "title"
private const val LINK_STR = "link"
private const val AUTHOR_STR = "dc:creator"
private const val IMAGE_SRC_STR = "enclosure"
private const val DESCRIPTION_STR = "description"
private const val CONTENT_STR = "content:encoded"
private const val CREATION_TIME_STR = "pubDate"
private const val CATEGORY_STR = "category"

class NewsXmlParser {
    private val factory = DocumentBuilderFactory.newInstance()

    fun parse(inputStream: InputStream): List<News> {
        inputStream.use {

            val builder = factory.newDocumentBuilder()
            val doc = builder.parse(it)
            val element = doc.documentElement
            element.normalize()

            val ret = mutableListOf<News>()

            val list =  element.getElementsByTagName(NEWS_NODE_NAME)
            for (i in 0 until (list.length - 1)) {
                val node = list.item(i)
                val news = News()
                val categories = mutableListOf<String>()

                for (j in 0 until (node.childNodes.length - 1)){
                    val cnode = node.childNodes.item(j)
                    cnode.normalize()

                    when (cnode.nodeName){
                        TITLE_STR -> news.title = cnode.textContent
                        LINK_STR -> news.link = cnode.textContent
                        AUTHOR_STR -> news.author = cnode.textContent
                        IMAGE_SRC_STR -> news.imageSrc = cnode.attributes.getNamedItem(URL_NODE_NAME).textContent
                        DESCRIPTION_STR -> news.description = cnode.textContent
                        CONTENT_STR -> news.content = cnode.textContent
                        CREATION_TIME_STR -> news.creationTime = Converter.getInstant(cnode.textContent)
                        CATEGORY_STR -> categories.add(cnode.textContent)
                    }
                }
                news.categories = categories
                ret.add(news)
            }
            return ret
        }
    }
}