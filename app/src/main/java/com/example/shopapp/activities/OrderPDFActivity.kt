package com.example.shopapp.activities

import android.Manifest
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.shopapp.R
import com.example.shopapp.databinding.ActivityOrderPdfactivityBinding
import com.example.shopapp.method.functions.currencyFormat
import com.example.shopapp.method.getOrderById
import com.example.shopapp.method.getProductById
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


const val REQUEST_CODE = 1232
class OrderPDFActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOrderPdfactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOrderPdfactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        askPermissions()

        getOrderDetail(intent.getStringExtra("orderId"))

        binding.btnPDF.setOnClickListener { convertXmlToPdf() }

        binding.textView5.text = "Total Cost :"
        binding.textView16.text = "Payment method:"

    }

    private fun askPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CODE
        )
    }

    fun convertXmlToPdf() {
        // Inflate the XML layout file
        val view: View = LayoutInflater.from(this).inflate(R.layout.activity_order_pdfactivity,binding.root)
        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.display!!.getRealMetrics(displayMetrics)
        } else this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        view.measure(
            View.MeasureSpec.makeMeasureSpec(displayMetrics.widthPixels, View.MeasureSpec.EXACTLY),
            View.MeasureSpec.makeMeasureSpec(displayMetrics.heightPixels, View.MeasureSpec.EXACTLY)
        )
        Log.d("mylog", "Width Now " + view.measuredWidth)
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels)
        // Create a new PdfDocument instance
        val document = PdfDocument()

        // Obtain the width and height of the view
        val viewWidth = view.measuredWidth
        val viewHeight = view.measuredHeight
        // Create a PageInfo object specifying the page attributes
        val pageInfo = PageInfo.Builder(viewWidth, viewHeight, 1).create()

        // Start a new page
        val page = document.startPage(pageInfo)

        // Get the Canvas object to draw on the page
        val canvas: Canvas = page.canvas

        // Create a Paint object for styling the view
        val paint = Paint()
        paint.setColor(Color.WHITE)

        // Draw the view on the canvas
        view.draw(canvas)

        // Finish the page
        document.finishPage(page)

        // Specify the path and filename of the output PDF file
        val downloadsDir: File =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        val fileName = intent.getStringExtra("orderId")+".pdf"
        val filePath = File(downloadsDir, fileName)
        try {
            // Save the document to a file
            val fos = FileOutputStream(filePath)
            document.writeTo(fos)
            document.close()
            fos.close()
            // PDF conversion successful
            Toast.makeText(this, "XML to PDF Conversion Successful", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            // Error occurred while converting to PDF
        }
    }

    private fun getOrderDetail(orderId: String?) {
        getOrderById(orderId!!){orderModel->
            var productDetail: MutableList<String> = mutableListOf()
            var i = 0
            for (data in orderModel.productId!!) {
                getProductById(data) {
                    productDetail.add(it.productName!!)
                    val price = it.productSp?.toInt()?.let { currencyFormat(it) }
                    productDetail.add(price!!)
                    productDetail.add(orderModel.listItemTotal?.get(i)!!.toString())
                    i++
                }
            }

            val arrayAdapter = ArrayAdapter(this ,R.layout.item_view_layout, productDetail)
            binding.listView.adapter = arrayAdapter

            val price = orderModel.totalCost?.toInt()?.let { currencyFormat(it) }
            binding.textView7.text = price
            binding.textView17.text = orderModel.paymentMethod
            binding.textview9.text = "ID: " + orderModel.orderId
            binding.textView19.text = "Customer: (+84)" + orderModel.userId

            if (orderModel.productId.isEmpty() ){
                binding.btnPDF.visibility = View.GONE
            }else {
                binding.btnPDF.visibility = View.VISIBLE
            }
        }
    }
}