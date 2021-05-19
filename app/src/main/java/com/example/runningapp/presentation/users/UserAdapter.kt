package com.example.runningapp.presentation.users

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.runningapp.databinding.ItemUserBinding

class UserAdapter(
    private val itemClick: (String) -> Unit,
    private val subscribeClick: (String) -> Unit,
    private val unsubscribeClick: (String) -> Unit
) : ListAdapter<UserItem, UserAdapter.UserViewHolder>(object : DiffUtil.ItemCallback<UserItem>() {
    override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean =
        oldItem == newItem

}) {

    class UserViewHolder(
        private val binding: ItemUserBinding,
        private val itemClick: (String) -> Unit,
        private val subscribeClick: (String) -> Unit,
        private val unsubscribeClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userItem: UserItem) {
            Log.d("MYTAG", "UserViewHolder bind: ${userItem.isSubscribed}")
            with(binding){
                btnSubscribe.isVisible = !userItem.isSubscribed
                btnUnsubscribe.isVisible = userItem.isSubscribed
                tvName.text = userItem.name
                root.setOnClickListener {
                    itemClick.invoke(userItem.id)
                }
               btnSubscribe.setOnClickListener {
                    subscribeClick.invoke(userItem.id)
                }
                btnUnsubscribe.setOnClickListener {
                    unsubscribeClick.invoke(userItem.id)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            itemClick,
            subscribeClick,
            unsubscribeClick
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) =
        holder.bind(getItem(position))

}