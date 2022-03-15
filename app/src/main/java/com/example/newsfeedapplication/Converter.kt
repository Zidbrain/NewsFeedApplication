package com.example.newsfeedapplication

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

private const val INSTANT_FORMAT = "EEE, dd MMM yyyy HH:mm:ss zzz"

class Converter {
    companion object {
        @JvmStatic
        fun getCategoriesString(categories: List<String>): String = buildString {
            append("Categories: ", categories[0])
            for (s in categories.slice(1..categories.lastIndex)) {
                append(", ", s)
            }
        }

        @JvmStatic
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
}

@BindingAdapter("imageSrc")
fun setImage(imageView: ImageView, imageSrc: String) {
    Picasso.get().load(imageSrc).fit().centerCrop().into(imageView)
}