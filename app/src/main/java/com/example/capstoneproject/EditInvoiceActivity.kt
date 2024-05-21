package com.example.capstoneproject

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

//
class EditInvoiceActivity : AppCompatActivity() {

    private lateinit var retrofit: Retrofit
    private lateinit var invoice: InvoiceCrud
    private val mainScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)
    private lateinit var selectDateButton: Button
    private lateinit var selectedDate: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editinvoice)
        retrofit = RetrofitClient.create()
        invoice = retrofit.create(InvoiceCrud::class.java)
        val clientEdit = findViewById<EditText>(R.id.clientedit)
        val amountEdit = findViewById<EditText>(R.id.amountedit)
        selectDateButton = findViewById(R.id.selectDateButton)

        selectDateButton.setOnClickListener {
            showDatePickerDialog()
        }
       // val dateString = selectedDate.toString()
      //  val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val descEdit = findViewById<EditText>(R.id.descedit)
        val deletebtn = findViewById<Button>(R.id.deletebtn)
        val savebtn = findViewById<Button>(R.id.savebtn)
        savebtn.setOnClickListener {
            //Perform Invoice  creation
                createInvoiceCall(
                    clientEdit.text.toString(),
                    amountEdit.text.toString().toInt(),
                    descEdit.text.toString())



        }
        deletebtn.setOnClickListener{
            deletebtn.setOnClickListener {
           val alertDialogBuilder = AlertDialog.Builder(this)
           alertDialogBuilder.apply {
               setTitle("Confirm Delete")
               setMessage("Are you sure you want to delete this invoice?")
               setPositiveButton("Delete") { dialog, _ ->
                   // Perform the delete action
//                   deleteInvoicecall(
//                       clientEdit.text.toString(),
//                       amountEdit.text.toString().toInt(),
//                       descEdit.text.toString())
//                   dialog.dismiss()
               }
               setNegativeButton("Cancel") { dialog, _ ->
                 //  dialog.dismiss()
               }

           }
           val alertDialog = alertDialogBuilder.create()
           alertDialog.show()

       }

        }

    }

    //Function To create Invoice

    private fun createInvoiceCall(
        clientEdit: String,
        amountEdit: Int,
        descEdit: String
    ) {

        mainScope.launch {
            val app = Invoice(
                clientName = clientEdit,
                amount = amountEdit,
                description = descEdit
            )
            if (clientEdit.isEmpty() || amountEdit==0) {
                showAlertDialog("Error", "please fill the details")

                return@launch

            }else if (amountEdit<3000){
                showAlertDialog("Error", "amount should be more than 3000")

                return@launch

            }

          else  {

                val response = invoice.createInvoice(app)
                if (response.isSuccessful) {
                    val statuscode = response.body() ?: -1
                    Log.i("@statuccode", "res:$statuscode")
                    val message = "Invoice Created .!!"
                    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(
                        this@EditInvoiceActivity,
                        "Error occurred!!!.Invoice not created ",
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
                selectedDate = "$year-${month + 1}-$dayOfMonth"
                selectDateButton.text = selectedDate

            },
            Calendar.getInstance().get(Calendar.YEAR), // Default year
            Calendar.getInstance().get(Calendar.MONTH), // Default month
            Calendar.getInstance().get(Calendar.DAY_OF_MONTH) // Default day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun showAlertDialog(title: String, message: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
