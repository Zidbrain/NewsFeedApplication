package com.example.newsfeedapplication

import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

private const val INSTANT_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz"

object Converter {
    fun getCategoriesString(categories: List<String>, prefix: String): String =
        categories.joinToString(prefix = prefix)

    fun formatInstant(instant: Instant): String {
        val formatter = DateTimeFormatter
            .ofLocalizedDateTime(FormatStyle.SHORT)
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault())
        return formatter.format(instant)
    }

    fun getInstant(input: String): Instant {
        val formatter = DateTimeFormatter
            .ofPattern(INSTANT_FORMAT)
            .withLocale(Locale.US)
        return Instant.from(formatter.parse(input))
    }
}

@BindingAdapter("android:text")
fun setTextInstant(textView: TextView, instant: Instant) {
    textView.text = Converter.formatInstant(instant)
}

@BindingAdapter("textHTML")
fun setTextFromHTML(textView: TextView, text: String) {
    textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT)
}

@BindingAdapter("android:text")
fun setTextCategories(textView: TextView, categories: List<String>) {
    textView.text = Html.fromHtml(
        Converter.getCategoriesString(
            categories,
            textView.resources.getString(R.string.categories_prefix)
        ),
        Html.FROM_HTML_MODE_COMPACT
    )
}

@BindingAdapter("imageSrc")
fun setImage(imageView: ImageView, imageSrc: String) {
    Picasso.get().load(imageSrc).fit().centerCrop().into(imageView)
}

@BindingAdapter("android:text", "time")
fun setTextFromTime(textView: TextView, text: String, time: Instant) {
    textView.text = text.format(Converter.formatInstant(time))
}