package joshuahalvorson.com.joshh.spaceimageviewer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class HubbleImageAdapter extends RecyclerView.Adapter<HubbleImageAdapter.HubbleImageHolder> {

        public interface OnItemClickListener {
            void onItemClick(HubbleImage image);
        }

        Context context;
        List<HubbleImage> imageList;
        HubbleImage image;

        private final OnItemClickListener listener;

        public HubbleImageAdapter(Context context, List<HubbleImage> imageList, OnItemClickListener listener){
            this.context = context;
            this.imageList = imageList;
            this.listener = listener;
        }

        @NonNull
        @Override
        public HubbleImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout, viewGroup, false);
            return new HubbleImageHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final HubbleImageAdapter.HubbleImageHolder hubbleImageHolder, final int i) {
            image = imageList.get(i);
            hubbleImageHolder.bind(image, listener);


        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }

        class HubbleImageHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;
            CardView cardView;

            public HubbleImageHolder(View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                textView = itemView.findViewById(R.id.textView);
                cardView = itemView.findViewById(R.id.card_view);
            }

            public void bind(final HubbleImage hubbleImage, final OnItemClickListener listener) {
                Glide.with(context)
                        .load(image.getThumbnailImage())
                        .apply(new RequestOptions()
                                .placeholder(R.drawable.placeholder_50x50)
                                .override(50,50)
                                .fitCenter()
                        )
                        .into(imageView);
                textView.setText(image.getName());
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        listener.onItemClick(hubbleImage);
                    }
                });
            }
        }
}
