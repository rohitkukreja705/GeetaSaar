package com.rohitkukreja.geetasaar.ui.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rohitkukreja.geetasaar.data.ContentType
import com.rohitkukreja.geetasaar.data.FavoritesStore
import com.rohitkukreja.geetasaar.databinding.ItemFavoriteBinding

class FavoriteAdapter(
    private var entries: List<FavoriteEntry>,
    private val onClick: (FavoriteEntry) -> Unit,
    private val onRemoved: () -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(val binding: ItemFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val entry = entries[position]
        val context = holder.binding.root.context
        val (type, id, label, title, preview) = when (entry) {
            is FavoriteEntry.VerseEntry -> FavoriteRow(
                ContentType.VERSE, entry.verse.id, "GITA VERSE", "Verse ${entry.verse.id}", entry.verse.english
            )
            is FavoriteEntry.MantraEntry -> FavoriteRow(
                ContentType.MANTRA, entry.mantra.id, "MANTRA", entry.mantra.title, entry.mantra.meaning
            )
            is FavoriteEntry.AartiEntry -> FavoriteRow(
                ContentType.AARTI, entry.aarti.id, "AARTI", entry.aarti.title, entry.aarti.meaning
            )
        }
        with(holder.binding) {
            typeLabel.text = label
            this.title.text = title
            this.preview.text = preview
            root.setOnClickListener { onClick(entry) }
            removeButton.setOnClickListener {
                FavoritesStore.toggleFavorite(context, type, id)
                val mutable = entries.toMutableList()
                mutable.removeAt(holder.bindingAdapterPosition)
                entries = mutable
                notifyItemRemoved(position)
                onRemoved()
            }
        }
    }

    override fun getItemCount(): Int = entries.size

    private data class FavoriteRow(
        val type: ContentType,
        val id: String,
        val label: String,
        val title: String,
        val preview: String
    )
}
