package com.example.almin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.guess_row.view.*

class RecyclerViewAdapter (private val guesses: List<String>): RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder> (){
    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return  ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.guess_row,parent,
                false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val guess = guesses[position]
        holder.itemView.apply {
            txtGuess.text = guess
        }
    }

    override fun getItemCount() = guesses.size
}