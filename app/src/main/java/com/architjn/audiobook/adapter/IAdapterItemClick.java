package com.architjn.audiobook.adapter;

/**
 * Created by HP on 23-07-2017.
 */

public interface IAdapterItemClick<T> {
    void onItemSelected(int position, T item);
}
