
package com.example.quotecollector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.quotecollector.ui.theme.QuoteCollectorTheme

class MainActivity : ComponentActivity() {

    val collectionViewModel = CollectionViewModel()
    val exploreViewModel = ExploreViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuoteCollectorTheme {
                NavControl(collectionViewModel, exploreViewModel)
                // A surface container using the 'background' color from the theme
                //Surface(modifier = Modifier.fillMaxSize(),color = MaterialTheme.colorScheme.background) {}
            }
        }
    }
}


@Composable
fun NavControl(collectionViewModel: CollectionViewModel, exploreViewModel: ExploreViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.Home.route) {
        composable(Destination.Home.route) {
            HomeScreen(navController)
        }
        composable(Destination.SelectCategory.route) {
            CategoryScreen(exploreViewModel) {x -> navController.navigate(Destination.ExploreList.exploreCategory(x))}
        }
        composable(Destination.ExploreList.route, arguments = listOf(
            navArgument("category") {
                type = NavType.StringType
                this.nullable = false
            }
        )) {
            ExploreScreen(exploreViewModel, it.arguments?.getString("category") ?: "")
        }
        composable(Destination.MyCollection.route) {
            MyCollectionScreen(collectionViewModel)
        }
        composable(Destination.Details.route, arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
                this.nullable = false
            }
        )) {
            //TODO quote detail view
        }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.padding(15.dp)) {
        Button(onClick = { navController.navigate(Destination.SelectCategory.route) }) {
            Text(text = "Categories")
        }

        Button(onClick = { navController.navigate(Destination.ExploreList.route) }) {
            Text(text = "Explore list")
        }

        Button(onClick = { navController.navigate(Destination.MyCollection.route) }) {
            Text(text = "My Collection")
        }
    }
}

@Composable
fun CategoryScreen(exploreViewModel: ExploreViewModel, navToCategory: (cat: String) -> (Unit)) {
    Column() {
        Button(onClick = { navToCategory("Art") }) {
            Text(text = "Art")
        }

        Button(onClick = { navToCategory("Politics") }) {
            Text(text = "Politics")
        }

        Button(onClick = { navToCategory("Science") }) {
            Text(text = "Science")
        }
    }
}

@Composable
fun ExploreScreen(exploreViewModel: ExploreViewModel, category: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally // ,verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Explore quotes related to $category")
        QuotesList(modifier = Modifier.weight(1f), quotes = exploreViewModel.quotes){}
        RefreshButton(modifier = Modifier.padding(25.dp)){exploreViewModel.refreshDummy("Art")}
    }
}

@Composable
fun MyCollectionScreen(collectionViewModel: CollectionViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally // ,verticalArrangement = Arrangement.SpaceBetween
    ) {
        QuotesList(
            modifier = Modifier.weight(1f),
            quotes = collectionViewModel.quotes)
        { qId -> collectionViewModel.removeQuote(qId) }
        //RefreshButton(modifier = Modifier.padding(25.dp))
    }
}

@Composable
fun QuotesList(modifier: Modifier = Modifier, quotes: List<Quote>, onClick: (qId: Int) -> Unit) {
    LazyColumn(modifier = modifier) {
        items(items = quotes,key = {it.id}) { 
            Surface(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .fillMaxWidth()
                    .clickable { onClick(it.id) },
                color = MaterialTheme.colorScheme.secondary)
            {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = it.author)
                    Text(text = it.text)
                }
            }
        }
    }
}

@Composable
fun RefreshButton(modifier: Modifier = Modifier, clickRefresh: () -> Unit) {
    Button(modifier = modifier, onClick = { clickRefresh() }) {
        Text(text = "REFRESH")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuoteCollectorTheme {
        Greeting("Android")
    }
}