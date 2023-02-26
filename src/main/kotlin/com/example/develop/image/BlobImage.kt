package com.example.develop.image

import javax.persistence.*

@Entity
@Table(name = "blob_images")
class BlobImage(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: String? = null,

    @Column(name = "title")
    val title: String,

    @Column(name = "description")
    val description: String,

    @Column(name = "file_name")
    val fileName: String? = null,

    @Column(name = "content_type")
    val contentType: String,

    @Lob
    @Column(name = "data")
    val data: ByteArray
) {
    //Class 'BlobImage' should have [public, protected] no-arg constructor
//    constructor(): this("", "", "", "", "", byteArrayOf()) -> 번거로움, noarg plugin으로 해결
}