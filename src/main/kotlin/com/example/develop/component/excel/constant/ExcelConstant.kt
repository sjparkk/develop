package com.example.develop.component.excel.constant

/**
 * 엑셀 공통 기능 상수
 */
class ExcelConstant {

    companion object {

        val TITLE: String = "title"
        val FILE_NAME: String = "fileName"
        val SHEET_NAME: String = "sheetName"
        val HEADER: String = "header"
        val BODY: String = "body"

        val COLOMN_WIDTH: String = "colomnWidth"
        val AUTO_SIZING: String = "autoSizing"

        val MERGED_REGION: String = "mergedRegion"
        val EMPTY_COL: String = "emptyCol"

        /* 기본 컬럼 넓이 확장 사이즈 */
        val DEFAULT_COLOUMN_WIDTH: Int = 3000

        /* 엑셀 다운로드 상수 */ /* XLSX-STREAMING */
        val EXCEL_XLSX_STREAMING_VIEW: String = "excelXlsxStreamingView"

    }

}