package com.triple.triple.Helper;

/**
 * Created by Kevin on 2018/2/20.
 */

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class RecycleViewPaddingHelper extends RecyclerView.ItemDecoration {

    private final int edgePadding;

    /**
     * EdgeDecorator
     *
     * @param edgePadding padding set on the left side of the first item and the right side of the last item
     */
    public RecycleViewPaddingHelper(int edgePadding) {
        this.edgePadding = edgePadding;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemCount = state.getItemCount();

        final int itemPosition = parent.getChildAdapterPosition(view);

        // no position, leave it alone
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }

        // first item
        if (itemPosition == 0) {
            outRect.set(view.getPaddingLeft(), edgePadding, view.getPaddingRight(), view.getPaddingBottom());
        }

    }
}