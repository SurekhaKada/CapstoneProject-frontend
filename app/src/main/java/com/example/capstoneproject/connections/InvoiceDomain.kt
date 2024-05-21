package com.example.capstoneproject.connections


//data class Invoice(
////    var id: Long,
//    var clientName: String,
//    var amount:Int,
//    var date: Date,
//    var description:String
//)

data class Invoice(
    var invoiceId: Int = 0,
    //var userId: Int = 0,
    var clientName: String = "",
    var amount: Int = 0,
    var date: String =" ",
    var description: String = "",

)