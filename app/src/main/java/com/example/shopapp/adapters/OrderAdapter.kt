package com.example.shopapp.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shopapp.R
import com.example.shopapp.activities.OrderPDFActivity
import com.example.shopapp.activities.OrdersActivity
import com.example.shopapp.activities.ProductDetailActivity
import com.example.shopapp.databinding.ItemOrderLayoutBinding
import com.example.shopapp.method.functions.currencyFormat
import com.example.shopapp.method.getInformation
import com.example.shopapp.method.getOrderById
import com.example.shopapp.method.getProductById
import com.example.shopapp.model.OrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class OrderAdapter(var context: Context, val list: ArrayList<OrderModel>)
    : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    inner class OrderViewHolder(val binding: ItemOrderLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        return OrderViewHolder(
            ItemOrderLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        var product: MutableList<String> = mutableListOf()
        var i = 0
        for (data in list[position].productId!!) {
            getProductById(data) {
                product.add(it.productName!!)
                val price = it.productSp?.toInt()?.let { currencyFormat(it) }
                product.add(price!!)
                product.add(list[position].listItemTotal?.get(i)!!.toString())
                i++
            }
        }
        val arrayNameAdapter = ArrayAdapter(context, R.layout.item_view_layout, product)
        holder.binding.spinner2.adapter = arrayNameAdapter
        holder.binding.textView7.text = list[position].paymentMethod
        holder.binding.textView12.text = list[position].status
        holder.binding.textview9.text = "ID: " + list[position].orderId
        val price = list[position].totalCost?.toInt()?.let { currencyFormat(it) }
        holder.binding.textView17.text = price

        holder.binding.btnCancel.setOnClickListener {
            updateStatus("Canceled", list[position].orderId!!)
            Toast.makeText(context, "Canceled successful", Toast.LENGTH_SHORT).show()
            holder.binding.btnReceived.visibility = View.GONE
            holder.binding.btnCancel.isEnabled = false
            val refresh = Intent(context, OrdersActivity::class.java)
            context.startActivity(refresh)
        }

        holder.binding.btnReceived.setOnClickListener {
            updateStatus("Received", list[position].orderId!!)
            Toast.makeText(context, "Received successful", Toast.LENGTH_SHORT).show()
            holder.binding.btnCancel.visibility = View.GONE
            holder.binding.btnReceived.isEnabled = false
            val refresh = Intent(context, OrdersActivity::class.java)
            context.startActivity(refresh)

        }

        //getStatus
        getOrderById(list[position].orderId!!) {
            if(it.status=="Ordered"){
                holder.binding.btnReceived.isEnabled = false

            }else if (it.status=="Canceled"){
                holder.binding.btnReceived.visibility = View.GONE
                holder.binding.btnCancel.isEnabled = false
            }else if (it.status=="Received"){
                holder.binding.btnCancel.visibility = View.GONE
                holder.binding.btnReceived.isEnabled = false
            }

        }

        holder.binding.btnPDF.setOnClickListener {
            val intent = Intent(context, OrderPDFActivity::class.java)
            intent.putExtra("orderId", list[position].orderId)
            intent.putExtra("userPhoneNumber",list[position].userId )
            context.startActivity(intent)

        }

    }

    fun updateStatus(str : String, doc: String){

        val data = hashMapOf<String, Any>()
        data["status"] = str
//        updateStatusOrder(doc,str){
//            if (it){
//                Toast.makeText(context, "Change Status Successful", Toast.LENGTH_SHORT).show()
//            }else{
//                Toast.makeText(context, "Change Status Failed", Toast.LENGTH_SHORT).show()
//            }
//        }
        Firebase.firestore.collection("allOrders")
            .document(doc).update(data).addOnSuccessListener {
                Toast.makeText(context, "Change Status Successful", Toast.LENGTH_SHORT).show()
            }
    }

}

