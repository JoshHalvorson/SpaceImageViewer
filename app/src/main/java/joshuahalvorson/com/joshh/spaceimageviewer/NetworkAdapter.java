package joshuahalvorson.com.joshh.spaceimageviewer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.senab.photoview.PhotoView;

public class NetworkAdapter {
    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(MainActivity.HTTP_HUBBLESITE_ORG)
            .addConverterFactory(GsonConverterFactory.create());
    private static SpaceTelescopeImage image;

    public static SpaceTelescopeImage getImage(int id, final ImageIdsCallBack callBack) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        builder.client(client);
        Retrofit retrofit = builder.build();
        SpaceTelescopeClient spaceTelescopeClient = retrofit.create(SpaceTelescopeClient.class);
        Call<SpaceTelescopeImage> call = spaceTelescopeClient.getImageData(id);
        call.enqueue(new Callback<SpaceTelescopeImage>() {
            @Override
            public void onResponse(Call<SpaceTelescopeImage> call, Response<SpaceTelescopeImage> response) {
                image = response.body();
                callBack.onFinish(image);
            }
            @Override
            public void onFailure(Call<SpaceTelescopeImage> call, Throwable t) {
                Log.i("NetworkAdapter", "retrofit on failure");
            }
        });
        return image;
    }

    public static PhotoView displayImage(String mainUrl, String thumbnailUrl, final ProgressBar progressBar, final Context context){
        final PhotoView photoView = new PhotoView(context);
        Glide.with(context).load(mainUrl).thumbnail(Glide.with(context).load(thumbnailUrl)).apply(RequestOptions.placeholderOf(R.drawable.ic_placeholder_400x450)).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Toast.makeText(context, "Error getting image", Toast.LENGTH_LONG).show();
                Log.i("GlideException", e.getMessage());
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                progressBar.setVisibility(View.GONE);
                photoView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                return false;
            }
        }).into(photoView);
        return photoView;
    }
}