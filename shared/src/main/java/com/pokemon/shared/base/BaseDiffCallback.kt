package com.pokemon.shared.base

import androidx.recyclerview.widget.DiffUtil
import com.pokemon.shared.interfaces.DiffUtilEquality

class BaseDiffCallback<T: Any> :
    DiffUtil.ItemCallback<T>()  {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.equals(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is DiffUtilEquality) {
            (oldItem as DiffUtilEquality).realEquals(newItem)
        } else oldItem.equals(newItem)
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? {
        if (oldItem is DiffUtilEquality) {
            if(!oldItem.realEquals(newItem)) {
                return oldItem.payloadChange(newItem)
            }
        }
        return super.getChangePayload(oldItem, newItem)
    }
}