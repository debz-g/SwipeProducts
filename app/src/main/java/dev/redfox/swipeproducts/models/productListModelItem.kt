package dev.redfox.swipeproducts.models

import com.google.gson.annotations.SerializedName

data class productListModelItem(
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("price")
    var price: Float? = null,
    @SerializedName("product_name" )
    var productName : String? = null,
    @SerializedName("product_type" )
    var productType : String? = null,
    @SerializedName("tax")
    var tax: Float? = null
)