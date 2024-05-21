package com.example.capstoneproject//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import com.example.capstoneproject.R
//import com.example.capstoneproject.connections.Invoice
//
//class AdapterInvoice : RecyclerView.Adapter<AdapterInvoice.InvoiceViewHolder>() {
//
//    private var invoices: List<Invoice> = emptyList()
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InvoiceViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_invoice, parent, false)
//        return InvoiceViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: InvoiceViewHolder, position: Int) {
//        val invoice = invoices[position]
//        holder.bind(invoice)
//    }
//
//    override fun getItemCount(): Int {
//        return invoices.size
//    }
//
//    fun setData(data: List<Invoice>) {
//        invoices = data
//        notifyDataSetChanged()
//    }
//
//    inner class InvoiceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val clientNameTextView: TextView = itemView.findViewById(R.id.clientNameTextView)
//        private val amountTextView: TextView = itemView.findViewById(R.id.amountTextView)
//        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)
//
//        fun bind(invoice: Invoice) {
//            clientNameTextView.text = "Client Name: ${invoice.clientName}"
//            amountTextView.text = "Amount: ${invoice.amount}"
//            descriptionTextView.text = "Description: ${invoice.description}"
//        }
//    }
//}
