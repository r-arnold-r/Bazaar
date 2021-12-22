package com.example.bazaar.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.R
import com.example.bazaar.api.model.ProductResponse
import de.hdodenhof.circleimageview.CircleImageView

class ProductAdapter(
        private val view: View,
        private val mItemClickListener: ItemClickListener,
        private var products: MutableList<ProductResponse>,
        private val itemViewInt: Int
)
    : RecyclerView.Adapter<ProductAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
                LayoutInflater.from(viewGroup.context).inflate(itemViewInt, viewGroup, false)
        return DataViewHolder(itemView, mItemClickListener)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {

        val productPricePerQuantityStr = products[position].price_per_unit.toString() + " " + products[position].price_type.toString() + "/" + products[position].amount_type.toString()
        holder.productPricePerQuantityTv.text = productPricePerQuantityStr

        holder.productNameTv.text = products[position].title
        holder.profileNameTv.text = products[position].username

        if(products[position].is_active){
            holder.checkIv?.setImageResource(R.drawable.ic_checkmark)
            holder.aiTv?.text = "Active"
            holder.aiTv?.setTextColor(Color.parseColor("#00B5C0"))
        }
        else{
            holder.checkIv?.setImageResource(R.drawable.ic_inactive)
            holder.aiTv?.text = "Inactive"
            holder.aiTv?.setTextColor(Color.parseColor("#9A9A9A"))
        }


        holder.orderNowBtn?.setOnClickListener{

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
        var profileNameTv: TextView = view.findViewById(R.id.profile_name_tv)
        var productNameTv: TextView = view.findViewById(R.id.product_name_tv)
        var orderNowBtn: AppCompatButton? = view.findViewById(R.id.order_now_btn)
        var checkIv: ImageView? = view.findViewById(R.id.check_iv)
        var aiTv: TextView? = view.findViewById(R.id.ai_tv)

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