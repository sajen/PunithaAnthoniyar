package com.church.punithaanthoniyar.bible.view

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.church.punithaanthoniyar.R
import com.church.punithaanthoniyar.bible.model.BibleChapter
import com.church.punithaanthoniyar.saintofday.model.Saint

class BibleListAdapter (private val context: Context,private val mList: MutableList<BibleChapter>) : RecyclerView.Adapter<BibleListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.bible_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = mList[position]
        holder.chapterName.text = item.chapterName
        holder.chapterCount.text = "("+item.count+")"

        holder.itemView.setOnClickListener {
            val intent = Intent(context,BibleDetailActivity::class.java)
            intent.putExtra("BookName",item.chapterName)
            intent.putExtra("BookId",item.chapterId)
            intent.putExtra("ChapterCount",item.count)

            context.startActivity(intent)

        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val chapterName: AppCompatTextView = itemView.findViewById(R.id.bible_chapters)
        val chapterCount: AppCompatTextView = itemView.findViewById(R.id.bible_chapters_count)
    }
}