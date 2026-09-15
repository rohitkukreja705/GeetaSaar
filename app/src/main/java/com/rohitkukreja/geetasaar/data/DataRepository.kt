package com.rohitkukreja.geetasaar.data

import android.content.Context
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Loads Gita chapters/verses and the mantras/aartis bundled as JSON assets,
 * and keeps them cached in memory for the lifetime of the process.
 */
object DataRepository {

    private var chapters: List<Chapter>? = null
    private var verses: List<Verse>? = null
    private var mantras: List<Mantra>? = null
    private var aartis: List<Aarti>? = null

    private fun readAsset(context: Context, fileName: String): String {
        context.assets.open(fileName).use { stream ->
            BufferedReader(InputStreamReader(stream, Charsets.UTF_8)).use { reader ->
                return reader.readText()
            }
        }
    }

    fun getChapters(context: Context): List<Chapter> {
        chapters?.let { return it }
        val json = JSONArray(readAsset(context, "gita_chapters.json"))
        val list = ArrayList<Chapter>(json.length())
        for (i in 0 until json.length()) {
            val o = json.getJSONObject(i)
            list.add(
                Chapter(
                    chapter = o.getInt("chapter"),
                    nameSanskrit = o.optString("name_sanskrit"),
                    nameTransliterated = o.optString("name_transliterated"),
                    nameTranslation = o.optString("name_translation"),
                    versesCount = o.optInt("verses_count"),
                    summaryEnglish = o.optString("summary_english"),
                    summaryHindi = o.optString("summary_hindi")
                )
            )
        }
        list.sortBy { it.chapter }
        chapters = list
        return list
    }

    fun getVerses(context: Context): List<Verse> {
        verses?.let { return it }
        val json = JSONArray(readAsset(context, "gita_verses.json"))
        val list = ArrayList<Verse>(json.length())
        for (i in 0 until json.length()) {
            val o = json.getJSONObject(i)
            list.add(
                Verse(
                    chapter = o.getInt("chapter"),
                    verse = o.getInt("verse"),
                    id = o.getString("id"),
                    sanskrit = o.optString("sanskrit"),
                    transliteration = o.optString("transliteration"),
                    wordMeanings = o.optString("word_meanings"),
                    hindi = o.optString("hindi"),
                    english = o.optString("english")
                )
            )
        }
        verses = list
        return list
    }

    fun getVersesForChapter(context: Context, chapterNumber: Int): List<Verse> =
        getVerses(context).filter { it.chapter == chapterNumber }.sortedBy { it.verse }

    fun getVerse(context: Context, id: String): Verse? =
        getVerses(context).firstOrNull { it.id == id }

    fun getMantras(context: Context): List<Mantra> {
        mantras?.let { return it }
        val json = JSONArray(readAsset(context, "mantras.json"))
        val list = ArrayList<Mantra>(json.length())
        for (i in 0 until json.length()) {
            val o = json.getJSONObject(i)
            list.add(
                Mantra(
                    id = o.getString("id"),
                    title = o.optString("title"),
                    category = o.optString("category"),
                    sanskrit = o.optString("sanskrit"),
                    transliteration = o.optString("transliteration"),
                    meaning = o.optString("meaning")
                )
            )
        }
        mantras = list
        return list
    }

    fun getAartis(context: Context): List<Aarti> {
        aartis?.let { return it }
        val json = JSONArray(readAsset(context, "aartis.json"))
        val list = ArrayList<Aarti>(json.length())
        for (i in 0 until json.length()) {
            val o = json.getJSONObject(i)
            list.add(
                Aarti(
                    id = o.getString("id"),
                    title = o.optString("title"),
                    deity = o.optString("deity"),
                    hindi = o.optString("hindi"),
                    meaning = o.optString("meaning")
                )
            )
        }
        aartis = list
        return list
    }

    /** Case-insensitive search across Sanskrit, transliteration, Hindi and English text of every verse. */
    fun searchVerses(context: Context, query: String): List<Verse> {
        if (query.isBlank()) return emptyList()
        val q = query.trim()
        return getVerses(context).filter {
            it.sanskrit.contains(q, ignoreCase = true) ||
                it.transliteration.contains(q, ignoreCase = true) ||
                it.hindi.contains(q, ignoreCase = true) ||
                it.english.contains(q, ignoreCase = true) ||
                it.id == q
        }
    }
}
