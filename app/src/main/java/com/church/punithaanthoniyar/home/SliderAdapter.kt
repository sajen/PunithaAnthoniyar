package com.church.punithaanthoniyar.home

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.church.punithaanthoniyar.R
import com.opensooq.pluto.base.PlutoAdapter
import com.opensooq.pluto.base.PlutoViewHolder
import com.opensooq.pluto.listeners.OnItemClickListener

class SliderAdapter(items: MutableList<Int>, onItemClickListener: OnItemClickListener<Int>) : PlutoAdapter<Int, SliderAdapter.ViewHolder>(items, onItemClickListener) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, R.layout.hom_gallery_image_view)
    }

    class ViewHolder(parent: ViewGroup, itemLayoutId: Int) : PlutoViewHolder<Int>(parent, itemLayoutId) {
        private var ivPoster: ImageView = getView(R.id.img_view)

        override fun set(item: Int, position: Int) {
            Glide.with(context).load(item).into(ivPoster)
        }
    }
}