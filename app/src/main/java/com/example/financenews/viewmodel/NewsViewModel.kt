package com.example.financenews.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financenews.data.model.Article
import com.example.financenews.data.model.NewsCategory
import com.example.financenews.data.model.Source
import com.example.financenews.data.repository.NewsRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    
    private val repository = NewsRepository()
    
    var articles = mutableStateListOf<Article>()
        private set
    
    var isLoading = mutableStateOf(false)
        private set
    
    var error = mutableStateOf<String?>(null)
        private set
    
    var selectedCategory = mutableStateOf(NewsCategory.ALL)
        private set
    
    var searchQuery = mutableStateOf("")
        private set
    
    private var currentPage = 1
    private val pageSize = 20
    
    init {
        loadNews()
    }
    
    fun loadNews(refresh: Boolean = false) {
        if (refresh) {
            currentPage = 1
            articles.clear()
        }
        
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            
            repository.getNews(
                category = selectedCategory.value,
                page = currentPage,
                pageSize = pageSize
            ).collectLatest { result ->
                isLoading.value = false
                result.fold(
                    onSuccess = { newArticles ->
                        articles.addAll(newArticles)
                    },
                    onFailure = { exception ->
                        error.value = exception.message ?: "加载失败"
                        loadMockData()
                    }
                )
            }
        }
    }
    
    fun searchNews(query: String) {
        searchQuery.value = query
        if (query.isBlank()) {
            loadNews(refresh = true)
            return
        }
        
        viewModelScope.launch {
            isLoading.value = true
            error.value = null
            articles.clear()
            
            repository.searchNews(query).collectLatest { result ->
                isLoading.value = false
                result.fold(
                    onSuccess = { newArticles ->
                        articles.addAll(newArticles)
                    },
                    onFailure = { exception ->
                        error.value = exception.message ?: "搜索失败"
                        loadMockData()
                    }
                )
            }
        }
    }
    
    fun selectCategory(category: NewsCategory) {
        selectedCategory.value = category
        loadNews(refresh = true)
    }
    
    fun loadMore() {
        currentPage++
        loadNews()
    }
    
    fun refresh() {
        loadNews(refresh = true)
    }
    
    private fun loadMockData() {
        val mockArticles = listOf(
            Article(
                source = Source("1", "财经网"),
                author = "张三",
                title = "全球股市本周表现强劲，科技股领涨",
                description = "受积极经济数据影响，全球主要股市本周普遍上涨，科技股成为主要推动力。",
                url = "https://example.com/1",
                urlToImage = null,
                publishedAt = "2026-03-22T08:00:00Z",
                content = "全球股市本周表现强劲..."
            ),
            Article(
                source = Source("2", "科技日报"),
                author = "李四",
                title = "人工智能芯片市场迎来新机遇",
                description = "随着AI应用的普及，芯片制造商迎来新一轮增长周期。",
                url = "https://example.com/2",
                urlToImage = null,
                publishedAt = "2026-03-21T15:30:00Z",
                content = "人工智能芯片市场..."
            ),
            Article(
                source = Source("3", "经济观察"),
                author = "王五",
                title = "央行宣布维持利率不变，市场反应平稳",
                description = "央行今日宣布维持基准利率不变，符合市场预期。",
                url = "https://example.com/3",
                urlToImage = null,
                publishedAt = "2026-03-20T10:00:00Z",
                content = "央行宣布..."
            ),
            Article(
                source = Source("4", "商业周刊"),
                author = "赵六",
                title = "新能源汽车销量创新高，产业链受益",
                description = "本月新能源汽车销量突破历史记录，带动相关产业链发展。",
                url = "https://example.com/4",
                urlToImage = null,
                publishedAt = "2026-03-19T14:20:00Z",
                content = "新能源汽车..."
            ),
            Article(
                source = Source("5", "投资快报"),
                author = "钱七",
                title = "黄金市场波动加剧，投资者需谨慎",
                description = "受国际形势影响，黄金价格本周出现较大波动。",
                url = "https://example.com/5",
                urlToImage = null,
                publishedAt = "2026-03-18T09:15:00Z",
                content = "黄金市场..."
            )
        )
        articles.addAll(mockArticles)
    }
}
