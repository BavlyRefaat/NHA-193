package com.depi.bookdiscovery.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.depi.bookdiscovery.SearchViewModel
import com.depi.bookdiscovery.SearchViewModelFactory
import com.depi.bookdiscovery.components.BookCard
import com.depi.bookdiscovery.dto.FilterOption
import com.depi.bookdiscovery.ui.theme.BookDiscoveryTheme
import com.depi.bookdiscovery.util.SettingsDataStore
import com.depi.bookdiscovery.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavController,
    searchViewModel: SearchViewModel,
) {
    var searchText by remember { mutableStateOf("") }
    val uiState by searchViewModel.uiState.collectAsStateWithLifecycle()
    val searchHistory by searchViewModel.searchHistory.collectAsStateWithLifecycle(initialValue = emptySet())
    val listState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(searchText) {
        if (searchText.isNotEmpty()) {
            listState.scrollToItem(0)
        }
    }

    val filterOptions = listOf(
        FilterOption("All", ""),
        FilterOption("Books", "books"),
        FilterOption("Magazines", "magazines")
    )
    var selectedFilter by remember { mutableStateOf(filterOptions.first()) }

    val trendingTerms = remember {
        mutableStateListOf(
            "AI and Machine Learning",
            "Climate Science",
            "Modern Romance",
            "Space Exploration",
            "Productivity",
            "Mental Health"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Search", color = MaterialTheme.colorScheme.onBackground) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    searchViewModel.search(it, selectedFilter.value)
                },
                placeholder = {
                    Text(
                        "Search books...",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(
                                Icons.Filled.Close,
                                contentDescription = "Clear search",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = {
                    searchViewModel.searchNow(searchText, selectedFilter.value)
                    keyboardController?.hide()
                }),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(filterOptions) { filter ->
                    FilterChip(
                        selected = filter == selectedFilter,
                        onClick = { selectedFilter = filter },
                        label = { Text(filter.label) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (searchText.isEmpty()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Recent Searches",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    if (searchHistory.isNotEmpty()) {
                        TextButton(onClick = { searchViewModel.clearSearchHistory() }) {
                            Text("Clear All")
                        }
                    }
                }
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(searchHistory.toList().reversed()) { search ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { searchText = search }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Filled.History,
                                contentDescription = "History",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = search,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Trending Terms",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(trendingTerms) { term ->
                        FilterChip(
                            onClick = { searchText = term },
                            label = { Text(term) },
                            selected = false
                        )
                    }
                }
            } else {
                when (val state = uiState) {
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is UiState.Success -> {
                        if (state.data.isEmpty()) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("No results found.")
                            }
                        } else {
                            LazyColumn(
                                state = listState,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                items(state.data) { book ->
                                    BookCard(book = book)
                                }
                                item {
                                    if (state.data.isNotEmpty()) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            CircularProgressIndicator()
                                        }
                                    }
                                }
                            }

                            LaunchedEffect(listState) {
                                snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                                    .collect { lastVisibleItemIndex ->
                                        if (lastVisibleItemIndex != null && lastVisibleItemIndex >= state.data.size - 5) {
                                            searchViewModel.loadMore()
                                        }
                                    }
                            }
                        }
                    }

                    is UiState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(state.message)
                        }
                    }

                    is UiState.Idle -> {
                        // Do nothing
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    BookDiscoveryTheme {
        val context = LocalContext.current
        val settingsDataStore = remember { SettingsDataStore(context) }
        val searchViewModel: SearchViewModel = viewModel(
            factory = SearchViewModelFactory(context, settingsDataStore)
        )
        SearchScreen(
            navController = rememberNavController(),
            searchViewModel = searchViewModel
        )
    }
}