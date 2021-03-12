package com.example.runningapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.runningapp.data.room.entities.Sprint
import com.example.runningapp.databinding.ItemSprintBinding
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class HistoryAdapter() : ListAdapter<Sprint, HistoryAdapter.SprintViewHolder>(object : DiffUtil.ItemCallback<Sprint>(){
    override fun areItemsTheSame(oldItem: Sprint, newItem: Sprint): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Sprint, newItem: Sprint): Boolean = oldItem == newItem
}) {

    inner class SprintViewHolder(private val binding: ItemSprintBinding) :
        RecyclerView.ViewHolder(binding.root){
        fun bind(sprint: Sprint){
            with(binding){
                binding.tvDatetime.text = sprint.datetime.toString(DateTimeFormat.shortDateTime())
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SprintViewHolder =
        SprintViewHolder(ItemSprintBinding.inflate(
        LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: SprintViewHolder, position: Int) = holder.bind(getItem(position))
}

