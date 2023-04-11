package dev.redfox.swipeproducts.networking

import dev.redfox.swipeproducts.models.productAddModel
import dev.redfox.swipeproducts.models.productListModelItem
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SwipeApiInterface {
    @GET("/api/public/get")
    suspend fun getProducts(): Response<List<productListModelItem>>

    @Multipart
    @POST("/api/public/add")
    suspend fun addProducts(
        @Part("product_name") productName: String,
        @Part("product_type") productType: String,
        @Part("price") price: String,
        @Part("tax") tax: String,
        @Part productImage: MultipartBody.Part
    ): Response<productAddModel>

}