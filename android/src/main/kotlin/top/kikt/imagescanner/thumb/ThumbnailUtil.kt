package top.kikt.imagescanner.thumb

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.Transition
import io.flutter.plugin.common.MethodChannel
import top.kikt.imagescanner.util.ResultHandler
import java.io.ByteArrayOutputStream
import java.io.File
import android.R.attr.top




/**
 * Created by debuggerx on 18-9-27 下午2:08
 */
object ThumbnailUtil {

    fun getGlideRequestOptions(exactSize: Boolean): RequestOptions {
        var options = RequestOptions()
        if (exactSize) {
            options = options.centerCrop();
        }
        return options;
    }

  fun getThumbnailByGlide(ctx: Context, path: String, width: Int, height: Int, format: Int, quality: Int, exactSize: Boolean, result: MethodChannel.Result?) {
    val resultHandler = ResultHandler(result)
    Glide.with(ctx)
            .asBitmap()
            .apply(getGlideRequestOptions(exactSize))
            .load(File(path))
            .into(object : BitmapTarget(width, height) {
              override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                super.onResourceReady(resource, transition)
                val bos = ByteArrayOutputStream()

                val compressFormat =
                        if (format == 1) {
                          Bitmap.CompressFormat.PNG
                        } else {
                          Bitmap.CompressFormat.JPEG
                        }

                resource.compress(compressFormat, quality, bos)
                resultHandler.reply(bos.toByteArray())
              }

              override fun onLoadCleared(placeholder: Drawable?) {
                resultHandler.reply(null)
              }

              override fun onLoadFailed(errorDrawable: Drawable?) {
                resultHandler.reply(null)
              }
            })
  }


  fun getThumbOfUri(context: Context, uri: Uri, width: Int, height: Int, format: Int, quality: Int, exactSize: Boolean, callback: (ByteArray?) -> Unit) {
    Glide.with(context)
            .asBitmap()
            .apply(getGlideRequestOptions(exactSize))
            .load(uri)
            .into(object : BitmapTarget(width, height) {
              override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                super.onResourceReady(resource, transition)
                val bos = ByteArrayOutputStream()

                val compressFormat =
                        if (format == 1) {
                          Bitmap.CompressFormat.PNG
                        } else {
                          Bitmap.CompressFormat.JPEG
                        }

                resource.compress(compressFormat, quality, bos)
                callback(bos.toByteArray())
              }

              override fun onLoadCleared(placeholder: Drawable?) {
                callback(null)
              }
            })
  }


}
