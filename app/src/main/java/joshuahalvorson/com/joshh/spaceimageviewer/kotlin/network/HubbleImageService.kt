package joshuahalvorson.com.joshh.spaceimageviewer.kotlin.network

import io.reactivex.Observable
import joshuahalvorson.com.joshh.spaceimageviewer.kotlin.model.Model
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HubbleImageService {

    @GET("api/v3/image/{id}/")
    fun getImageData(@Path("id") id: Int): Observable<Model.HubbleImage>

    @GET("api/v3/images/")
    fun getAllImages(@Query("page") page: Int): Observable<List<Model.ImagePreview>>

    companion object {
        fun create(): HubbleImageService {

            val retrofit = Retrofit.Builder()
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(
                            GsonConverterFactory.create())
                    .baseUrl("http://hubblesite.org/")
                    .build()

            return retrofit.create(HubbleImageService::class.java)
        }
    }

}