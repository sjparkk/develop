package com.example.develop.image

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/api/images")
class ImageController(
    val imageService: ImageService
) {

    @PostMapping("/upload")
    fun uploadImage(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("title") title: String,
        @RequestParam("description") description: String
    ): ResponseEntity<String> {

        imageService.save(file, title, description)
        return ResponseEntity.status(HttpStatus.OK).body("Image uploaded successfully")
    }
}