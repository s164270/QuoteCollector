package com.example.quotecollector

sealed class Destination(val route: String) {
    object Home: Destination("home")
    object SelectCategory: Destination("explore_category")
    object ExploreList: Destination("explore/{category}") {
        fun exploreCategory(category: String) = route.replace("{category}", category)
    }
    object MyCollection: Destination("my:collection")
    object Details: Destination("quote/{id}") {
        fun quoteDetails(id: Int) = route.replace("{id}", id.toString())
    }
}