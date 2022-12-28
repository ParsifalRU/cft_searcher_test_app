package com.example.bin_searcher_app.network

import com.example.bin_searcher_app.model.DataModel
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface BinApi{
    @GET("/{bin}")
    fun getCardInfo(@Path("bin") bin: String): Single<DataModel>
}


