package dev.redfox.swipeproducts.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.redfox.swipeproducts.R
import dev.redfox.swipeproducts.adapters.ProductSearchAdapter
import dev.redfox.swipeproducts.databinding.FragmentSearchProductBinding
import dev.redfox.swipeproducts.models.productListModelItem
import dev.redfox.swipeproducts.networking.ProductListRepository
import dev.redfox.swipeproducts.viewmodels.ProductViewModel
import dev.redfox.swipeproducts.viewmodels.ProductViewModelFactory
import java.util.Locale

private lateinit var productSearchAdapter: ProductSearchAdapter

@AndroidEntryPoint
class SearchProductFragment : Fragment() {

    private var _binding: FragmentSearchProductBinding? = null
    private val binding
        get() = _binding!!

//    private val repository: ProductListRepository by lazy {
//        ProductListRepository()
//    }

    var productList = ArrayList<productListModelItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchProductBinding.inflate(inflater, container, false)

//        productViewModel = ViewModelProvider(this, ProductViewModelFactory(repository))[ProductViewModel::class.java]

        val productViewModel: ProductViewModel by viewModels()

        productViewModel.getProducts()

        val loadProgress = binding.shimmerEffect

        loadProgress.visibility = View.VISIBLE

        productViewModel.response.observe(viewLifecycleOwner, Observer {
             productList = it.body() as ArrayList<productListModelItem>

            loadProgress.visibility = View.GONE

            productSearchAdapter = ProductSearchAdapter(productList)
            binding.searchRecyclerView.setHasFixedSize(true)
            binding.searchRecyclerView.adapter = productSearchAdapter
            binding.searchRecyclerView.layoutManager = LinearLayoutManager(context)

            productSearchAdapter.onItemClick = {
                Toast.makeText(context, "One Click", Toast.LENGTH_SHORT).show()
            }
        })

        binding.fabAddProduct.setOnClickListener {
            findNavController().navigate(R.id.action_searchProductFragment_to_addProductFragment)
        }

        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })


        return binding.root
    }

    private fun filterList(query: String?){
        if(query!=null){
            val filteredList = ArrayList<productListModelItem>()
            for (i in productList){
                if(i.productName!!.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }

            if(filteredList.isEmpty()){
                Toast.makeText(requireContext(), "No data found", Toast.LENGTH_SHORT).show()
            } else {
                productSearchAdapter.setfilteredList(filteredList)
            }
        }
    }



}