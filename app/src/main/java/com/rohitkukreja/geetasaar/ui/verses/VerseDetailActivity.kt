package com.rohitkukreja.geetasaar.ui.verses

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.rohitkukreja.geetasaar.R
import com.rohitkukreja.geetasaar.data.ContentType
import com.rohitkukreja.geetasaar.data.DataRepository
import com.rohitkukreja.geetasaar.data.FavoritesStore
import com.rohitkukreja.geetasaar.data.Verse
import com.rohitkukreja.geetasaar.databinding.ActivityVerseDetailBinding

class VerseDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_VERSE_ID = "extra_verse_id"
    }

    private lateinit var binding: ActivityVerseDetailBinding
    private lateinit var verse: Verse
    private var favoriteMenuItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerseDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val verseId = intent.getStringExtra(EXTRA_VERSE_ID) ?: return finish()
        verse = DataRepository.getVerse(this, verseId) ?: return finish()

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = "Verse ${verse.id}"
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        showTab(0)
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) = showTab(tab.position)
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        if (verse.wordMeanings.isBlank()) {
            binding.wordMeaningsCard.visibility = android.view.View.GONE
        } else {
            binding.wordMeaningsText.text = verse.wordMeanings
        }
    }

    private fun showTab(position: Int) {
        binding.verseText.text = when (position) {
            0 -> verse.sanskrit
            1 -> verse.transliteration
            2 -> verse.hindi
            else -> verse.english
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.verse_detail_menu, menu)
        favoriteMenuItem = menu.findItem(R.id.action_favorite)
        updateFavoriteIcon()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_favorite -> {
                FavoritesStore.toggleFavorite(this, ContentType.VERSE, verse.id)
                updateFavoriteIcon()
                true
            }
            R.id.action_share -> {
                shareVerse()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun updateFavoriteIcon() {
        val isFavorite = FavoritesStore.isFavorite(this, ContentType.VERSE, verse.id)
        favoriteMenuItem?.setIcon(
            if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        )
    }

    private fun shareVerse() {
        val text = buildString {
            append("Bhagavad Gita ${verse.id}\n\n")
            append(verse.sanskrit).append("\n\n")
            append(verse.english)
        }
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }
        startActivity(Intent.createChooser(shareIntent, "Share verse"))
    }
}
