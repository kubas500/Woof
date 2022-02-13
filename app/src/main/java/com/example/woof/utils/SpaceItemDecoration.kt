package com.example.woof.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(
    private val topFirst: Int,
    private val bottomLast: Int,
    private val side: Int,
    private val middle: Int,
): RecyclerView.ItemDecoration() {

    constructor(all: Int): this(all, all, all, all)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val position = parent.getChildAdapterPosition(view)
        parent.adapter?.let {
            if(position == 0) outRect.top = topFirst
            if(position == it.itemCount - 1) outRect.bottom = bottomLast
            if(position in 1 until it.itemCount) outRect.top = middle

            outRect.right = side
            outRect.left = side
        }
    }
}