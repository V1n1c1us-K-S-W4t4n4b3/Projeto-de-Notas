package com.kzdev.appcursolucas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kzdev.appcursolucas.data.Note
import com.kzdev.appcursolucas.databinding.ItemListNoteBinding

class AdapterNote(
    private var dataSet: List<Note>,
    private val onItemClicked: (Note) -> Unit,
    ) : RecyclerView.Adapter<AdapterNote.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemListNoteBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val id = dataSet[position].uid
        val note = dataSet[position].note

        viewHolder.binding.tvId.text = id.toString()
        viewHolder.binding.tvNote.text = note

        viewHolder.itemView.setOnClickListener {
            onItemClicked(dataSet[position])
        }
    }

    override fun getItemCount() = dataSet.size

    class ViewHolder(val binding: ItemListNoteBinding) : RecyclerView.ViewHolder(binding.root)

    fun updateData(newList: List<Note>) {
        dataSet = newList
        notifyDataSetChanged()
    }
}