package com.giovannidiluca.news.feed

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.SubcomposeAsyncImage
import com.giovannidiluca.data.model.Article
import com.giovannidiluca.news.R
import com.giovannidiluca.news.ui.theme.NewsTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder

private val URL_CHARACTER_ENCODING = Charsets.UTF_8.name()

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsListScreen(navController: NavController, viewModel: NewsViewModel = hiltViewModel()) {
    val items = viewModel.fetchArticles().collectAsLazyPagingItems()

    val refreshScope = rememberCoroutineScope()
    var refreshing by remember { mutableStateOf(false) }

    fun refresh() = refreshScope.launch {
        refreshing = true
        delay(1000)
        items.refresh()
        refreshing = false
    }

    val state = rememberPullRefreshState(refreshing, ::refresh)

    NewsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            Box(
                Modifier
                    .pullRefresh(state)
                    .fillMaxSize()
            ) {
                HeadlineList(articles = items) { article ->
                    navController.navigateToArticle(article)
                }
                PullRefreshIndicator(refreshing, state, Modifier.align(Alignment.TopCenter))
            }
        }
    }
}


private fun showErrorMessage(context: Context) =
    Toast.makeText(context, R.string.error, Toast.LENGTH_SHORT).show()


@Preview
@Composable
private fun Loading() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(40.dp)
                .padding(8.dp)
        )
    }
}

@Composable
private fun HeadlineList(
    articles: LazyPagingItems<Article>, onClickItem: (Article) -> Unit
) {
    val context = LocalContext.current

    LazyColumn {
        item { TopBar(stringResource(id = R.string.source)) }
        items(count = articles.itemCount,
            key = articles.itemKey(),
            contentType = articles.itemContentType { article -> article }) { index ->
            articles[index]?.let { article ->
                if (article.active) HeadlineCard(article = article,
                    onClick = { onClickItem(article) })
            }
        }

        when (articles.loadState.refresh) {
            is LoadState.Loading -> item { Loading() }
            is LoadState.Error -> item { showErrorMessage(context) }
            is LoadState.NotLoading -> {}
        }

        when (articles.loadState.append) {
            is LoadState.Loading -> item { Loading() }
            is LoadState.Error -> item { showErrorMessage(context) }
            is LoadState.NotLoading -> {}
        }
    }
}

fun NavController.navigateToArticle(article: Article) {
    val articleJson = Json.encodeToString(article)
    val articleEncoded = URLEncoder.encode(articleJson, URL_CHARACTER_ENCODING)
    navigate("news_details_route/$articleEncoded") {
        launchSingleTop = true
    }
}

@Composable
fun HeadlineCard(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onClick() }, elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubcomposeAsyncImage(
                model = article.urlToImage,
                contentDescription = stringResource(R.string.headline_image_description),
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .clip(shape = RoundedCornerShape(8.dp)),
                loading = { Loading() },
            )
            Text(text = article.title, fontWeight = FontWeight.Bold)
            Text(text = article.publishedAt, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeadlineCardPreview() {
    NewsTheme {
        HeadlineCard(Article(
            title = "Kate, Princess of Wales, announces cancer was found during surgery, undergoing preventative chemotherapy - CBS News",
            description = "The video announcement from Kate, Princess of Wales, comes after months of widespread speculation about her health and controversy over doctored images released by Kensington Palace.",
            urlToImage = "",
            publishedAt = "2024-03-22T19:36:00Z",
            author = "John Doe",
            active = true,
        ), onClick = {})
    }
}