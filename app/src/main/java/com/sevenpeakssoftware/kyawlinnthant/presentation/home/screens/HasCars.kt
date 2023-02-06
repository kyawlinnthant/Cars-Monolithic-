package com.sevenpeakssoftware.kyawlinnthant.presentation.home.screens

import android.content.Context
import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.sevenpeakssoftware.kyawlinnthant.BuildConfig
import com.sevenpeakssoftware.kyawlinnthant.app.theme.CarsTheme
import com.sevenpeakssoftware.kyawlinnthant.core.DateTimeConverter
import com.sevenpeakssoftware.kyawlinnthant.domain.model.CarVo

@Composable
fun HasCarsView(
    modifier: Modifier = Modifier,
    data: List<CarVo>,
    listState: LazyListState,
    onItemClicked: (Int) -> Unit
) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("has_cars"),
        state = listState,

        ) {
        items(
            items = data,
            key = { it.id }
        ) { item ->
            CarItem(
                id = item.id,
                image = item.image,
                title = item.title,
                date = DateTimeConverter.transform(context.is24(), item.publishedDate),
                ingress = item.ingress,
                onItemClicked = onItemClicked,
            )
        }
    }
}

@Composable
private fun CarItem(
    id: Int,
    image: String,
    title: String,
    date: String,
    ingress: String,
    onItemClicked: (Int) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(ratio = 1f),
        contentAlignment = Alignment.BottomCenter
    ) {
        AsyncImage(
            model = "${BuildConfig.BASE_URL}${image}",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio = 1f)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black
                        ),
                        startY = 220f,

                        )
                )
                .clickable {
                    onItemClicked(id)
                }
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 12.dp)
        ) {
            Text(
                text = title,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Text(
                text = date,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Text(
                text = ingress,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
private fun CarItemPreview() {
    CarsTheme {
        Surface {
            CarItem(
                id = 1,
                image = "",
                title = "This is title",
                date = "11:11",
                ingress = "Description is here",
                onItemClicked = {}
            )
        }
    }
}

fun Context.is24() = DateFormat.is24HourFormat(this)

