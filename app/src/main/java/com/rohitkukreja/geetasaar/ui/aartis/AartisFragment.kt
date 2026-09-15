package com.rohitkukreja.geetasaar.ui.aartis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohitkukreja.geetasaar.data.DataRepository
import com.rohitkukreja.geetasaar.databinding.FragmentAartisBinding

class AartisFragment : Fragment() {

    private var _binding: FragmentAartisBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAartisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val aartis = DataRepository.getAartis(requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = AartiAdapter(aartis)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
