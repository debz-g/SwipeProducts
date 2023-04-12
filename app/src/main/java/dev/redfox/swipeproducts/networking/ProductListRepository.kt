package dev.redfox.swipeproducts.networking

import dev.redfox.swipeproducts.models.productAddModel
import dev.redfox.swipeproducts.models.productListModel
import dev.redfox.swipeproducts.models.productListModelItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class ProductListRepository @Inject constructor(private val swipeApiInterface: SwipeApiInterface) {

    suspend fun getProducts(): Response<List<productListModelItem>> {
        return swipeApiInterface.getProducts()
    }

    suspend fun addProducts(productName: RequestBody, productType: RequestBody, price: RequestBody, tax: RequestBody, productImage: MultipartBody.Part): Response<productAddModel>{
        return swipeApiInterface.addProducts(productName,productType,price,tax,productImage)
    }
}