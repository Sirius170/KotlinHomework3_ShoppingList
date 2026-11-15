package com.example.lab7

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class MyAdapter(
    context: Context,
    private val data: List<Item>,
    private val layout: Int
) : ArrayAdapter<Item>(context, layout, data) {

    // ViewHolder 保存子元件，避免每次重畫項目都重新 findViewById。
    private class ViewHolder(view: View) {
        val photo: ImageView = view.findViewById(R.id.imgPhoto)
        val message: TextView = view.findViewById(R.id.tvMsg)
    }

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = LayoutInflater.from(parent.context)
                .inflate(layout, parent, false)
            holder = ViewHolder(view)
            view.tag = holder
        } else {
            view = convertView
            holder = view.tag as ViewHolder
        }

        val item = data[position]
        holder.photo.setImageResource(item.photo)
        holder.message.text = if (layout == R.layout.adapter_vertical) {
            item.name
        } else {
            "${item.name}：${item.price}元"
        }

        return view
    }
}
