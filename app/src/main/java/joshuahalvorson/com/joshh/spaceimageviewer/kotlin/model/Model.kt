package joshuahalvorson.com.joshh.spaceimageviewer.kotlin.model

import java.io.Serializable

object Model {

    data class HubbleImage(var id: Int = 0,
                           var name: String? = null,
                           var description: String? = null,
                           var credits: String? = null,
                           var news_name: String? = null,
                           var mission: String? = null,
                           var collection: String? = null,
                           var image_files: List<ImageFile>? = null,
                           var loadingImage: String? = null,
                           var thumbnailImage: String? = null,
                           var fullResImage: String? = null) : Serializable

    data class ImageFile(val file_url: String? = null,
                         val file_size: Int = 0,
                         var width: Int = 0,
                         var height: Int = 0) : Serializable

    data class ImagePreview(var id: Int = 0,
                            var name: String? = null,
                            var news_name: String? = null,
                            var collection: String? = null) : Serializable

}