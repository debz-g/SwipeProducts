package dev.redfox.swipeproducts.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.redfox.swipeproducts.databinding.ProductItemLayoutBinding
import dev.redfox.swipeproducts.models.productListModelItem

class ProductSearchAdapter(
    val productList: MutableList<productListModelItem>
): RecyclerView.Adapter<ProductSearchAdapter.ProductViewHolder>() {

    var onItemClick : ((productListModelItem) -> Unit)? = null
    var onItemLongClick : ((productListModelItem) -> Unit)? = null

    class ProductViewHolder(val binding: ProductItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ProductItemLayoutBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.binding.productName.text = product.product_name
        holder.binding.productPrice.text = product.price.toString()

        Picasso.get().load(product.image).into(holder.binding.productImage)

        holder.itemView.setOnClickListener(){
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