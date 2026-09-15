package com.rohitkukreja.geetasaar.ui.favorites

import com.rohitkukreja.geetasaar.data.Aarti
import com.rohitkukreja.geetasaar.data.Mantra
import com.rohitkukreja.geetasaar.data.Verse

/** A single row shown in the Favorites tab, wrapping whichever content type was favorited. */
sealed class FavoriteEntry {
    data class VerseEntry(val verse: Verse) : FavoriteEntry()
    data class MantraEntry(val mantra: Mantra) : FavoriteEntry()
    data class AartiEntry(val aarti: Aarti) : FavoriteEntry()
}
