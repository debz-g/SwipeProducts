package dev.redfox.swipeproducts.models

data class productListModelItem(
    val image: String,
    val price: Double,
    val product_name: String,
    val product_type: String,
    val tax: Double
)