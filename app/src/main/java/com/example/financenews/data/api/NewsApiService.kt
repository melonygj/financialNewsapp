package com.example.financenews.data.api

import com.example.financenews.data.model.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    
    @GET("v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("language") language: String = "zh",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
    
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("category") category: String = "business",
        @Query("language") language: String = "zh",
        @Query("pageSize") pageSize: Int = 20,
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
    
    companion object {
        const val BASE_URL = "https://newsapi.org/"
        // 免费 API Key，用户需要替换为自己的
        const val API_KEY = "demo"
        
        // 备用 API 端点
        const val GNEWS_BASE_URL = "https://gnews.io/api/v4/"
    }
}
