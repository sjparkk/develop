package com.example.develop.image

import org.springframework.stereotype.Service
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile

@Service
class ImageService(
    val imageRepository: ImageRepository
) {

    fun save(
        file: MultipartFile,
        title: String,
        description: String
    ) {
        val fileName: String? = file.originalFilename?.let { StringUtils.cleanPath(it) }
        val imageData: ByteArray = file.bytes
        val image = BlobImage(
            title = title,
            description = description,
            fileName = fileName,
            contentType = file.contentType!!,
            data = imageData
        )

        imageRepository.save(image)

    }
}