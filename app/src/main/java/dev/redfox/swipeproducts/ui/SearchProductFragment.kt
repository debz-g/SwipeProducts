package dev.redfox.swipeproducts.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.redfox.swipeproducts.R
import dev.redfox.swipeproducts.adapters.ProductSearchAdapter
import dev.redfox.swipeproducts.databinding.FragmentSearchProductBinding
import dev.redfox.swipeproducts.models.productListModelItem
import dev.redfox.swipeproducts.networking.ProductListRepository
import dev.redfox.swipeproducts.viewmodels.ProductViewModel
import dev.redfox.swipeproducts.viewmodels.ProductViewModelFactory

private lateinit var productSearchAdapter: ProductSearchAdapter
private lateinit var productViewModel: ProductViewModel
class SearchProductFragment : Fragment() {

    private var _binding: FragmentSearchProductBinding? = null
    private val binding
        get() = _binding!!

    private val repository: ProductListRepository by lazy {
        ProductListRepository()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchProductBinding.inflate(inflater, container, false)

        productViewModel = ViewModelProvider(this, ProductViewModelFactory(repository))[ProductViewModel::class.java]

        productViewModel.getProducts()

        val loadProgress = binding.shimmerEffect

        loadProgress.visibility = View.VISIBLE

        productViewModel.response.observe(viewLifecycleOwner, Observer {
            val data: MutableList<productListModelItem> = it.body() as MutableList<productListModelItem>

            loadProgress.visibility = View.GONE

            productSearchAdapter = ProductSearchAdapter(data)
            binding.searchRecyclerView.setHasFixedSize(true)
            binding.searchRecyclerView.adapter = productSearchAdapter
            binding.searchRecyclerView.layoutManager = LinearLayoutManager(context)

            productSearchAdapter.onItemClick = {
                Toast.makeText(context, "One Click", Toast.LENGTH_SHORT).show()
            }

            productSearchAdapter.onItemLongClick = {
                Toast.makeText(context, "Long click", Toast.LENGTH_SHORT).show()
            }
        })

        binding.fabAddProduct.setOnClickListener {
            findNavController().navigate(R.id.action_searchProductFragment_to_addProductFragment)
        }


        return binding.root
    }

}