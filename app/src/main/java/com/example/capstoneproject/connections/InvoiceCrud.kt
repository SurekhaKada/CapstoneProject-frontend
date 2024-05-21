package com.example.capstoneproject.connections


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface InvoiceCrud {

    @GET("/invoices")
    suspend fun getAllInvoices(): Response<Invoice>
    @POST("/invoices")
    suspend fun createInvoice(@Body app: Invoice): Response<Invoice>
    @DELETE("/{id}")
    suspend fun deleteInvoice(@Body app: Invoice): Response<Invoice>

}