package dev.redfox.swipeproducts.networking

import dev.redfox.swipeproducts.models.productAddModel
import dev.redfox.swipeproducts.models.productListModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface SwipeApiInterface {

    @GET("/get/")
    suspend fun getProducts(): Response<productListModel>

    @POST("/add/")
    suspend fun addProducts(): Response<productAddModel>
}