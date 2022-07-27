package com.runway.routes.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.runway.routes.domain.PREVIEW_HEIGHT
import com.runway.routes.domain.PREVIEW_WIDTH
import com.runway.routes.domain.entity.getPreviewMapUri
import com.runway.routes.domain.entity.ownerStringResource


@Composable
fun DetailsContent(
    component: DetailsComponent,
    modifier: Modifier = Modifier,
    popToMain: () -> Unit
) {

    val runway = component.model.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = runway.nameRu) },
                navigationIcon = {
                    IconButton(onClick = popToMain) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }, actions = {})
        },
        content = { contentPadding ->
            Column(modifier = Modifier.padding(contentPadding)) {
                AsyncImage(
                    model = runway.getPreviewMapUri(),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(PREVIEW_WIDTH / PREVIEW_HEIGHT.toFloat())
                )
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "Наименование - ${runway.nameRu}")
                    Text(text = "Наименование [eng] - ${runway.nameEn}")
                    Text(text = "Индекс - ${runway.indexRu} / ${runway.indexEn}")
                    Text(text = "Страна - ${runway.country}")
                    Text(text = "Регион - ${runway.region}")
                    Text(text = "Принадлежность - ${runway.belongs?.ownerStringResource()}")
                }
            }
        }
    )
}