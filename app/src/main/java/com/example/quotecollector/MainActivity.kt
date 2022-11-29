
package com.example.quotecollector

import android.os.Bundle
import android.util.Log
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuoteCollectorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavControl(collectionViewModel)
                    //MyCollectionScreen()
                    //HomeScreen()
                    //Greeting("Android")
                }
            }
        }
    }
}


@Composable
fun NavControl(collectionViewModel: CollectionViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destination.Home.route) {
        composable(Destination.Home.route) {
            HomeScreen(navController)
        }
        composable(Destination.ExploreCategory.route) {
            CategoryScreen()
        }
        composable(Destination.ExploreList.route) {
            ExploreScreen()
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
    Column() {
        Button(onClick = { navController.navigate(Destination.ExploreCategory.route) }) {
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
fun CategoryScreen() {
    Column() {
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Art")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Politics")
        }

        Button(onClick = { /*TODO*/ }) {
            Text(text = "Science")
        }
    }
}

@Composable
fun ExploreScreen(quotes: List<Int> = List(10) {it}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally // ,verticalArrangement = Arrangement.SpaceBetween
    ) {
        ExploreQuotesList(modifier = Modifier.weight(1f))
        RefreshButton(modifier = Modifier.padding(25.dp))
    }
}

@Composable
fun ExploreQuotesList(modifier: Modifier = Modifier, quotes: List<Int> = List(50) {it}) {
    LazyColumn(modifier = modifier) {
        items(items = quotes) {
            Text(text = it.toString())
        }
    }
}

@Composable
fun MyCollectionScreen(collectionViewModel: CollectionViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally // ,verticalArrangement = Arrangement.SpaceBetween
    ) {
        MyQuotesList(
            modifier = Modifier.weight(1f),
            quotes = collectionViewModel.quotes) { qId -> collectionViewModel.removeQuote(qId) }
        //RefreshButton(modifier = Modifier.padding(25.dp))
    }
}

@Composable
fun MyQuotesList(modifier: Modifier = Modifier, quotes: List<Quote>, onClick: (qId: Int) -> Unit) {
    val context = LocalContext.current
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
fun RefreshButton(modifier: Modifier = Modifier,) {
    Button(modifier = modifier, onClick = { /*TODO*/ }) {
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