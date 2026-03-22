package com.example.financenews.data.repository

import com.example.financenews.data.api.RetrofitClient
import com.example.financenews.data.model.Article
import com.example.financenews.data.model.NewsCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewsRepository {
    
    private val newsApiService = RetrofitClient.newsApiService
    
    fun getNews(
        category: NewsCategory = NewsCategory.ALL,
        page: Int = 1,
        pageSize: Int = 20
    ): Flow<Result<List<Article>>> = flow {
        try {
            // 计算一周前的时间
            val oneWeekAgo = LocalDateTime.now().minusDays(7)
                .format(DateTimeFormatter.ISO_DATE)
            
            val response = newsApiService.getEverything(
                query = category.query,
                from = oneWeekAgo,
                pageSize = pageSize,
                page = page
            )
            
            if (response.isSuccessful) {
                val articles = response.body()?.articles ?: emptyList()
                emit(Result.success(articles))
            } else {
                emit(Result.failure(Exception("API Error: ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
    
    fun getTopHeadlines(
        page: Int = 1,
        pageSize: Int = 20
    ): Flow<Result<List<Article>>> = flow {
        try {
            val response = newsApiService.getTopHeadlines(
                pageSize = pageSize,
                page = page
            )
            
            if (response.isSuccessful) {
                val articles = response.body()?.articles ?: emptyList()
                emit(Result.success(articles))
            } else {
                emit(Result.failure(Exception("API Error: ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
    
    fun searchNews(
        query: String,
        page: Int = 1,
        pageSize: Int = 20
    ): Flow<Result<List<Article>>> = flow {
        try {
            val oneWeekAgo = LocalDateTime.now().minusDays(7)
                .format(DateTimeFormatter.ISO_DATE)
            
            val response = newsApiService.getEverything(
                query = query,
                from = oneWeekAgo,
                pageSize = pageSize,
                page = page
            )
            
            if (response.isSuccessful) {
                val articles = response.body()?.articles ?: emptyList()
                emit(Result.success(articles))
            } else {
                emit(Result.failure(Exception("API Error: ${response.code()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}
