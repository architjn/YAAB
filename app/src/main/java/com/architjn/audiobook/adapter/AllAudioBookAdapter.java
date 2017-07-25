package com.architjn.audiobook.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.architjn.audiobook.R;
import com.architjn.audiobook.bean.AudioBook;
import com.architjn.audiobook.bean.Folder;
import com.architjn.audiobook.utils.Utils;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Wiz@rd on 11/23/2016.
 */
public class AllAudioBookAdapter extends RecyclerView.Adapter<AllAudioBookAdapter.ViewHolder> {

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
        final AudioBook book = items.get(position);
        holder.name.setText(book.getAlbumName());
        holder.author.setText(book.getArtistName());
        int h = (int) ((book.getDuration() / 1000) / 3600);
        int m = (int) (((book.getDuration() / 1000) / 60) % 60);
        holder.duration.setText(String.format(Locale.ENGLISH, "%02dhr%02dmin", h, m));
        if (book.getAlbumArt() != null) {
            Picasso.with(context).load(Utils.getUriOfMedia(book.getAlbumId()))
                    .placeholder(R.drawable.ic_default_artist)
                    .resizeDimen(R.dimen.size_64dp, R.dimen.size_64dp)
                    .centerCrop()
                    .into(holder.photo);
            Utils.log(book.getAlbumArt());
        }
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
        private final TextView name, author, duration, progressTxt;
        private final DonutProgress progress;
        private final CircleImageView photo;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            author = (TextView) itemView.findViewById(R.id.author);
            duration = (TextView) itemView.findViewById(R.id.duration);
            progressTxt = (TextView) itemView.findViewById(R.id.progress_text);
            progress = (DonutProgress) itemView.findViewById(R.id.donut_progress);
            photo = (CircleImageView) itemView.findViewById(R.id.profile_image);
        }
    }
}

