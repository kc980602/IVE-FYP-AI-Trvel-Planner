package com.triple.triple.Helper;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Kevin on 2018/4/6.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int itemInRow;
    public SpacesItemDecoration(int space, int itemInRow) {
        this.space = space;
        this.itemInRow = itemInRow;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        if (parent.getChildLayoutPosition(view) >= itemInRow) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
