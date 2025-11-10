package com.depi.bookdiscovery.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.depi.bookdiscovery.R
import com.depi.bookdiscovery.UiUtil.DepiSearchBar
import com.depi.bookdiscovery.ui.viewmodel.SettingsViewModel

@Composable
fun MainScreen(settingsViewModel: SettingsViewModel) {
    MainContent()
}

@Composable
fun MainContent() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        item {
            DepiSearchBar()
            Spacer(modifier = Modifier.height(16.dp))
            ContinueReading()
            Spacer(modifier = Modifier.height(16.dp))
            Bestsellers()
            Spacer(modifier = Modifier.height(16.dp))
            NewReleases()
        }
    }
}

@Composable
fun ContinueReading() {
    Column {
        Text(text = stringResource(R.string.main_continue_reading), color = MaterialTheme.colorScheme.onBackground)
        // TODO: Implement Continue Reading section
        Text(text = "Placeholder for Continue Reading", color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun Bestsellers() {
    val bestsellers = listOf("Book 1", "Book 2", "Book 3", "Book 4", "Book 5")

    Column {
        Text(text = stringResource(R.string.main_bestsellers), color = MaterialTheme.colorScheme.onBackground)
        LazyRow {
            items(bestsellers) { book ->
                Text(text = book, modifier = Modifier.padding(8.dp), color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}

@Composable
fun NewReleases() {
    val newReleases = listOf("Book 6", "Book 7", "Book 8", "Book 9", "Book 10")

    Column {
        Text(text = stringResource(R.string.main_new_releases), color = MaterialTheme.colorScheme.onBackground)
        LazyRow {
            items(newReleases) { book ->
                Text(text = book, modifier = Modifier.padding(8.dp), color = MaterialTheme.colorScheme.onBackground)
            }
        }
    }
}
