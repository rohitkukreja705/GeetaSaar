package com.rohitkukreja.geetasaar.ui.chapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rohitkukreja.geetasaar.R
import com.rohitkukreja.geetasaar.data.Chapter
import com.rohitkukreja.geetasaar.databinding.ItemChapterBinding

class ChapterAdapter(
    private val chapters: List<Chapter>,
    private val onClick: (Chapter) -> Unit
) : RecyclerView.Adapter<ChapterAdapter.ChapterViewHolder>() {

    inner class ChapterViewHolder(val binding: ItemChapterBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val binding = ItemChapterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val chapter = chapters[position]
        with(holder.binding) {
            chapterNumber.text = chapter.chapter.toString()
            chapterNameSanskrit.text = chapter.nameSanskrit
            chapterNameTranslation.text = "${chapter.nameTransliterated} — ${chapter.nameTranslation}"
            chapterVerseCount.text = root.context.getString(R.string.verse_count_format, chapter.versesCount)
            root.setOnClickListener { onClick(chapter) }
        }
    }

    override fun getItemCount(): Int = chapters.size
}
