package com.church.punithaanthoniyar.saintofday

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.saintofday.model.Saint

class SaintOfDayAdapter (private val context: Context,private val mList: List<Saint>) : RecyclerView.Adapter<SaintOfDayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.saint_item_layout, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]

        holder.saintName.text = item.saintName
        holder.saintBorn.text = context.getString(R.string.saint_born,item.saintBorn)
        holder.saintDied.text = context.getString(R.string.saint_died,item.saintDied)
        holder.saintAbout.text = item.saintAbout

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val saintName: AppCompatTextView = itemView.findViewById(R.id.saint_name)
        val saintBorn: AppCompatTextView = itemView.findViewById(R.id.saint_born)
        val saintDied: AppCompatTextView = itemView.findViewById(R.id.saint_died)
        val saintAbout: AppCompatTextView = itemView.findViewById(R.id.saint_about)
    }
}