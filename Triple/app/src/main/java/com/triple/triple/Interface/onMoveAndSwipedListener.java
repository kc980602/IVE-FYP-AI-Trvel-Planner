package com.triple.triple.Interface;

/**
 * Created by Kevin on 2018/2/20.
 */

public interface onMoveAndSwipedListener {

    boolean onItemMove(int fromPosition, int toPosition);

    void onItemDismiss(int position);

}