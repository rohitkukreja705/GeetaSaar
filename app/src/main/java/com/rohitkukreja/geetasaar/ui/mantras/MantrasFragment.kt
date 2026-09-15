package com.rohitkukreja.geetasaar.ui.mantras

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rohitkukreja.geetasaar.data.DataRepository
import com.rohitkukreja.geetasaar.databinding.FragmentMantrasBinding

class MantrasFragment : Fragment() {

    private var _binding: FragmentMantrasBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMantrasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mantras = DataRepository.getMantras(requireContext())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = MantraAdapter(mantras)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
