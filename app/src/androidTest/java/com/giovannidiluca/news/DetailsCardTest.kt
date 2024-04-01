package com.giovannidiluca.news

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.giovannidiluca.data.model.Article
import com.giovannidiluca.news.feed.DetailsCard
import org.junit.Rule
import org.junit.Test

class DetailsCardTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun detailsCard_shouldDisplayArticleDetails() {
        val article = Article(
            title = "Sample Article",
            description = "This is a sample article description",
            urlToImage = "https://example.com/image.jpg",
            publishedAt = "Mar, 22 2024",
            author = "John Doe",
            active = true,
        )

        composeTestRule.setContent {
            DetailsCard(article = article)
        }

        composeTestRule.onNodeWithText("Sample Article", substring = true).assertExists()
        composeTestRule.onNodeWithText("This is a sample article description", substring = true)
            .assertExists()
        composeTestRule.onNodeWithText("Mar, 22 2024", substring = true).assertExists()
        composeTestRule.onNodeWithText("Author: John Doe", substring = true).assertExists()
    }

    @Test
    fun detailsCard_shouldNotDisplayAuthorWhenIsEmpty() {
        val article = Article(
            title = "Sample Article",
            description = "This is a sample article description",
            urlToImage = "https://example.com/image.jpg",
            publishedAt = "Mar, 22 2024",
            author = "",
            active = true,
        )

        composeTestRule.setContent {
            DetailsCard(article = article)
        }

        composeTestRule.onNodeWithText("Author: John Doe", substring = true).assertDoesNotExist()
    }
}
