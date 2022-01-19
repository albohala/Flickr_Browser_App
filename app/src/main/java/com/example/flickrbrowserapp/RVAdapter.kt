package com.example.flickrbrowserapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickrbrowserapp.databinding.ItemRowBinding

class RVAdapter(var photo: ArrayList<PhotosDetails>, val mainActivity: MainActivity):  RecyclerView.Adapter<RVAdapter.ItemViewHolder>(){
    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val photoItem = photo[position]

        holder.binding.apply {
            tvImageTitle.text = photoItem.title

            Glide.with(mainActivity).load(photoItem.url_h).into(imgImage)

            imgLarge.isVisible = false

            llImage.setOnClickListener() {
                Glide.with(mainActivity).load(photoItem.url_h).into(imgLarge)
                tvImageTitle.isVisible = false
                imgImage.isVisible = false
                imgLarge.isVisible = true
            }
        }
    }

    override fun getItemCount(): Int {
        return photo.size
    }

    fun update(photos: ArrayList<PhotosDetails>) {
        this.photo = photos
        notifyDataSetChanged()
    }
}