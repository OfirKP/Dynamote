package com.ofirkp.sockettest

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide


import com.ofirkp.sockettest.RemotesFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_remote_item_label_bottom.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class RemoteRecyclerAdapter(
    private val mValues: List<Remote>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<RemoteRecyclerAdapter.ViewHolder>() {


    private val mOnClickListener: View.OnClickListener
    val TYPE_LABEL_BOTTOM = 0
    val TYPE_LABEL_TOP = 1

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Remote
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = when(viewType) {
            TYPE_LABEL_BOTTOM -> LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_remote_item_label_bottom, parent, false)
            TYPE_LABEL_TOP -> LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_remote_item_label_top, parent, false)
            else -> LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_remote_item_label_bottom, parent, false)
        }
            return ViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return if(position % 2 == 0) TYPE_LABEL_BOTTOM else TYPE_LABEL_TOP
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.nameView.text = item.name
        Glide.with(holder.mView.context)
            .load(item.drawableBackgroundID)
            .into(holder.bgView)
        Glide.with(holder.mView.context)
            .load(item.drawableIconID)
            .into(holder.iconView)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val bgView: ImageButton = mView.bgSoftwareImageButton
        val nameView: TextView = mView.softwareNameTextView
        val iconView: ImageView = mView.softwareIconImageView

        override fun toString(): String {
            return super.toString() + " '" + nameView.text + "'"
        }
    }
}
