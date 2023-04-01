package com.example.develop.excel.component.dto

import com.example.develop.excel.component.model.ExcelAddMergedRegion

data class ReqExcelDownloadDTO(

    /* 엑셀 제목 */
    var title: String? = null,
    /* 엑셀 fileName */
    var sheetName: String? = null,
    /* 엑셀 fileName */
    var sheetName2: String? = null,
    /* 엑셀 Header */
    var header: Array<String> = arrayOf(),
    /* 엑셀 Header */
    var header2: Array<String> = arrayOf(),
    /* 엑셀 fileName */
    var fileName: String? = null,
    /* 엑셀 컬럼 사이즈(지정하지 않으면 기본 사이즈(3000)가 지정된다) */
    var columnWidth: Int? = null,
    /* 오토 컬럼 리사이징 사용 여부 기본 false */
    var autoSize: Boolean = false,
    /* 엑셀 MergedRegion */
    val mergedList: List<ExcelAddMergedRegion> = listOf(),
    /* 무시 로우 */
    val emptyCol: List<Int> = listOf()

) {
}