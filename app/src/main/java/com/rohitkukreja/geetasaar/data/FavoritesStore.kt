package com.rohitkukreja.geetasaar.data

import android.content.Context

/**
 * Persists favorited verse/mantra/aarti ids in SharedPreferences, namespaced by [ContentType].
 */
object FavoritesStore {

    private const val PREFS_NAME = "geeta_saar_favorites"

    private fun keyFor(type: ContentType) = "favorites_${type.name}"

    private fun prefs(context: Context) =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun isFavorite(context: Context, type: ContentType, id: String): Boolean =
        prefs(context).getStringSet(keyFor(type), emptySet())?.contains(id) == true

    fun toggleFavorite(context: Context, type: ContentType, id: String): Boolean {
        val p = prefs(context)
        val key = keyFor(type)
        val current = HashSet(p.getStringSet(key, emptySet()) ?: emptySet())
        val nowFavorite: Boolean
        if (current.contains(id)) {
            current.remove(id)
            nowFavorite = false
        } else {
            current.add(id)
            nowFavorite = true
        }
        p.edit().putStringSet(key, current).apply()
        return nowFavorite
    }

    fun getFavoriteIds(context: Context, type: ContentType): Set<String> =
        prefs(context).getStringSet(keyFor(type), emptySet()) ?: emptySet()
}
