package cocooncreations.net.moviereviews.ui.base.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import cocooncreations.net.moviereviews.R;
import cocooncreations.net.moviereviews.data.model.Movie;
import cocooncreations.net.moviereviews.ui.base.listener.OnItemClickListener;
import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;

/**
 * Created by aleksandr on 10/14/17.
 */

public class RealmMoviesAdapter extends RealmRecyclerViewAdapter<Movie, RealmMoviesAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener onItemClickListener;

    public RealmMoviesAdapter(@NonNull Context context, @Nullable OrderedRealmCollection<Movie> data) {
        super(data, true);
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = getData().get(position);
        if (movie.getMultimedia() != null) {
            Glide.with(context).load(movie.getMultimedia().getSrc())
                    .apply(new RequestOptions().placeholder(R.drawable.img_default_movie).error(R.drawable.img_default_movie))
                    .into(holder.imageMain);
        } else {
            holder.imageMain.setImageResource(R.drawable.img_default_movie);
        }
        holder.textTitle.setText(movie.getTitle());
    }

    @Override
    public int getItemCount() {
        return getData() == null ? 0 : getData().size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageMain;
        private TextView textTitle;

        ViewHolder(final View itemView) {
            super(itemView);

            imageMain = itemView.findViewById(R.id.image_main);
            textTitle = itemView.findViewById(R.id.text_title);

            itemView.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }
    }
}
