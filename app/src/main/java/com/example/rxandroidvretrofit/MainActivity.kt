package com.example.rxandroidvretrofit

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rxandroidvretrofit.network.UserRetrofit
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    private var mListUser: List<User> = listOf()
    private var mDisposable: Disposable? = null
    private lateinit var userAdapter: UserAdapter
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userAdapter= UserAdapter()
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("loading...")
        val rclUser = findViewById<RecyclerView>(R.id.rclUser)
        val callApi = findViewById<Button>(R.id.callApi)
        rclUser.layoutManager=LinearLayoutManager(this)
        rclUser.adapter=userAdapter
        callApi.setOnClickListener {
            progressDialog.show()
            UserRetrofit.api.getUser().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<User>> {
                    override fun onSubscribe(d: Disposable) {
                        Log.d("AAAAAAAA", "onSubscribe")
                        mDisposable = d
                    }

                    override fun onNext(t: List<User>) {
                        mListUser = t
                    }

                    override fun onError(e: Throwable) {
                        Log.d("AAAAAAAA", "onSubscribe")
                    }

                    override fun onComplete() {
                        progressDialog.dismiss()
                        userAdapter.setListUser(mListUser)
                    }
                })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mDisposable != null) {
            mDisposable!!.dispose()
        }
    }
}