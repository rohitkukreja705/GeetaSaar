package com.rohitkukreja.geetasaar.ui.chapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohitkukreja.geetasaar.data.DataRepository
import com.rohitkukreja.geetasaar.databinding.FragmentChaptersBinding
import com.rohitkukreja.geetasaar.ui.verses.VerseListActivity

class ChaptersFragment : Fragment() {

    private var _binding: FragmentChaptersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChaptersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val chapters = DataRepository.getChapters(requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = ChapterAdapter(chapters) { chapter ->
            val intent = Intent(requireContext(), VerseListActivity::class.java)
            intent.putExtra(VerseListActivity.EXTRA_CHAPTER, chapter.chapter)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
