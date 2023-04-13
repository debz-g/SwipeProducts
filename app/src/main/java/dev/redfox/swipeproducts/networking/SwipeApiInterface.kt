package dev.redfox.swipeproducts.networking

import dev.redfox.swipeproducts.models.productAddModel
import dev.redfox.swipeproducts.models.productListModelItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    suspend fun addProductsWithImage(
        @Part("product_name") productName: RequestBody,
        @Part("product_type") productType: RequestBody,
        @Part("price") price: RequestBody,
        @Part("tax") tax: RequestBody,
        @Part productImage: MultipartBody.Part
    ): Response<productAddModel>

    @Multipart
    @POST("/api/public/add")
    suspend fun addProductsWithoutImage(
        @Part("product_name") productName: RequestBody,
        @Part("product_type") productType: RequestBody,
        @Part("price") price: RequestBody,
        @Part("tax") tax: RequestBody
    ): Response<productAddModel>
}