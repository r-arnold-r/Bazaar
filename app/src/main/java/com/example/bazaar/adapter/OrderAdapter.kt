package com.example.bazaar.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.bazaar.MyApplication
import com.example.bazaar.R
import com.example.bazaar.api.model.AddOrderResponse
import com.example.bazaar.api.model.Orders
import com.example.bazaar.api.model.User
import com.example.bazaar.manager.SharedPreferencesManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import de.hdodenhof.circleimageview.CircleImageView

class OrderAdapter(
        private val view: View,
        private val mItemClickListener: ItemClickListener,
        private var orders: MutableList<Orders>
)
    : RecyclerView.Adapter<OrderAdapter.DataViewHolder>(), Filterable {

    var ordersFilterList = ArrayList<Orders>()

    init {
        ordersFilterList = orders as ArrayList<Orders>
    }

    fun getItemData (position : Int) : Orders
    {
        return ordersFilterList[position]
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
                LayoutInflater.from(viewGroup.context).inflate(R.layout.order_item, viewGroup, false)
        return DataViewHolder(itemView, mItemClickListener)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {


        if(ordersFilterList[position].owner_username ==
                MyApplication.sharedPreferences.getUserValue(SharedPreferencesManager.KEY_USER, User()).username) {

                    holder.profileNameTv.text = ordersFilterList[position].username
        }
        else{
            holder.profileNameTv.text = ordersFilterList[position].owner_username
        }


        holder.productNameTv.text = ordersFilterList[position].title
        holder.priceEditTv.text = ordersFilterList[position].price_per_unit
        holder.amountEditTv.text = ordersFilterList[position].units
        holder.descriptionTv.text = ordersFilterList[position].description

        holder.arrowIv.setOnClickListener{
            if(holder.descriptionTv.visibility == View.GONE){
                holder.descriptionTv.visibility = View.VISIBLE
                holder.arrowIv.setImageResource(R.drawable.ic_arrow_up_order_item)
            }
            else{
                holder.descriptionTv.visibility = View.GONE
                holder.arrowIv.setImageResource(R.drawable.ic_arrow_down_order_item)
            }
        }


    }

    override fun getItemCount(): Int {
        return ordersFilterList.size
    }

    class DataViewHolder(view: View, itemClickListener: ItemClickListener?) :
            RecyclerView.ViewHolder(view), View.OnClickListener {

        var productImageCiV: CircleImageView = view.findViewById(R.id.product_image_civ)
        var profileNameTv: TextView = view.findViewById(R.id.profile_name_tv)
        var profileImageCiV: CircleImageView = view.findViewById(R.id.profile_image_civ)
        var priceAmountSp: Spinner = view.findViewById(R.id.price_amount_sp)
        var productNameTv: TextView = view.findViewById(R.id.product_name_tv)
        var amountTv: TextView = view.findViewById(R.id.amount_tv)
        var amountEditTv: TextView = view.findViewById(R.id.amount_edit_tv)
        var priceTv: TextView = view.findViewById(R.id.price_tv)
        var priceEditTv: TextView = view.findViewById(R.id.price_edit_tv)
        var arrowIv: ImageView = view.findViewById(R.id.arrow_iv)
        var ignoreFab: FloatingActionButton = view.findViewById(R.id.ignore_fab)
        var acceptFab: FloatingActionButton = view.findViewById(R.id.accept_fab)
        var descriptionTv: TextView = view.findViewById(R.id.description_tv)
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


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                ordersFilterList = if (charSearch == "") {
                    orders as ArrayList<Orders>
                } else {
                    val resultList = ArrayList<Orders>()
                    for (row in orders) {
                        if (row.title.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = ordersFilterList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                ordersFilterList = results?.values as ArrayList<Orders>
                notifyDataSetChanged()
            }
        }
    }

}