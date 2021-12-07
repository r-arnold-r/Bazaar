package com.example.bazaar.adapter

import android.annotation.SuppressLint
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.R
import com.example.bazaar.api.model.ProductResponse
import de.hdodenhof.circleimageview.CircleImageView

class ProductAdapter(
        private val view: View,
        private val mItemClickListener: ItemClickListener,
        private var products: MutableList<ProductResponse>
)
    : RecyclerView.Adapter<ProductAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
                LayoutInflater.from(viewGroup.context).inflate(R.layout.product_item, viewGroup, false)
        return DataViewHolder(itemView, mItemClickListener)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        val productPricePerQuantityStr = products[position].price_per_unit.toString() + " " + products[position].price_type.toString() + "/" + products[position].amount_type.toString()
        holder.productPricePerQuantityTv.text = productPricePerQuantityStr

        holder.productPricePerQuantityTv.movementMethod = ScrollingMovementMethod()
        holder.productNameTv.movementMethod = ScrollingMovementMethod()

        holder.productNameTv.text = products[position].title
        holder.profileNameTv.text = products[position].username

        holder.orderNowBtn.setOnClickListener{

            Log.d("xxx", "Product was clicked for order: " + products[position].toString())
        }

    }

    override fun getItemCount(): Int {
        return products.size
    }

    class DataViewHolder(view: View, itemClickListener: ItemClickListener?) :
            RecyclerView.ViewHolder(view), View.OnClickListener {

        var productImageCiV: CircleImageView = view.findViewById(R.id.product_image_civ)
        var productPricePerQuantityTv: TextView = view.findViewById(R.id.product_price_per_quantity_tv)
        var profileImageCiv: CircleImageView = view.findViewById(R.id.profile_image_civ)
        var profileNameTv: TextView = view.findViewById(R.id.profile_name_tv)
        var productNameTv: TextView = view.findViewById(R.id.product_name_tv)
        var orderNowBtn: AppCompatButton = view.findViewById(R.id.order_now_btn)

        var mItemClickListener: ItemClickListener? = itemClickListener

        override fun onClick(view: View) {
            if (mItemClickListener != null) {
                mItemClickListener!!.onItemClick(adapterPosition)
            }
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    interface ItemClickListener {
        fun onItemClick(position: Int)
    }



}