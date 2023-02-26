package com.example.develop.image

import org.springframework.data.jpa.repository.JpaRepository

interface ImageRepository: JpaRepository<BlobImage, Long> {
}