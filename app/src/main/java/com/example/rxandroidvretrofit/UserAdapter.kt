package com.example.rxandroidvretrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter():RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var listUser: List<User> = listOf()
    fun setListUser(listUser:List<User>){
        this.listUser=listUser
        notifyDataSetChanged()
    }
    lateinit var onClick:((User)->Unit)
    inner class ViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView) {
        val txtName=itemView.findViewById<TextView>(R.id.txtName)
        val imgUser=itemView.findViewById<ImageView>(R.id.imgUser)
        var item:User?=null
        init {
            itemView.setOnClickListener {
                item?.let { it1 -> onClick.invoke(it1) }
            }
        }
        fun bind(item:User){
            this.item=item
            txtName.text=item.login
            Glide.with(itemView.context).load(item.avatar_url).into(imgUser)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.dong_user,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listUser.size
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}