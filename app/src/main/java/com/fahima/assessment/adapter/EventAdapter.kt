package com.fahima.assessment.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.fahima.assessment.R
import com.fahima.assessment.model.Event
import com.fahima.assessment.util.Constants
import com.fahima.assessment.util.formatTo
import com.fahima.assessment.util.toDate

class EventAdapter : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    inner class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Event>() {
        override fun areItemsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Event, newItem: Event): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    private var onItemClickListener: ((Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.event_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = differ.currentList[position]
        holder.itemView.apply {
            this.findViewById<TextView>(R.id.tvTimestamp).text = event.timestamp?.toDate()?.formatTo(Constants.DATE_FORMAT_MMM_DD_YY_HH_MM) ?: event.timestamp
            this.findViewById<TextView>(R.id.tvTitle).text = event.title
            this.findViewById<TextView>(R.id.tvLocation).text = "${event.locationLine1}, ${event.locationLine2}"
            this.findViewById<TextView>(R.id.tvDescription).text = event.description
            val contentContainer = this.findViewById<ConstraintLayout>(R.id.contentContainer)
            Glide.with(this).load(event.image).into(object : CustomTarget<Drawable>() {
                override fun onLoadCleared(placeholder: Drawable?) {}

                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    contentContainer.background = resource
                }

            })
            setOnClickListener {
                onItemClickListener?.let { it(event) }
            }
        }
    }

    fun setOnItemClickListener(listener: (Event) -> Unit) {
        onItemClickListener = listener
    }
}













