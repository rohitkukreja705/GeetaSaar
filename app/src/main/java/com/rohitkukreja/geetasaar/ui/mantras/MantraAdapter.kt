package com.rohitkukreja.geetasaar.ui.mantras

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rohitkukreja.geetasaar.R
import com.rohitkukreja.geetasaar.data.ContentType
import com.rohitkukreja.geetasaar.data.FavoritesStore
import com.rohitkukreja.geetasaar.data.Mantra
import com.rohitkukreja.geetasaar.databinding.ItemMantraBinding

class MantraAdapter(private val mantras: List<Mantra>) :
    RecyclerView.Adapter<MantraAdapter.MantraViewHolder>() {

    private val expandedIds = HashSet<String>()

    inner class MantraViewHolder(val binding: ItemMantraBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MantraViewHolder {
        val binding = ItemMantraBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MantraViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MantraViewHolder, position: Int) {
        val mantra = mantras[position]
        val context = holder.binding.root.context
        with(holder.binding) {
            title.text = mantra.title
            category.text = mantra.category
            sanskrit.text = mantra.sanskrit
            transliteration.text = mantra.transliteration
            meaning.text = mantra.meaning

            val expanded = expandedIds.contains(mantra.id)
            detailContainer.visibility = if (expanded) android.view.View.VISIBLE else android.view.View.GONE
            expandIcon.rotation = if (expanded) 180f else 0f

            headerRow.setOnClickListener {
                if (expandedIds.contains(mantra.id)) expandedIds.remove(mantra.id)
                else expandedIds.add(mantra.id)
                notifyItemChanged(position)
            }

            fun refreshFavoriteIcon() {
                val isFavorite = FavoritesStore.isFavorite(context, ContentType.MANTRA, mantra.id)
                favoriteButton.setImageResource(
                    if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                )
            }
            refreshFavoriteIcon()
            favoriteButton.setOnClickListener {
                FavoritesStore.toggleFavorite(context, ContentType.MANTRA, mantra.id)
                refreshFavoriteIcon()
            }
        }
    }

    override fun getItemCount(): Int = mantras.size
}
