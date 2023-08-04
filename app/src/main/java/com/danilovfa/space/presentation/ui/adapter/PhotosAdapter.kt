package com.danilovfa.space.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.danilovfa.common.domain.model.MarsRoverPhoto
import com.danilovfa.space.R
import com.danilovfa.space.databinding.ItemPhotoBinding
import com.danilovfa.space.utils.extensions.loadImageByUrlCenterCropWithPlaceholder

class PhotosAdapter(private val photos: List<MarsRoverPhoto>) :
    RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPhotoBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding, parent.context)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    inner class ViewHolder(private val binding: ItemPhotoBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: MarsRoverPhoto) {

            binding.apply {
                cameraNameTextView.text = photo.camera
                roverNameTextView.text = photo.rover
                context.loadImageByUrlCenterCropWithPlaceholder(
                    imageUrl = photo.photoUrl,
                    view = photoImageView,
                    placeholder = R.drawable.progress_bar_photo_item
                )

                itemCardView.setOnClickListener {
                    onItemClickListener?.onItemClick(photo)
                }
            }
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(photo: MarsRoverPhoto)
    }
}