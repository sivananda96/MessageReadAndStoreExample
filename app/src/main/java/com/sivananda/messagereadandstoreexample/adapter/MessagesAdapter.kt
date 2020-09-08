package com.sivananda.messagereadandstoreexample.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sivananda.messagereadandstoreexample.R
import com.sivananda.messagereadandstoreexample.model.BankMessageItem

class MessagesAdapter(val msgList: MutableList<BankMessageItem>?) :
    RecyclerView.Adapter<MessagesAdapter.MessageHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_row_msg, parent, false)
        return MessageHolder(view)
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.bind(msgList?.get(position))
    }

    override fun getItemCount(): Int {
        return msgList?.count() ?: 0
    }

    fun addItem(msg : BankMessageItem) {
        msgList?.add(msg)
        notifyDataSetChanged()
    }

    class MessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var msgTv: TextView ?= null
        private var numTv: TextView ?= null
        private var dateTv : TextView? = null

        init {
            msgTv = itemView.findViewById(R.id.textView3)
            numTv = itemView.findViewById(R.id.textView2)
            dateTv = itemView.findViewById(R.id.textView1)
        }

        fun bind(message: BankMessageItem?) {
            msgTv?.text = message?.content
            numTv?.text = message?.number
            dateTv?.text = message?.date
        }
    }
}