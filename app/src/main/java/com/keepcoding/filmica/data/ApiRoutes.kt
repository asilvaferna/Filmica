package com.keepcoding.filmica.data

import android.net.Uri
import com.keepcoding.filmica.BuildConfig

object ApiRoutes {

    fun searchUrl(
        query: String,
        language: String = "en-US",
        page: Int = 1
    ): String {
        return getUriBuilder()
            .appendPath("search")
            .appendPath("movie")
            .appendQueryParameter("language", language)
            .appendQueryParameter("page", page.toString())
            .appendQueryParameter("query", query)
            .toString()
    }

    fun discoverUrl(
        language: String = "en-US",
        sort: String = "popularity.desc",
        page: Int = 1
    ): String {
        return getUriBuilder()
            .appendPath("discover")
            .appendPath("movie")
            .appendQueryParameter("language", language)
            .appendQueryParameter("sort_by", sort)
            .appendQueryParameter("page", page.toString())
            .build()
            .toString()
    }

    fun trendingUrl(
        mediaType: String = "movie",
        timeWindow: String = "day",
        page: Int = 1
    ): String {
        return getUriBuilder()
            .appendPath("trending")
            .appendPath(mediaType)
            .appendPath(timeWindow)
            .appendQueryParameter("page", page.toString())
            .toString()
    }

    private fun getUriBuilder() =
        Uri.Builder()
            .scheme("https")
            .authority("api.themoviedb.org")
            .appendPath("3")
            .appendQueryParameter("api_key", BuildConfig.MovieDBApiKey)
            .appendQueryParameter("include_adult", "false")
            .appendQueryParameter("include_video", "false")
}