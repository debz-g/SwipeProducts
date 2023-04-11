package dev.redfox.swipeproducts.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dev.redfox.swipeproducts.R
import dev.redfox.swipeproducts.databinding.FragmentAddProductBinding
import dev.redfox.swipeproducts.databinding.FragmentSearchProductBinding
import dev.redfox.swipeproducts.networking.ProductListRepository
import dev.redfox.swipeproducts.utils.Constants
import dev.redfox.swipeproducts.utils.Snacker
import dev.redfox.swipeproducts.viewmodels.ProductViewModel
import dev.redfox.swipeproducts.viewmodels.ProductViewModelFactory
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

private lateinit var productViewModel: ProductViewModel

class AddProductFragment : Fragment() {

    private var _binding: FragmentAddProductBinding? = null
    private val binding
        get() = _binding!!

    private val repository: ProductListRepository by lazy {
        ProductListRepository()
    }
    lateinit var imageUri: Uri

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it!!
        binding.ivProduct.setImageURI(it)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentAddProductBinding.inflate(inflater, container, false)

        productViewModel = ViewModelProvider(
            this,
            ProductViewModelFactory(repository)
        )[ProductViewModel::class.java]

        binding.ivProduct.setOnClickListener {
            contract.launch("image/*")
        }

        binding.btnAddProduct.setOnClickListener {
            if (binding.etProductName.text!!.isEmpty() || binding.etProductName.text!!.isEmpty() || binding.etProductName.text!!.isEmpty() || binding.etProductName.text!!.isEmpty()) {
                Snacker(it, "Please enter all the fields").error()
            } else {
                upload()
            }
        }

        return binding.root
    }

    fun imageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg","image/png","image/jpg")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it,Constants.REQUEST_CODE_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Activity.RESULT_OK){
            when(requestCode){
                Constants.REQUEST_CODE_IMAGE -> {
                    imageUri = data?.data!!
                    binding.ivProduct.setImageURI(imageUri)
                }
            }
        }
    }


    fun upload() {
        val filesDir = requireActivity().filesDir
        val file = File(filesDir, "image.png")

        val inputStream = requireActivity().contentResolver.openInputStream(imageUri)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)

        Log.d("debzImage",imageUri.toString())

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val body: MultipartBody.Part =
            MultipartBody.Part.createFormData("files[]", file.name, requestBody)

        productViewModel.addProducts(
            binding.etProductName.text.toString(),
            binding.etProductType.text.toString(),
            binding.etProductPrice.text.toString(),
            binding.etProductTax.text.toString(),
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