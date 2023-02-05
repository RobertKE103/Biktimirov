package com.example.movies.ui.main

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject
import kotlin.math.roundToInt

class SpaceItemDecoration @Inject constructor(): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val margin = 10F
        val space = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            margin,
            view.resources.displayMetrics
        )

        if (parent.getChildAdapterPosition(view) == 0){
            outRect.top = space.roundToInt()
            outRect.bottom = 0
        }

    }

}