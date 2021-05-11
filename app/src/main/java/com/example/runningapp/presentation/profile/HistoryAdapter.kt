package com.example.runningapp.presentation.history

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.runningapp.databinding.ItemSprintBinding
import com.example.runningapp.domain.model.Sprint
import com.example.runningapp.presentation.model.Time
import org.joda.time.format.DateTimeFormat

class HistoryAdapter() :
    ListAdapter<Sprint, SprintViewHolder>(object : DiffUtil.ItemCallback<Sprint>() {
        override fun areItemsTheSame(oldItem: Sprint, newItem: Sprint): Boolean =
            oldItem.userId == newItem.userId

        override fun areContentsTheSame(oldItem: Sprint, newItem: Sprint): Boolean =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SprintViewHolder =
        SprintViewHolder(
            ItemSprintBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: SprintViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class SprintViewHolder(private val binding: ItemSprintBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(sprint: Sprint) {
        with(binding) {
            Log.d("MYTAG", "HistoryAdapter bind(): ${sprint.dateTime}")
            tvDatetime.text =
                "date time: " + sprint.dateTime.toString(DateTimeFormat.shortDateTime())
            tvAvgSpeed.text = "average speed: " + sprint.avgSpeed.toString()
            val time = Time().apply {
                addSeconds(sprint.secondsRun)
            }
            tvDistance.text = "distance: " + sprint.distance.toString()
            tvSecondsRun.text = "seconds run: $time"
        }
    }
}
