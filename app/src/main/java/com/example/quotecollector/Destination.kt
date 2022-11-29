package com.example.quotecollector

sealed class Destination(val route: String) {
    object Home: Destination("home")
    object ExploreCategory: Destination("explore_category")
    object ExploreList: Destination("explore_list")
    object MyCollection: Destination("my:collection")
    object Details: Destination("quote/{id}") {
        fun quoteDetails(id: Int) = route.replace("{id}", id.toString())
    }
}