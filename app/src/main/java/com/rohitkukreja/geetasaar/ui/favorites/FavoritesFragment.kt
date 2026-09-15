package com.rohitkukreja.geetasaar.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohitkukreja.geetasaar.data.ContentType
import com.rohitkukreja.geetasaar.data.DataRepository
import com.rohitkukreja.geetasaar.data.FavoritesStore
import com.rohitkukreja.geetasaar.databinding.FragmentFavoritesBinding
import com.rohitkukreja.geetasaar.ui.verses.VerseDetailActivity

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // Favorites can change while another activity/fragment is on top, so reload every time this tab is shown.
        loadFavorites()
    }

    private fun loadFavorites() {
        val context = requireContext()
        val entries = ArrayList<FavoriteEntry>()

        val favoriteVerseIds = FavoritesStore.getFavoriteIds(context, ContentType.VERSE)
        DataRepository.getVerses(context).filter { it.id in favoriteVerseIds }
            .forEach { entries.add(FavoriteEntry.VerseEntry(it)) }

        val favoriteMantraIds = FavoritesStore.getFavoriteIds(context, ContentType.MANTRA)
        DataRepository.getMantras(context).filter { it.id in favoriteMantraIds }
            .forEach { entries.add(FavoriteEntry.MantraEntry(it)) }

        val favoriteAartiIds = FavoritesStore.getFavoriteIds(context, ContentType.AARTI)
        DataRepository.getAartis(context).filter { it.id in favoriteAartiIds }
            .forEach { entries.add(FavoriteEntry.AartiEntry(it)) }

        binding.emptyText.visibility = if (entries.isEmpty()) View.VISIBLE else View.GONE
        binding.recyclerView.visibility = if (entries.isEmpty()) View.GONE else View.VISIBLE

        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = FavoriteAdapter(
            entries,
            onClick = { entry ->
                if (entry is FavoriteEntry.VerseEntry) {
                    val intent = Intent(context, VerseDetailActivity::class.java)
                    intent.putExtra(VerseDetailActivity.EXTRA_VERSE_ID, entry.verse.id)
                    startActivity(intent)
                }
            },
            onRemoved = {
                if ((binding.recyclerView.adapter?.itemCount ?: 0) == 0) {
                    binding.emptyText.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
