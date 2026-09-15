package com.rohitkukreja.geetasaar.ui.verses

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohitkukreja.geetasaar.data.DataRepository
import com.rohitkukreja.geetasaar.databinding.ActivityVerseListBinding

class VerseListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CHAPTER = "extra_chapter"
    }

    private lateinit var binding: ActivityVerseListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerseListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val chapterNumber = intent.getIntExtra(EXTRA_CHAPTER, 1)
        val chapter = DataRepository.getChapters(this).firstOrNull { it.chapter == chapterNumber }

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = chapter?.let { "Chapter $chapterNumber — ${it.nameTransliterated}" }
            ?: "Chapter $chapterNumber"
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }

        binding.chapterSummary.text = chapter?.summaryEnglish.orEmpty()

        val verses = DataRepository.getVersesForChapter(this, chapterNumber)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = VerseAdapter(verses) { verse ->
            val intent = Intent(this, VerseDetailActivity::class.java)
            intent.putExtra(VerseDetailActivity.EXTRA_VERSE_ID, verse.id)
            startActivity(intent)
        }
    }
}
