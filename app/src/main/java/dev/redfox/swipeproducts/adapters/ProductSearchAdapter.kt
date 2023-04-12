package dev.redfox.swipeproducts.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.redfox.swipeproducts.R
import dev.redfox.swipeproducts.databinding.ProductItemLayoutBinding
import dev.redfox.swipeproducts.models.productListModel
import dev.redfox.swipeproducts.models.productListModelItem

class ProductSearchAdapter(
    var productList: MutableList<productListModelItem>
) : RecyclerView.Adapter<ProductSearchAdapter.ProductViewHolder>() {

    var onItemClick: ((productListModelItem) -> Unit)? = null

    class ProductViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
            val productImg: ImageView = itemView.findViewById(R.id.product_image)
            val productName: TextView = itemView.findViewById(R.id.product_name)
            val productPrice: TextView = itemView.findViewById(R.id.product_price)
            val btnDetails: ImageButton = itemView.findViewById(R.id.btn_details)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.data_item_layout, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.productName.text = "Name: " + product.productName
        holder.productPrice.text = "Price: $" + product.price.toString()

        if(product.image == null || product.image!!.isEmpty()) {
            Picasso.get().load(R.drawable.placeholder).placeholder(R.drawable.placeholder)
                .into(holder.productImg)
        } else {
            Picasso.get().load(product.image).placeholder(R.drawable.placeholder)
                .into(holder.productImg)
        }


        holder.btnDetails.setOnClickListener() {
            onItemClick?.invoke(product)
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }


    fun setfilteredList(productList: ArrayList<productListModelItem>){
        this.productList = productList
        notifyDataSetChanged()
    }
}