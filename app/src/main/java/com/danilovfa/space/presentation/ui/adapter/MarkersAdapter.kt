package com.danilovfa.space.presentation.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.danilovfa.space.databinding.ItemMarkerBinding
import com.danilovfa.space.presentation.model.MapMarker

class MarkersAdapter :
    RecyclerView.Adapter<MarkersAdapter.ViewHolder>() {

    private var onMarkerDeleteListener: OnMarkerDeleteListener? = null

    private val differCallback = object : DiffUtil.ItemCallback<MapMarker>() {
        override fun areItemsTheSame(oldItem: MapMarker, newItem: MapMarker): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MapMarker, newItem: MapMarker): Boolean {
            return oldItem.label == newItem.label
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMarkerBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding, parent.context)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ViewHolder(private val binding: ItemMarkerBinding, private val context: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(marker: MapMarker) {
            binding.apply {
                markerNameTextView.text = marker.label
                markerLongitudeTextView.text = marker.marker.position.longitude.toString()
                markerLatitudeTextView.text = marker.marker.position.latitude.toString()
                markerDeleteImageButton.setOnClickListener {
                    onMarkerDeleteListener?.onMarkerDelete(marker)
                }
            }
        }
    }

    fun setOnMarkerDeleteListener(listener: OnMarkerDeleteListener) {
        onMarkerDeleteListener = listener
    }

    interface OnMarkerDeleteListener {
        fun onMarkerDelete(marker: MapMarker)
    }
}