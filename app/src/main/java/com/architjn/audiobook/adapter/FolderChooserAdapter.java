package com.architjn.audiobook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.architjn.audiobook.R;
import com.architjn.audiobook.bean.Folder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Wiz@rd on 11/23/2016.
 */
public class FolderChooserAdapter extends RecyclerView.Adapter<FolderChooserAdapter.ViewHolder> {

    private static final int GO_BACK = 132;
    private static final int NORMAL_VIEW = 135;
    private Context context;
    private List<Folder> items;
    private IAdapterItemClick<Folder> callback;

    public FolderChooserAdapter(Context context, ArrayList<Folder> items,
                                IAdapterItemClick<Folder> callback) {
        this.context = context;
        this.items = items;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == NORMAL_VIEW) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_folder_choose, parent, false);
            return new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_folder_choose, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (position == 0) {
            holder.name.setText(R.string.go_back_dots);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null)
                        callback.onGoBack();
                }
            });
            return;
        }
        holder.name.setText(items.get(position - 1).getName());
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
        return items.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return GO_BACK;
        return NORMAL_VIEW;
    }

    public void updateList(ArrayList<Folder> visitors) {
        this.items = visitors;
        notifyDataSetChanged();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;


        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

    public interface IAdapterItemClick<T> {
        void onItemSelected(int position, T item);

        void onGoBack();
    }

}

