package com.rohitkukreja.geetasaar.ui.verses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rohitkukreja.geetasaar.data.Verse
import com.rohitkukreja.geetasaar.databinding.ItemVerseBinding

class VerseAdapter(
    private val verses: List<Verse>,
    private val onClick: (Verse) -> Unit
) : RecyclerView.Adapter<VerseAdapter.VerseViewHolder>() {

    inner class VerseViewHolder(val binding: ItemVerseBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerseViewHolder {
        val binding = ItemVerseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VerseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VerseViewHolder, position: Int) {
        val verse = verses[position]
        with(holder.binding) {
            verseId.text = "Verse ${verse.id}"
            verseSanskrit.text = verse.sanskrit
            verseEnglishPreview.text = verse.english
            root.setOnClickListener { onClick(verse) }
        }
    }

    override fun getItemCount(): Int = verses.size
}
