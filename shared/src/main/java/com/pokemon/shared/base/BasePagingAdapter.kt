package com.pokemon.shared.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.pokemon.shared.Inflate
import com.pokemon.shared.interfaces.ItemListClickListener

abstract class BasePagingAdapter<T : Any, VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : PagingDataAdapter<T, BasePagingAdapter.BaseViewHolder<T, VB>>(BaseDiffCallback()) {

    abstract fun onBindData(data: T?, binding: VB, position: Int)

    protected val dataList = mutableListOf<T>()
    private var clickListener: ItemListClickListener<T?>? = null
    protected var lifecycle: Lifecycle? = null
        private set

    fun setItemClickListener(listener: ItemListClickListener<T?>) {
        clickListener = listener
    }

    fun setLifecycle(lifecycle: Lifecycle) {
        this.lifecycle = lifecycle
    }

    class BaseViewHolder<T : Any, VB : ViewBinding>(
        private val binding: VB, private val adapter: BasePagingAdapter<T, VB>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: T?) {
            adapter.onBindData(data, binding, bindingAdapterPosition)
            binding.root.setOnClickListener {
                adapter.clickListener?.onItemClickListener(data, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T, VB> {
        val binding = inflate.invoke(
            LayoutInflater.from(parent.context), parent, false
        )
        return BaseViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T, VB>, position: Int) {
        holder.onBind(getItem(position))
    }
}