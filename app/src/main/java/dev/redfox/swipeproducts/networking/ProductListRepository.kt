package dev.redfox.swipeproducts.networking

import dev.redfox.swipeproducts.models.productAddModel
import dev.redfox.swipeproducts.models.productListModel
import dev.redfox.swipeproducts.models.productListModelItem
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit

class ProductListRepository() {

    suspend fun getProducts(): Response<List<productListModelItem>> {
        return ProdctListRetrofitInstance.api.getProducts()
    }

    suspend fun addProducts(productName: String, productType: String, price: String, tax: String, productImage: MultipartBody.Part): Response<productAddModel>{
        return ProdctListRetrofitInstance.api.addProducts(productName,productType,price,tax,productImage)
    }
}