package com.triple.triple.Helper;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.triple.triple.Interface.onMoveAndSwipedListener;

/**
 * Created by Kevin on 2018/2/20.
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private onMoveAndSwipedListener moveAndSwipedListener;

    public ItemTouchHelperCallback(onMoveAndSwipedListener listener) {
        this.moveAndSwipedListener = listener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {

        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            // for recyclerView with gridLayoutManager, support drag all directions, not support swipe
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);

        } else {
            // for recyclerView with linearLayoutManager, support drag up and down, and swipe lift and right
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags =  0;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // If the 2 items are not the same type, no dragging
        if (viewHolder.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        moveAndSwipedListener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//        moveAndSwipedListener.onItemDismiss(viewHolder.getAdapterPosition());
    }
}