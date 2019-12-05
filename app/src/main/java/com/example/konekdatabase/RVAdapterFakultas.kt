package com.example.konekdatabase

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fakultas_list.view.*

class RVAdapterFakultas (private  val context: Context, private val arrayList: ArrayList<Fakultas>) :
RecyclerView.Adapter<RVAdapterFakultas.Holder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.fakultas_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.lbl_idfakultas.text = arrayList?.get(position)?.id_fakultas
        holder.view.lbl_kodefakultas.text ="Kode Fakultas" +arrayList.get(position).kode_fakultas
        holder.view.lbl_namafakultas.text ="Nama Fakultas :" +arrayList.get(position).nama_fakultas


        holder.view.cv_list.setOnClickListener {

            val i = Intent(context,ManageFakultasActivity::class.java)
            i.putExtra("ID",arrayList.get(position).id_fakultas)
            i.putExtra("Kode Fakultas",arrayList.get(position).kode_fakultas)
            i.putExtra("Nama Fakultas",arrayList.get(position).kode_fakultas)
            context.startActivity(i)

        }

    }
    class Holder(val view:View) : RecyclerView.ViewHolder(view)
}