package com.example.capstoneproject

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.capstoneproject.connections.InvoiceCrud
import com.example.capstoneproject.connections.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit



class ListOfInvoices : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var invoiceAdapter: InvoiceAdapter
    private lateinit var retrofit: Retrofit
    val mainScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listofinvoices)
        retrofit = RetrofitClient.create()
        val crud = retrofit.create(InvoiceCrud::class.java)
        recyclerView = findViewById<RecyclerView>(R.id.recVw)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addbtn = findViewById<Button>(R.id.addinvoice)
        addbtn.setOnClickListener(View.OnClickListener
        {
            startActivity(Intent(this, EditInvoiceActivity::class.java))
        })



        }
}