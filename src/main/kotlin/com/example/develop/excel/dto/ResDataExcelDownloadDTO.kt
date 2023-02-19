package com.example.develop.excel.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.time.LocalDateTime

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ResDataExcelDownloadDTO(

    var name: String? = null,

    var email: String? = null,

    var tel: String? = null,

    var fax: String? = null,

    var postcode: String? = null,

    var address1: String? = null,

    var address2: String? = null,

    var createdAt: LocalDateTime? = null,

    var updatedAt: LocalDateTime? = null,

    ) {
}