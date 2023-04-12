package dev.redfox.swipeproducts.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import dev.redfox.swipeproducts.R
import dev.redfox.swipeproducts.databinding.FragmentAddProductBinding
import dev.redfox.swipeproducts.networking.ProductListRepository
import dev.redfox.swipeproducts.utils.Snacker
import dev.redfox.swipeproducts.viewmodels.ProductViewModel
import dev.redfox.swipeproducts.viewmodels.ProductViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding
        get() = _binding!!

//    private val repository: ProductListRepository by lazy {
//        ProductListRepository()
//    }
    var imageUri: Uri? = null

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it!!
        binding.ivProduct.setImageURI(it)
    }




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        _binding = FragmentAddProductBinding.inflate(inflater, container, false)

        var spinnerProductType: String = "Accessories"
        val productTypeList = resources.getStringArray(R.array.productTypes)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.drop_down_item, productTypeList)
        binding.productTypeList.setAdapter(arrayAdapter)
        binding.productTypeList.setOnItemClickListener { adapterView, view, i, l ->
            spinnerProductType = adapterView.getItemAtPosition(i).toString()
        }

//        productViewModel = ViewModelProvider(
//            this,
//            ProductViewModelFactory(repository)
//        )[ProductViewModel::class.java]



        binding.ivProduct.setOnClickListener {
            contract.launch("image/*")
        }

        binding.btnAddProduct.setOnClickListener {
            if (binding.etProductName.text!!.isEmpty() || binding.etProductName.text!!.isEmpty() || binding.etProductName.text!!.isEmpty() || binding.etProductName.text!!.isEmpty()) {
                Snacker(it, "Please enter all the fields").error()
            } else {
                upload(
                    binding.etProductName.text.toString(),
                    spinnerProductType,
                    binding.etProductPrice.text.toString(),
                    binding.etProductTax.text.toString()
                )
            }
        }

        return binding.root
    }


    fun upload(productName: String, productType: String, price: String, tax: String) {
        val filesDir = requireActivity().filesDir
        val file = File(filesDir, "image.png")
        val body: MultipartBody.Part?
        val productViewModel: ProductViewModel by viewModels()

        if (imageUri == null) {
            val reqBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            body =
                MultipartBody.Part.createFormData("invalid", file.name, reqBody)
        } else {

            val inputStream = requireActivity().contentResolver.openInputStream(imageUri!!)
            val outputStream = FileOutputStream(file)
            inputStream!!.copyTo(outputStream)

            val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            body =
                MultipartBody.Part.createFormData("files[]", file.name, requestBody)
        }


        val productNameBody: RequestBody =
            productName.toRequestBody("text/plain".toMediaTypeOrNull())
        val productTypeBody: RequestBody =
            productType.toRequestBody("text/plain".toMediaTypeOrNull())
        val priceBody: RequestBody = price.toRequestBody("text/plain".toMediaTypeOrNull())
        val taxBody: RequestBody = tax.toRequestBody("text/plain".toMediaTypeOrNull())

        productViewModel.addProducts(
            productNameBody,
            productTypeBody,
            priceBody,
            taxBody,
            body
        )

        productViewModel.addApiResponse.observe(viewLifecycleOwner, Observer {
            if (it.body()!!.success == true) {
                Toast.makeText(requireContext(), "Product Added Succefully", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

}