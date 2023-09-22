package com.example.gitusers.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.gitusers.R
import com.example.gitusers.databinding.ItemUserBinding
import com.example.gitusers.domain.model.User

class UserPagingAdapter(private val clickListener: (String, User) -> Unit) :
    PagingDataAdapter<User, UserPagingAdapter.UserViewHolder>(COMPARATOR) {

    inner class UserViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = itemView.findViewById<TextView>(R.id.textView_name)
        fun bindTo(item: User?, position: Int) {
            binding.apply {

                textViewName.text = item?.login

                Glide.with(binding.root).load(item?.avatarUrl)
                    .timeout(6000).placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background).centerInside()
                    .into(imageViewUser)

                textviewNodeId.text = "NodeId - ${item?.nodeId}"
                textviewType.text = "Type - ${item?.type}"
                textviewUrl.text = "URL - ${item?.htmlUrl}"
                binding.linearLayoutExpandedArea.isVisible = item?.isExpanded == true
                textViewName.setOnClickListener {
                    item?.isExpanded = !(item?.isExpanded)!!
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindTo(getItem(position), position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.htmlUrl == newItem.htmlUrl
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

}