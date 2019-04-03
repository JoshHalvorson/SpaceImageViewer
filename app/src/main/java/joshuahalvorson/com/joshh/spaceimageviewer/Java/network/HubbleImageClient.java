package joshuahalvorson.com.joshh.spaceimageviewer.Java.network;

import java.util.List;

import joshuahalvorson.com.joshh.spaceimageviewer.Java.image.HubbleImage;
import joshuahalvorson.com.joshh.spaceimageviewer.Java.image.ImagePreview;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HubbleImageClient {

    String BASE_URL = "http://hubblesite.org/";

    @GET("api/v3/image/{id}/")
    Call<HubbleImage> getImageData(@Path("id") int id);

    @GET("api/v3/images/")
    Call<List<ImagePreview>> getAllImages(@Query("page") int page);



}
