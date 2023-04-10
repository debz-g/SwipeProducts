package dev.redfox.swipeproducts.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.redfox.swipeproducts.R
import dev.redfox.swipeproducts.databinding.FragmentSearchProductBinding

class SearchProductFragment : Fragment() {

    private var _binding: FragmentSearchProductBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchProductBinding.inflate(inflater, container, false)
        return inflater.inflate(R.layout.fragment_search_product, container, false)
    }

}