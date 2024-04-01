package com.giovannidiluca.news.feed

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.giovannidiluca.data.model.Article
import com.giovannidiluca.news.R
import com.giovannidiluca.news.ui.theme.NewsTheme

@Composable
fun NewsDetailsScreen(article: Article) {
    NewsTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            color = MaterialTheme.colorScheme.background
        ) {
            Column {
                TopBar(stringResource(id = R.string.source))
                DetailsCard(article = article)
            }
        }
    }
}

@Composable
fun DetailsCard(article: Article) {

    Column(
        modifier = Modifier
            .padding(8.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SubcomposeAsyncImage(
            model = article.urlToImage,
            contentDescription = stringResource(R.string.headline_image_description),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 320.dp)
                .clip(shape = RoundedCornerShape(8.dp)),
            loading = { CircularProgressIndicator() },
        )
        if (article.author.isNotEmpty()) Text(
            text = stringResource(R.string.author) + article.author,
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = article.title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(text = article.description, style = MaterialTheme.typography.bodyMedium)
        Text(text = article.publishedAt, style = MaterialTheme.typography.bodySmall)

    }
}
