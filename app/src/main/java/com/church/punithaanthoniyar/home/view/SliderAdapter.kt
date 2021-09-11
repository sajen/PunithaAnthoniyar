package com.church.punithaanthoniyar.home.view

import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.church.punithaanthoniyar.R
import com.opensooq.pluto.base.PlutoAdapter
import com.opensooq.pluto.base.PlutoViewHolder
import com.opensooq.pluto.listeners.OnItemClickListener

class SliderAdapter(imageList: ArrayList<String> = arrayListOf<String>(), onItemClickListener: OnItemClickListener<String>) : PlutoAdapter<String, SliderAdapter.ViewHolder>(imageList, onItemClickListener) {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent, R.layout.hom_gallery_image_view)
    }

    class ViewHolder(parent: ViewGroup, itemLayoutId: Int) : PlutoViewHolder<String>(parent, itemLayoutId) {
        private var ivPoster: ImageView = getView(R.id.img_view)

        override fun set(item: String, position: Int) {

            Glide.with(context).load(item).override(SIZE_ORIGINAL, SIZE_ORIGINAL).into(ivPoster)
        }
    }
}