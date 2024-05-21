package com.example.capstoneproject

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.capstoneproject.connections.Invoice
import com.example.capstoneproject.connections.InvoiceCrud
import com.example.capstoneproject.connections.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.util.Calendar

class AddInvoiceActivity : AppCompatActivity() {
    private lateinit var selectDateButton: Button

    private lateinit var selectedDate: String
    //private lateinit var formatDate: Date
    private lateinit var retrofit: Retrofit
    private lateinit var invoice: InvoiceCrud
    private val mainScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addinvoice)
        retrofit = RetrofitClient.create()
        invoice = retrofit.create(InvoiceCrud::class.java)

        selectDateButton = findViewById(R.id.selectDateButton)

        selectDateButton.setOnClickListener {
            showDatePickerDialog()

        }




        val createbtn = findViewById<Button>(R.id.createbtn)

        createbtn.setOnClickListener {

            createInvoiceCall()


        }
    }

    private fun createInvoiceCall() {


        mainScope.launch {

            val clientName = findViewById<EditText>(R.id.clientText).toString()
            val amount = findViewById<EditText>(R.id.amountText).toString().toInt()
            //  val date = findViewById<EditText>(R.id.dateText)
            val datetext = findViewById<Button?>(R.id.selectDateButton).text.toString()
            val description = findViewById<EditText>(R.id.descText).toString()
           // val dateprint = selectDateButton.text.toString()
            if (clientName.isEmpty() || amount == 0) {
                Toast.makeText(
                    this@AddInvoiceActivity,
                    "Please fill in all fields!",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (amount.toDouble() < 3000) {
                Toast.makeText(
                    this@AddInvoiceActivity,
                    "Amount must be greater than 3000",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

//                   val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())  // Format for date only
//                val parsedDate = dateFormat.parse(datetext)
//                val formattedDate = dateFormat.format(parsedDate!!)

//                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())  // Format for date only
//                val parsedDate = dateFormat.parse(datetext)
//             val formatDate = parsedDate?.let { dateFormat.format(it) }
                Toast.makeText(
                    this@AddInvoiceActivity,
                    "date value $datetext",
                    Toast.LENGTH_SHORT
                ).show()

                val app =
                    Invoice(
                        clientName = clientName,
                        amount = amount,
                        date = datetext,
                        description = description
                    )
                Toast.makeText(applicationContext, "after invoice declartion function", Toast.LENGTH_SHORT).show()


                val response = invoice.createInvoice(app)


                if (response.isSuccessful) {
                    val statusCode = response.body() ?: -1
                    Log.i("@Response", "res:$statusCode")
                    val message = "Invoice Created .!"
                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this@AddInvoiceActivity,
                        "Error occurred!!!.Invoice not created",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }

    }


    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val selectedDate = "$year-${month + 1}-$dayOfMonth"
                selectDateButton.text = selectedDate // Update the Button text with selected date
                Log.d("SelectedDate", selectedDate)
            },
            Calendar.getInstance().get(Calendar.YEAR), // Default year
            Calendar.getInstance().get(Calendar.MONTH), // Default month
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) // Default day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()

        datePickerDialog.show()


    }
}





