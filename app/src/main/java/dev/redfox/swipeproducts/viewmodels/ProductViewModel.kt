package dev.redfox.swipeproducts.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.redfox.swipeproducts.models.productAddModel
import dev.redfox.swipeproducts.models.productListModel
import dev.redfox.swipeproducts.models.productListModelItem
import dev.redfox.swipeproducts.networking.ProductListRepository
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productListRepository: ProductListRepository): ViewModel() {

    private val _response = MutableLiveData<Response<List<productListModelItem>>>()
    val response: MutableLiveData<Response<List<productListModelItem>>> get() = _response

    private var _addApiResponse = MutableLiveData<Response<productAddModel>>()
    val addApiResponse: MutableLiveData<Response<productAddModel>> get() = _addApiResponse

    fun getProducts() {
        viewModelScope.launch {
            val dResponse = productListRepository.getProducts()
            _response.value = dResponse
        }
    }

    fun addProducts(productName: RequestBody, productType: RequestBody, price: RequestBody, tax: RequestBody, productImage: MultipartBody.Part){
        viewModelScope.launch {
            val addProductsResponse=productListRepository.addProducts(productName,productType,price,tax,productImage)
            _addApiResponse.value = addProductsResponse
        }
    }
}

@Suppress("UNCHECKED_CAST")
class ProductViewModelFactory(val productListRepository: ProductListRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModel(productListRepository) as T
    }
}