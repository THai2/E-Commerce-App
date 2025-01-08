package com.example.shopapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.shopapp.MainActivity
import com.example.shopapp.adapters.CartAdapter
import com.example.shopapp.databinding.ActivityCheckoutBinding
import com.example.shopapp.method.addOrder
import com.example.shopapp.method.functions.currencyFormat
import com.example.shopapp.method.getProductById
import com.example.shopapp.model.CreateOrder
import com.example.shopapp.model.OrderModel
import com.example.shopapp.roomdb.AppDatabase
import com.example.shopapp.roomdb.ProductModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import vn.zalopay.sdk.Environment
import vn.zalopay.sdk.ZaloPayError
import vn.zalopay.sdk.ZaloPaySDK
import vn.zalopay.sdk.listeners.PayOrderListener


class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding
    private lateinit var list: ArrayList<String>
    private lateinit var listProductName: ArrayList<String>
    private lateinit var listProductItem: ArrayList<String>
    private lateinit var listProductSp: ArrayList<String>
    private lateinit var listProductId: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)

        //Zalo
        val policy = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX)

        //list luu tat ca id cua product
        list = ArrayList()
        listProductName = ArrayList()
        listProductItem = ArrayList()
        listProductSp = ArrayList()
        listProductId = ArrayList()

        //bien luu tinh tien

        var total = 0
        var totalItem = 0
        var totalCurrent = 0
        setContentView(binding.root)


        val dao = AppDatabase.getInstance(this@CheckoutActivity).productDao()
        dao.getAllProduct().observe(this){
            binding.cartRecyclerView.adapter = CartAdapter(this, it)

            if(it.size == 0){
                binding.button3.isEnabled = false
                binding.button4.isEnabled = false
            }else{
                binding.button3.isEnabled = true
                binding.button4.isEnabled = true
            }

            list.clear()
            for (data in it){
                list.add(data.productId)
                listProductItem.add(data.productCount!!.toString())
                listProductId.add(data.productId)
                listProductName.add(data.productName!!)
                listProductSp.add(data.productSp!!.toString())
            }

            //get total cost
            for (item in it!!){
                totalItem+= item.productCount!!.toInt()
                totalCurrent = (item.productSp!!.toInt())*item.productCount!!
                total += totalCurrent
            }
//          binding.textView1.text = "Total item in cart is: ${data.size}"
            binding.textView14.text = "${totalItem}"
            val price = total?.toInt()?.let { currencyFormat(it) }
            binding.textView13.text = price

        }

        binding.button3.setOnClickListener {

            requestZaloPay(total)
        }

        binding.button4.setOnClickListener {
            uploadData("COD","Cash On Delivery", total)
        }

    }

//    private fun getTotalCost(data: List<ProductModel>?) {
//        var total = 0
//        var totalItem = 0
//        var totalCurrent = 0
//        for (item in data!!){
//            totalItem+= item.productCount!!.toInt()
//            totalCurrent = (item.productSp!!.toInt())*totalItem
//            total += totalCurrent
//        }
////        binding.textView1.text = "Total item in cart is: ${data.size}"
//        binding.textView14.text = "Total item in cart is: ${totalItem}"
//        binding.textView13.text = "Total cost: $total"
//    }

    private fun requestZaloPay(total: Int) {

        //Create Order
        val orderApi = CreateOrder()
        //Tien hanh thanh toan
        try {

            val data: JSONObject = orderApi.createOrder(total.toString())
            val code = data.getString("return_code");
            if (code.equals("1")) {
                val token: String = data.getString("zp_trans_token")
                ZaloPaySDK.getInstance().payOrder(this@CheckoutActivity,token,"demozpdk://app", object:
                    PayOrderListener {
                    override fun onPaymentSucceeded(transactionId: String?, transToken: String?, appTransID: String?) {
                        Toast.makeText(this@CheckoutActivity, "Payment success", Toast.LENGTH_SHORT).show()
                        val paymentMethod = "ZaloPay"
                        val id = transactionId

                        uploadData(paymentMethod, id, total)
                    }

                    override fun onPaymentCanceled(zpTransToken: String?, appTransID: String?) {
                        Toast.makeText(this@CheckoutActivity, "Payment fail", Toast.LENGTH_SHORT).show()
                    }

                    override fun onPaymentError(zaloPayError: ZaloPayError?, zpTransToken: String?, appTransID: String?) {
                        Toast.makeText(this@CheckoutActivity, "Payment fail", Toast.LENGTH_SHORT).show()
                    }

                })

            }
        }catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun uploadData(paymentMethod: String, id: String?, total: Int,) {

        if (list.size > 0) {
            for (currentId in list){
            val dao = AppDatabase.getInstance(this).productDao()

            getProductById(currentId){
//                listProductId.add(it.productId!!)
//                listProductName.add(it.productName!!)
//                listProductSp.add(it.productSp!!)

                lifecycleScope.launch(Dispatchers.IO) {
                        dao.deleteProduct(ProductModel(currentId))
                    }
            }
            }
            saveData(listProductName,
                listProductSp,
                listProductId,paymentMethod,id,total,listProductItem)

        }else{

            }
//        for (currentId in list){
//            val dao = AppDatabase.getInstance(this).productDao()
//
//            getProductById(currentId){
//                listProductId.add(it.productId!!)
//                listProductName.add(it.productName!!)
//                listProductSp.add(it.productSp!!)
//
//                lifecycleScope.launch(Dispatchers.IO) {
//                        dao.deleteProduct(ProductModel(currentId))
//                    }
//            }

//            Firebase.firestore.collection("products").document(currentId)
//                .get().addOnSuccessListener {
//
//                    listProductId.add(it.getString("productId")!!)
//                    listProductName.add(it.getString("productName")!!)
//                    listProductSp.add(it.getString("productSp")!!)
//
//                    lifecycleScope.launch(Dispatchers.IO) {
//                        dao.deleteProduct(ProductModel(currentId))
//                    }
//                }


    }


    private fun saveData(
        listProductName: ArrayList<String>,
        listProductSp: ArrayList<String>,
        listProductId: ArrayList<String>,
        paymentMethod: String,
        id: String?,
        total: Int,
        listProductItem: ArrayList<String>
    ) {
        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
//        val data = HashMap<String, Any>()
//        data["name"] = listProductName
//        data["price"] = listProductSp
//        data["id"] = listProductId
//        data["status"] = "Ordered"
//        data["userId"] = preferences.getString("userPhoneNumber","")!!
//        data["paymentMethod"] = paymentMethod!!
//        data["idZaloPayMethod"] = id!!
//        data["total"] = total
//        data["totalItem"] = listProductItem

        val firestore = Firebase.firestore.collection("allOrders")
        val key = firestore.document().id
//        data["orderId"] = key

        val newOrder = OrderModel (listProductName, key, preferences.getString("userPhoneNumber","")!!,
        "Ordered", total.toString(),listProductId,listProductSp, paymentMethod, id , listProductItem )

//        firestore.document(key).set(data).addOnSuccessListener {
//            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this,MainActivity::class.java))
//            finish()
//        }.addOnFailureListener {
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
//        }

        addOrder(newOrder){
            if (it){
                Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
//        firestore.document(key).set(newOrder).addOnSuccessListener {
//            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this,MainActivity::class.java))
//            finish()
//        }.addOnFailureListener {
//            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
//        }


    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        ZaloPaySDK.getInstance().onResult(intent)
    }


}