package com.example.quotecollector

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class ExploreViewModel: ViewModel() {
    val quotes = mutableStateListOf<Quote>()

    fun refreshDummy(category: String) {
        quotes.clear()
        quotes.addAll(List<Quote>(10) { getDummyQuote(id = it, category = category) })
        //val test: List<Quote> = List<Quote>(10) { getDummyQuote(category) }
    }

    private fun getDummyQuote(id: Int, category: String): Quote {
        val names = listOf("Oscar Wilde", " Marilyn Monroe", "Albert Einstein", "Dr. Seuss", "Gandhi", "Mark Twain")
        val words = "I am so clever that sometimes I don't understand a single word of what I am saying".split(" ")

        val wordCount = Random.nextInt(5,20)
        var sentence = words.random()
        for (i in 1 .. wordCount) { sentence = sentence + " " + words.random() }
        sentence += "."

        return Quote(id = id, author = names.random(), text = sentence, category = category)
    }

    init {
        quotes.clear()
    }
}