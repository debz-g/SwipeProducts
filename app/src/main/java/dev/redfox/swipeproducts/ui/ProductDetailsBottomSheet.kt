package dev.redfox.swipeproducts.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dev.redfox.swipeproducts.R
import dev.redfox.swipeproducts.databinding.ProductDetailsBottomSheetBinding
import dev.redfox.swipeproducts.models.productListModelItem

@AndroidEntryPoint
class ProductDetailsBottomSheet(val productDetails: productListModelItem): BottomSheetDialogFragment() {

    companion object {
        const val TAG ="ProductBottomSheetDialogFragment"
    }


    lateinit var binding: ProductDetailsBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflater = LayoutInflater.from(requireContext())
        binding = ProductDetailsBottomSheetBinding.inflate(inflater)

        binding.apply {

            if(productDetails.image == null || productDetails.image!!.isEmpty()) {
                Picasso.get().load(R.drawable.placeholder).placeholder(R.drawable.placeholder)
                    .into(ivProductPic)
            } else {
                Picasso.get().load(productDetails.image).placeholder(R.drawable.placeholder)
                    .into(ivProductPic)
            }

            tvProductName.text = "Product Name: " + productDetails.productName
            tvProductType.text = productDetails.productType
            tvProductPrice.text = "Price:$ " + productDetails.price.toString()
            tvProductTax.text = "Tax:$ " + productDetails.tax.toString()

        }
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}