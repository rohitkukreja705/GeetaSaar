package com.rohitkukreja.geetasaar.ui.aartis

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rohitkukreja.geetasaar.R
import com.rohitkukreja.geetasaar.data.Aarti
import com.rohitkukreja.geetasaar.data.ContentType
import com.rohitkukreja.geetasaar.data.FavoritesStore
import com.rohitkukreja.geetasaar.databinding.ItemAartiBinding

class AartiAdapter(private val aartis: List<Aarti>) :
    RecyclerView.Adapter<AartiAdapter.AartiViewHolder>() {

    private val expandedIds = HashSet<String>()

    inner class AartiViewHolder(val binding: ItemAartiBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AartiViewHolder {
        val binding = ItemAartiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AartiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AartiViewHolder, position: Int) {
        val aarti = aartis[position]
        val context = holder.binding.root.context
        with(holder.binding) {
            title.text = aarti.title
            deity.text = aarti.deity
            hindi.text = aarti.hindi
            meaning.text = aarti.meaning

            val expanded = expandedIds.contains(aarti.id)
            detailContainer.visibility = if (expanded) android.view.View.VISIBLE else android.view.View.GONE
            expandIcon.rotation = if (expanded) 180f else 0f

            headerRow.setOnClickListener {
                if (expandedIds.contains(aarti.id)) expandedIds.remove(aarti.id)
                else expandedIds.add(aarti.id)
                notifyItemChanged(position)
            }

            fun refreshFavoriteIcon() {
                val isFavorite = FavoritesStore.isFavorite(context, ContentType.AARTI, aarti.id)
                favoriteButton.setImageResource(
                    if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
                )
            }
            refreshFavoriteIcon()
            favoriteButton.setOnClickListener {
                FavoritesStore.toggleFavorite(context, ContentType.AARTI, aarti.id)
                refreshFavoriteIcon()
            }
        }
    }

    override fun getItemCount(): Int = aartis.size
}
