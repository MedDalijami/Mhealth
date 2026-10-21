package com.example.mhealthcat.data.repositories

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.util.Base64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import kotlin.math.roundToInt
import androidx.core.graphics.scale

object ProfilePictureStore {
    private const val SIZE_PX = 256
    private const val JPEG_QUALITY = 80

    // Picked picture (content:// Uri) -> Base64 text to save in users/{uid}.profilePicture
    suspend fun encode(context: Context, uri: Uri): String = withContext(Dispatchers.IO) {
        val source = ImageDecoder.createSource(context.contentResolver, uri)

        val decoded = ImageDecoder.decodeBitmap(source) { decoder, info, _ ->
            val shortSide = minOf(info.size.width, info.size.height)
            val scale = SIZE_PX.toFloat() / shortSide
            if (scale < 1f) {
                decoder.setTargetSize(
                    (info.size.width * scale).roundToInt(),
                    (info.size.height * scale).roundToInt()
                )
            }
            decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
        }

        val side = minOf(decoded.width, decoded.height)
        val square = Bitmap.createBitmap(
            decoded,
            (decoded.width - side) / 2,
            (decoded.height - side) / 2,
            side,
            side
        )
        val thumbnail = if (side > SIZE_PX) square.scale(SIZE_PX, SIZE_PX) else square

        val bytes = ByteArrayOutputStream().use { output ->
            thumbnail.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, output)
            output.toByteArray()
        }
        Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    suspend fun toCachedUri(context: Context, uid: String, base64: String): Uri = withContext(Dispatchers.IO) {
        val bytes = Base64.decode(base64, Base64.NO_WRAP)
        val folder = File(context.cacheDir, "profile_pictures").apply { mkdirs() }

        val file = File(folder, "profile_${uid}_${base64.hashCode()}.jpg")
        if (!file.exists()) {
            file.writeBytes(bytes)
        }

        folder.listFiles { old -> old.name.startsWith("profile_${uid}_") && old.name != file.name }
            ?.forEach { it.delete() }

        Uri.fromFile(file)
    }
}
