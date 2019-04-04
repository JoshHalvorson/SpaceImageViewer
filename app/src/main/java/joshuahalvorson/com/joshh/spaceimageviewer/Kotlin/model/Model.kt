package joshuahalvorson.com.joshh.spaceimageviewer.Kotlin.model

import java.io.Serializable

object Model {

    data class HubbleImage(var id: Int = 0,
                           var name: String? = null,
                           var description: String? = null,
                           var credits: String? = null,
                           var newsName: String? = null,
                           var mission: String? = null,
                           var collection: String? = null,
                           var imageFiles: List<ImageFile>? = null,
                           var loadingImage: String? = null,
                           var thumbnailImage: String? = null,
                           var fullResImage: String? = null) : Serializable

    data class ImageFile(val fileUrl: String? = null,
                         val fileSize: Int = 0,
                         var width: Int = 0,
                         var height: Int = 0) : Serializable

    data class ImagePreview(var id: Int = 0,
                            var name: String? = null,
                            var newsName: String? = null,
                            var collection: String? = null) : Serializable

}