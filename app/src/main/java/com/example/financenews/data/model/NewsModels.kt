package com.example.financenews.data.model

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<Article>
)

data class Article(
    @SerializedName("source") val source: Source,
    @SerializedName("author") val author: String?,
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String?,
    @SerializedName("url") val url: String,
    @SerializedName("urlToImage") val urlToImage: String?,
    @SerializedName("publishedAt") val publishedAt: String,
    @SerializedName("content") val content: String?
) {
    fun getCategory(): NewsCategory {
        val titleLower = title.lowercase()
        val descLower = description?.lowercase() ?: ""
        
        return when {
            titleLower.contains("stock") || titleLower.contains("market") || 
                 titleLower.contains("trade") || titleLower.contains("指数") || 
                 titleLower.contains("股市") || titleLower.contains("市场") -> NewsCategory.MARKETS
            titleLower.contains("tech") || titleLower.contains("technology") || 
                 titleLower.contains("科技") || titleLower.contains("互联网") || 
                 descLower.contains("tech") || descLower.contains("ai") || 
                 descLower.contains("artificial intelligence") -> NewsCategory.TECHNOLOGY
            titleLower.contains("economy") || titleLower.contains("economic") || 
                 titleLower.contains("gdp") || titleLower.contains("经济") || 
                 titleLower.contains("央行") || titleLower.contains("利率") -> NewsCategory.ECONOMY
            titleLower.contains("business") || titleLower.contains("company") || 
                 titleLower.contains("企业") || titleLower.contains("商业") || 
                 titleLower.contains("财报") || titleLower.contains("盈利") -> NewsCategory.BUSINESS
            else -> NewsCategory.GENERAL
        }
    }
}

data class Source(
    @SerializedName("id") val id: String?,
    @SerializedName("name") val name: String
)

enum class NewsCategory(val displayName: String, val query: String) {
    ALL("全部", "finance OR business OR economy"),
    BUSINESS("商业", "business"),
    TECHNOLOGY("科技", "technology"),
    MARKETS("市场", "stock market OR trading"),
    ECONOMY("经济", "economy"),
    GENERAL("综合", "finance")
}
