package com.runway.routes.feature.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.runway.routes.R
import com.runway.routes.domain.entity.Owner
import com.runway.routes.domain.entity.RunwayEntity
import com.runway.routes.domain.entity.RunwayType
import com.runway.routes.domain.entity.regionText
import com.runway.routes.ui.composable.SearchView

@Composable
fun ListContent(
    component: ListComponent,
    modifier: Modifier = Modifier
) {

    val runwaysLazyItems = component.runways.collectAsLazyPagingItems()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var textState by remember { mutableStateOf(TextFieldValue("")) }
        SearchView(textState) { state ->
            textState = state
            component.updateFilter(state.text)
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(2.dp),
        ) {
            items(items = runwaysLazyItems) { runway ->
                runway?.let { SuccessItemComponent(runway) }
            }
            runwaysLazyItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { ProgressItem(modifier.fillParentMaxSize()) }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                }
            }
        }
    }
}

@Composable
private fun SuccessItemComponent(runway: RunwayEntity) {
    Card(
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(horizontal = 4.dp, vertical = 2.dp))
    ) {
        Column(
            modifier = Modifier.padding(4.dp)
        ) {
            Text(
                text = "#${runway.runwayId}",
                color = Color.Red,
                maxLines = 1
            )
            Text(
                color = Color.Black,
                maxLines = 1,
                text = "${runway.nameRu} - ${runway.indexRu}"
            )
            val regionText = runway.regionText()
            if (!regionText.isNullOrEmpty()) {
                Text(
                    color = Color.Black,
                    maxLines = 1,
                    text = regionText
                )
            }
            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    color = Color.Black,
                    maxLines = 1,
                    text = "${runway.belongs?.value}"
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .background(Color.Yellow),
                    color = Color.Red,
                    maxLines = 1,
                    text = stringResource(R.string.distance, runway.distanceKm!!)
                )
            }
        }
    }
}

@Composable
private fun ProgressItem(modifier: Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun LoadingItem() {
    CircularProgressIndicator(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Preview
@Composable
fun SuccessItemComponentPreview() {
    val runway = RunwayEntity(
        1, 1, 10.0, 20.0,
        "nameEn", "nameRu", true, RunwayType.AIRPORT,
        "indexEx", "indexRu", Owner.GA,
        "Belarus", "Minsk obl", "Minsk", 111.0
    )
    SuccessItemComponent(runway)
}