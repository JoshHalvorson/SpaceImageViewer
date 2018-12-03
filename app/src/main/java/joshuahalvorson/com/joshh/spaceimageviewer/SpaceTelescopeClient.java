package joshuahalvorson.com.joshh.spaceimageviewer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SpaceTelescopeClient {

    @GET("api/v3/image/{id}")
    Call<SpaceTelescopeImage> getImageData(@Path("id") int id);

}
