package com.rohitkukreja.geetasaar.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohitkukreja.geetasaar.data.DataRepository
import com.rohitkukreja.geetasaar.databinding.ActivitySearchBinding
import com.rohitkukreja.geetasaar.ui.verses.VerseAdapter
import com.rohitkukreja.geetasaar.ui.verses.VerseDetailActivity

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.searchInput.requestFocus()

        binding.searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                runSearch(s?.toString().orEmpty())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun runSearch(query: String) {
        val results = DataRepository.searchVerses(this, query)
        binding.emptyText.visibility = if (query.isNotBlank() && results.isEmpty()) View.VISIBLE else View.GONE
        binding.recyclerView.adapter = VerseAdapter(results) { verse ->
            val intent = Intent(this, VerseDetailActivity::class.java)
            intent.putExtra(VerseDetailActivity.EXTRA_VERSE_ID, verse.id)
            startActivity(intent)
        }
    }
}
