package dev.redfox.swipeproducts.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.redfox.swipeproducts.R
import dev.redfox.swipeproducts.databinding.ProductItemLayoutBinding
import dev.redfox.swipeproducts.models.productListModel
import dev.redfox.swipeproducts.models.productListModelItem

class ProductSearchAdapter(
    val productList: MutableList<productListModelItem>
) : RecyclerView.Adapter<ProductSearchAdapter.ProductViewHolder>() {

    var onItemClick: ((productListModelItem) -> Unit)? = null
    var onItemLongClick: ((productListModelItem) -> Unit)? = null

    class ProductViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
            val productImg: ImageView = itemView.findViewById(R.id.productImage)
            val productName: TextView = itemView.findViewById(R.id.productName)
            val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.product_item_layout, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.productName.text = product.productName
        holder.productPrice.text = product.price.toString()

        if(product.image == null || product.image!!.isEmpty()) {
            Picasso.get().load(R.drawable.placeholde).placeholder(R.drawable.placeholde)
                .into(holder.productImg)
        } else {
            Picasso.get().load(product.image).placeholder(R.drawable.placeholde)
                .into(holder.productImg)
        }






        holder.itemView.setOnClickListener() {
            onItemClick?.invoke(product)
        }

        holder.itemView.setOnLongClickListener {
            onItemLongClick?.invoke(product)
            true
        }

    }

    override fun getItemCount(): Int {
        return productList.size
    }
}