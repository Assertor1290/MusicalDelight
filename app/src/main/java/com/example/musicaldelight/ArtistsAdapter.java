package com.example.musicaldelight;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

public class ArtistsAdapter extends RecyclerView.Adapter<ArtistsAdapter.ArtistsViewHolder>{
    private static ArtistsAdapter.ClickListener clickListener;

    private LayoutInflater layoutInflater;
    private Cursor ArtistsCursor;
    private Context context;

    public ArtistsAdapter(Context context, Cursor artistsCursor) {
        ArtistsCursor = artistsCursor;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public ArtistsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.artists_name,parent,false);
        //Log.d("Sense","Album CreateView");
        return new ArtistsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistsViewHolder holder, int position) {
        if (!ArtistsCursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }

        //Log.d("Sense","Album BindView");
        //holder.txt_AlbumName.setText(ArtistsCursor.getString(ArtistsCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM)));
        holder.txt_ArtistName.setText(ArtistsCursor.getString(ArtistsCursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST)));

        String album_art = ArtistsCursor.getString(ArtistsCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
        //Log.d("Sense",album_art+"");
        Glide
                .with(context)
                .load(album_art)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.album_art)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                )
                .transition(new DrawableTransitionOptions()
                        .crossFade()
                )
                .into(holder.Album_ART)
        ;
    }

    @Override
    public int getItemCount() {
        return ArtistsCursor.getCount();
    }

    class ArtistsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        TextView txt_ArtistName,txt_AlbumName;
        ImageView Album_ART;

        ArtistsViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            txt_ArtistName = itemView.findViewById(R.id.artistAlbum);
            txt_AlbumName = itemView.findViewById(R.id.ArtistName);
            Album_ART = itemView.findViewById(R.id.imageView_Artist_AlbumArt);
        }
        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }

        @Override
        public boolean onLongClick(View v) {
            clickListener.onItemLongClick(getAdapterPosition(), v);
            return false;
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ArtistsAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
        void onItemLongClick(int position, View v);
    }


}
