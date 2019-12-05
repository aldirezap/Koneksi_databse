@file:Suppress("DEPRECATION")
package com.example.konekdatabase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    var arrayList = ArrayList<Fakultas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Data Fakultas"
        recycle_view.setHasFixedSize(true)
        recycle_view.layoutManager = LinearLayoutManager(this)

        val context = this
        floatingActionButton.setOnClickListener {
            val intent = Intent(context,ManageFakultasActivity::class.java)
            startActivity(intent)
        }
        loadAllFakultas()
    }


    override fun onResume() {
        super.onResume()
        loadAllFakultas()
    }

    private fun loadAllFakultas() {
        val loading = ProgressDialog(this)
        loading.setMessage("Memuat data ...")
        loading.show()

        AndroidNetworking.get(ApiEndPoint.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()
                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        loading.dismiss()
                        Toast.makeText(
                            applicationContext,
                            "Fakultas data is empty, Add the Data first",
                            Toast.LENGTH_SHORT
                        ).show()

                        for (i in 0 until jsonArray?.length()!!) {
                            val jsonObject = jsonArray?.optJSONObject(i)
                            arrayList.add(
                                Fakultas(
                                    jsonObject.getString("id_fakultas"),
                                    jsonObject.getString("kode_fakultas"),
                                    jsonObject.getString("nama_fakultas")
                                )
                            )

                            if (jsonArray?.length() - 1 == i) {
                                loading.dismiss()
                                val adapter = RVAdapterFakultas(applicationContext, arrayList)
                                adapter.notifyDataSetChanged()
                                recycle_view.adapter = adapter
                            }
                        }
                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    Log.d("ONERROR",anError?.errorDetail?.toString())
                    Toast.makeText(applicationContext,"Connection Failure", Toast.LENGTH_SHORT).show()
                }
            })
    }
}



