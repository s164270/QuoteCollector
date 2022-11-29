package com.example.quotecollector

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CollectionViewModel : ViewModel() {
    val quotes = mutableStateListOf<Quote>()

    fun testAdd() {
        quotes.add( Quote(quotes.size + 1, "Test", "testAdd", "Art") )
    }

    fun removeQuote(qId: Int) {
        quotes.removeIf { qId == it.id }
    }

    init {
        quotes.addAll(
            listOf(
                Quote(1, "Joe", "Quote test 1", "Science"),
                Quote(2, "Mike", "Quote test 2", "Science"),
                Quote(3, "Linda", "Quote test 3", "Science"),
                Quote(4, "Sofie", "Quote test 4", "Science"),
                Quote(5, "Rick", "Quote test 5", "Science"),
                Quote(6, "Kyle", "Quote test 6", "Science")
            )
        )
    }
}