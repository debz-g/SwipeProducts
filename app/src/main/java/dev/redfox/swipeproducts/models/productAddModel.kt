package dev.redfox.swipeproducts.models

data class productAddModel(
    val message: String,
    val product_details: ProductDetails,
    val product_id: Int,
    val success: Boolean
)