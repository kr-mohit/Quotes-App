package com.idonnoe.quotesapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.idonnoe.quotesapp.models.Quote

class QuoteAdapter: ListAdapter<Quote, QuoteAdapter.QuoteViewHolder>(DiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quote_item_view, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class QuoteViewHolder(itemView: View) : ViewHolder(itemView) {

        val tvQuote = itemView.findViewById<TextView>(R.id.tv_quote)
        val tvAuthor = itemView.findViewById<TextView>(R.id.tv_author)

        fun bind(item: Quote) {
            tvQuote.text = item.content
            tvAuthor.text = item.author
        }
    }

    class DiffUtil: androidx.recyclerview.widget.DiffUtil.ItemCallback<Quote>() {
        override fun areItemsTheSame(
            oldItem: Quote,
            newItem: Quote
        ): Boolean {
            return oldItem.quoteId == newItem.quoteId
        }

        override fun areContentsTheSame(
            oldItem: Quote,
            newItem: Quote
        ): Boolean {
            return oldItem == newItem
        }

    }
}