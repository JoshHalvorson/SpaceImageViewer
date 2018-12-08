package joshuahalvorson.com.joshh.spaceimageviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class HubbleImageAdapter extends RecyclerView.Adapter<HubbleImageAdapter.HubbleImageHolder> {

        Context context;
        List<HubbleImage> imageList;
        HubbleImage image;

        public HubbleImageAdapter(Context context, List<HubbleImage> imageList){
            this.context = context;
            this.imageList = imageList;
        }

        @NonNull
        @Override
        public HubbleImageHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_layout, viewGroup, false);
            return new HubbleImageHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HubbleImageAdapter.HubbleImageHolder hubbleImageHolder, int i) {
            HubbleImage image = imageList.get(i);
            Glide.with(context)
                    .load(image.getThumbnailImage())
                    .into(hubbleImageHolder.imageView);
            hubbleImageHolder.textView.setText(image.getName());
        }

        @Override
        public int getItemCount() {
            return imageList.size();
        }

        class HubbleImageHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView textView;

            public HubbleImageHolder(View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.imageView);
                textView = itemView.findViewById(R.id.textView);
            }
        }
}
