package dev.redfox.swipeproducts.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.redfox.swipeproducts.R
import dev.redfox.swipeproducts.adapters.ProductSearchAdapter
import dev.redfox.swipeproducts.databinding.FragmentSearchProductBinding
import dev.redfox.swipeproducts.models.productListModelItem
import dev.redfox.swipeproducts.viewmodels.ProductViewModel
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

        val productViewModel: ProductViewModel by viewModels()

        productViewModel.getProducts()

        productViewModel.response.observe(viewLifecycleOwner, Observer {
             productList = it.body() as ArrayList<productListModelItem>

            productSearchAdapter = ProductSearchAdapter(productList)
            binding.searchRecyclerView.setHasFixedSize(true)
            binding.searchRecyclerView.adapter = productSearchAdapter
            binding.searchRecyclerView.layoutManager = LinearLayoutManager(context)

            binding.shimmerEffect.visibility = View.INVISIBLE

            productSearchAdapter.onItemClick = {
                val dialog = ProductDetailsBottomSheet(it)
                dialog.isCancelable = true
                dialog.show(parentFragmentManager,"ProductBottomSheetDialogFragment")
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
                if(i.productName!!.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))){
                    filteredList.add(i)
                }
            }

            if(filteredList.isEmpty()){
                binding.searchRecyclerView.visibility = View.INVISIBLE
                binding.noData.visibility = View.VISIBLE
            } else {
                binding.searchRecyclerView.visibility = View.VISIBLE
                binding.noData.visibility = View.INVISIBLE
                productSearchAdapter.setfilteredList(filteredList)
            }
        }
    }



}