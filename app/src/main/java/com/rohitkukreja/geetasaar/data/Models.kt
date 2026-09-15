package com.rohitkukreja.geetasaar.data

/** A single chapter of the Bhagavad Gita. */
data class Chapter(
    val chapter: Int,
    val nameSanskrit: String,
    val nameTransliterated: String,
    val nameTranslation: String,
    val versesCount: Int,
    val summaryEnglish: String,
    val summaryHindi: String
)

/** A single shloka/verse of the Bhagavad Gita. */
data class Verse(
    val chapter: Int,
    val verse: Int,
    val id: String,
    val sanskrit: String,
    val transliteration: String,
    val wordMeanings: String,
    val hindi: String,
    val english: String
)

/** A standalone Vedic/devotional mantra. */
data class Mantra(
    val id: String,
    val title: String,
    val category: String,
    val sanskrit: String,
    val transliteration: String,
    val meaning: String
)

/** An Aarti (devotional hymn) dedicated to a deity. */
data class Aarti(
    val id: String,
    val title: String,
    val deity: String,
    val hindi: String,
    val meaning: String
)

/** Marker for the kind of content a favorite/search-result entry refers to. */
enum class ContentType {
    VERSE, MANTRA, AARTI
}
