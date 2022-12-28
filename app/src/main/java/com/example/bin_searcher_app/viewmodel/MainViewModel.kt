package com.example.bin_searcher_app.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bin_searcher_app.model.DataBase
import com.example.bin_searcher_app.model.DataModel
import com.example.bin_searcher_app.network.BinApi
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver

class MainViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    val liveData = MutableLiveData<DataModel>()
    val livedataStatus = MutableLiveData<String>()
    val livedataDB = MutableLiveData<ArrayList<String>>()

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun searchCardInfo(binApi: BinApi?, bin: String){
        binApi?.let {
            compositeDisposable.add(binApi.getCardInfo(bin)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<DataModel>(){
                    override fun onSuccess(t: DataModel) {
                        liveData.value = t
                        livedataStatus.value = "onSuccess"
                    }
                    override fun onError(e: Throwable) {
                        livedataStatus.value = "${e.message}"
                    }
                })
            )
        }
    }

    fun readDB(context: Context){
        DataBase(context).let {
            compositeDisposable.add(DataBase(context).rvList()
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ArrayList<String>>(){
                    override fun onSuccess(t: ArrayList<String>) {
                        livedataDB.value = t
                    }

                    override fun onError(e: Throwable) {
                        livedataStatus.value = "${e.message}"
                    }
                })
            )
        }
    }

    fun addDB(context: Context, hint: String){
        DataBase(context).let {
            compositeDisposable.add(DataBase(context).addAuto(hint)
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ArrayList<String>>(){
                    override fun onSuccess(t: ArrayList<String>) {
                    }

                    override fun onError(e: Throwable) {
                        livedataStatus.value = "${e.message}"
                    }
                })
            )
        }
    }

    fun deleteDB(context: Context){
        DataBase(context).let {
            compositeDisposable.add(DataBase(context).delete()
                .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<String>() {
                    override fun onSuccess(t: String) {
                    }

                    override fun onError(e: Throwable) {
                        livedataStatus.value = "${e.message}"
                    }
                })
            )
        }
    }

}


