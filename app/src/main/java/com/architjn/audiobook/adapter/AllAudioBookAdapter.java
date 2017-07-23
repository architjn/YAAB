package com.architjn.audiobook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.architjn.audiobook.R;
import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.bean.Folder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiz@rd on 11/23/2016.
 */
public class AllAudioBookAdapter extends RecyclerView.Adapter<AllAudioBookAdapter.ViewHolder> {

    private static final int GO_BACK = 132;
    private static final int NORMAL_VIEW = 135;
    private Context context;
    private List<AudioBook> items;
    private IAdapterItemClick<AudioBook> callback;

    public AllAudioBookAdapter(Context context, ArrayList<AudioBook> items,
                               IAdapterItemClick<AudioBook> callback) {
        this.context = context;
        this.items = items;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_audiobook, parent, false);
            return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onItemSelected(holder.getAdapterPosition() - 1,
                            items.get(holder.getAdapterPosition() - 1));
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void updateList(ArrayList<AudioBook> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }
}

