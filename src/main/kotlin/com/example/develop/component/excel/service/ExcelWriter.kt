package com.example.develop.component.excel.service

import com.example.develop.component.excel.constant.ExcelConstant.Companion.AUTO_SIZING
import com.example.develop.component.excel.constant.ExcelConstant.Companion.BODY
import com.example.develop.component.excel.constant.ExcelConstant.Companion.COLOMN_WIDTH
import com.example.develop.component.excel.constant.ExcelConstant.Companion.DEFAULT_COLOUMN_WIDTH
import com.example.develop.component.excel.constant.ExcelConstant.Companion.EMPTY_COL
import com.example.develop.component.excel.constant.ExcelConstant.Companion.FILE_NAME
import com.example.develop.component.excel.constant.ExcelConstant.Companion.HEADER
import com.example.develop.component.excel.constant.ExcelConstant.Companion.MERGED_REGION
import com.example.develop.component.excel.constant.ExcelConstant.Companion.SHEET_NAME
import com.example.develop.component.excel.constant.ExcelConstant.Companion.TITLE
import com.example.develop.component.excel.model.ExcelAddMergedRegion
import com.example.develop.component.excel.utils.ExcelUtils
import org.apache.commons.lang3.StringUtils
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.hssf.util.HSSFColor
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ExcelWriter(
    private val workbook: Workbook,
    private val model: Map<String, Any> = mapOf(),
    private val request: HttpServletRequest,
    private val response: HttpServletResponse,

    ) {
    val log: Logger = LoggerFactory.getLogger(this::class.java)

    /* 타이틀 스타일 */
    private var tStyle: CellStyle = workbook.createCellStyle()

    /* 헤더 스타일 */
    private var hStyle: CellStyle = workbook.createCellStyle()

    /* 바디 스타일 */
    private var bStyle: CellStyle = workbook.createCellStyle()

    /* 제목 여부 */
    private var titleYN: Boolean = false

    /**
     * 엑셀 생성
     */
    fun create() {

        // 1. 파일명 셋팅
        applyFileNameForRequest(mapToFileName())
        // 2. ContentType 셋팅
        applyContentTypeForRequest()
        createCellStyle()
        // 3. 시트 생성
        //sheet.addMergedRegion( CellRangeAddress(1, 1, 1, 2))
        val sheet = springToSheetName(SHEET_NAME)?.let { workbook.createSheet(it) } ?: workbook.createSheet()
        //AutoSizing 옵션 사용시 엑셀쓰기가 10배가 느려져서 제거함
        //((SXSSFSheet) sheet).trackAllColumnsForAutoSizing(); //excelXlsxStreamingView AutoSize 조절시 꼭 설정해야함
        // 4. 제목 생성
        createTitle(sheet, stringToTitle(), mapToHeaderList())
        // 5. 헤더 생성
        createHeader(sheet, mapToHeaderList())
        // 6. 바디 생성
        createBody(sheet, mapToBodyList(), mapToMergedList(MERGED_REGION))

    }

    /**
     * 파일명 설정
     * @return
     */
    private fun mapToFileName(): String? {
        return model[FILE_NAME] as String?
    }

    /**
     * 시트 이름 설정 (커스텀)
     * @return
     */
    private fun springToSheetName(sheetName: String): String? {
        return model[sheetName] as String?
    }

    /**
     * 제목 설정
     * @return
     */
    private fun stringToTitle(): String? {
        return model[TITLE] as String?
    }

    /**
     * 헤더 설정
     * @return
     */
    private fun mapToHeaderList(): List<String?> {
        return if (model[HEADER] != null && model[HEADER] is List<*>)
            model[HEADER] as List<String?>
        else listOf()
    }

    /**
     * 바디 설정
     * @return
     */
    private fun mapToBodyList(): List<List<String?>> {
        val lists = if (model[BODY] != null && model[BODY] is List<*>)
            model[BODY] as List<MutableList<String?>>
        else listOf()

        val empty = mapToBodyEmptyList()

        for (list in lists) {
            for (i in 0 until list.count()) {
                var isEmpty = false
                empty.forEach {
                    if (i.equals(it)) isEmpty = true
                }
                if (isEmpty) list.removeAt(i)
            }
        }

        return lists

    }

    /**
     * merged list
     * @return List<String>
     */
    private fun mapToMergedList(merged: String): List<ExcelAddMergedRegion> {
        return if (model[merged] != null && model[merged] is List<*>)
            model[merged] as List<ExcelAddMergedRegion>
        else listOf()
    }

    /**
     * empty list
     * @return List<String>
     */
    private fun mapToBodyEmptyList(): List<Integer> {
        return if (model[EMPTY_COL] != null && model[EMPTY_COL] is List<*>)
            model[EMPTY_COL] as List<Integer>
        else listOf()
    }

    /**
     * 파일명 설정 적용
     * @param fileName
     */
    private fun applyFileNameForRequest(fileName: String?) {
        var excelFileName = fileName ?: "export"
        try {
            log.info("Excel File Created:: fileName = {}.xlsx", excelFileName)

            excelFileName = if (excelFileName.isNotEmpty()) ExcelUtils.getDisposition(
                excelFileName,
                ExcelUtils.getBrowser(request)
            ) else "export"
            excelFileName = appendFileExtension(excelFileName)
        } catch (e: Exception) {
            throw IllegalArgumentException(e.message, e)
//            throw ExcelComponentException(e.message, e)
        }
        response.setHeader("Content-Disposition", "attachment; filename=\"$excelFileName\"")
        response.setHeader("Content-Transfer-Encoding", "binary")
        response.setHeader("Cache-Control", "no-cache, must-revalidate")
        response.setHeader("Pragma", "no-cache")
        response.setHeader("Expires", "-1")
    }

    /**
     * 파일 확장자 설정 적용
     * @param fileName
     * @return
     */
    private fun appendFileExtension(fileName: String): String {
        var fileName = fileName
        if (workbook is XSSFWorkbook || workbook is SXSSFWorkbook) {
            fileName += ".xlsx"
        }
        if (workbook is HSSFWorkbook) {
            fileName += ".xls"
        }
        return fileName
    }

    /**
     * 요청 ContentType 적용
     */
    private fun applyContentTypeForRequest() {
        if (workbook is XSSFWorkbook || workbook is SXSSFWorkbook) {
            response.setHeader("Content-Type", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
        }
        if (workbook is HSSFWorkbook) {
            // response.setHeader("Content-Type", "application/vnd.ms-excel;charset=EUC-KR");
            response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8")
        }
    }

    /**
     * 제목 생성
     * @param sheet
     * @param title
     */
    private fun createTitle(sheet: Sheet, title: String?, headList: List<String?>?) {
        if (StringUtils.isNotEmpty(title)) {
            var colSize = headList!!.size
            if (colSize > 5) colSize = 5

            //셀 병합
            sheet.addMergedRegion(CellRangeAddress(0, 0, 0, colSize))
            //헤더 로우 생성
            createRow(sheet, title, 0, tStyle)
            titleYN = true
        }
    }

    /**
     * 헤더 생성
     * @param sheet
     * @param headList
     */
    private fun createHeader(sheet: Sheet, headList: List<String?>?) {
        val rowNum = if (titleYN) 1 else 0
        if (headList != null) {
            val size = headList.size
            val row = sheet.createRow(rowNum)
            for (i in 0 until size) {
                row.createCell(i).setCellValue(if (headList[i] != null) headList[i] else " ")

                if (model[AUTO_SIZING] as Boolean) sheet.autoSizeColumn(i) //컬럼 width  사이즈 보기좋게 확장
                else {
                    //컬럼 사이즈 적용 사용자 정의 설정이 없으면 기본 설정 적용
                    val colomnWidth =
                        if (model[COLOMN_WIDTH] != null) model[COLOMN_WIDTH] as Int? else DEFAULT_COLOUMN_WIDTH
                    sheet.setColumnWidth(i, colomnWidth!!)
                }
            }
        }
    }

    /**
     * 바디 생성
     * @param sheet
     * @param bodyList
     */
    private fun createBody(sheet: Sheet, bodyList: List<List<String?>>?, mergedList: List<ExcelAddMergedRegion>) {
        if (bodyList != null) {
            val rowSize = bodyList.size
            val rowNumExtendValue = if (titleYN) 2 else 1
            for (i in 0 until rowSize) {
                // 로우별 셀 생성
                createRow(sheet, bodyList[i], i + rowNumExtendValue, bStyle)
            }
            // 병합 기능
            if (mergedList.isNotEmpty())
                setMergedRegion(sheet, mergedList)
        }
    }

    /**
     * 셀 스트링 데이터 생성
     * @param sheet
     * @param cellData
     * @param rowNum
     */
    private fun createRow(sheet: Sheet, cellData: String?, rowNum: Int, style: CellStyle?) {
        val cellList = if (cellData != null) listOf(cellData) else null
        createRow(sheet, cellList, rowNum, style)
    }

    /**
     * 셀 리스트 데이터 생성
     * @param sheet
     * @param cellList
     * @param rowNum
     */
    private fun createRow(sheet: Sheet, cellList: List<String?>?, rowNum: Int, style: CellStyle?) {
        if (cellList != null) {
            val size = cellList.size
            val row = sheet.createRow(rowNum)

            for (i in 0 until size) {
                row.createCell(i).setCellValue(if (cellList[i] != null) cellList[i] else " ")
            }
        }
    }


    /**
     * 셀 스타일 설정
     */
    private fun createCellStyle() {
        /** 타이틀 스타일 선언  */
        tStyle = workbook.createCellStyle()
        /** 타이틀 스타일  */
        tStyle.fillPattern = FillPatternType.SOLID_FOREGROUND
        tStyle.fillForegroundColor = HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.index
        tStyle.borderBottom = BorderStyle.THIN
        tStyle.borderLeft = BorderStyle.THIN
        tStyle.borderRight = BorderStyle.THIN
        tStyle.borderTop = BorderStyle.THIN
        tStyle.alignment = HorizontalAlignment.CENTER
        tStyle.verticalAlignment = VerticalAlignment.CENTER
        /** 헤더 FONT 셋팅   */
        val titleFont = workbook.createFont()
        titleFont.fontName = "맑은 고딕" // 폰트 이름
        titleFont.fontHeightInPoints = 15.toShort()
        titleFont.bold = true
        tStyle.setFont(titleFont)
        /** 헤더 스타일 선언  */
        hStyle = workbook.createCellStyle()
        /** 헤더 스타일  */
        hStyle.fillPattern = FillPatternType.SOLID_FOREGROUND
        hStyle.fillForegroundColor = HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.index
        hStyle.borderBottom = BorderStyle.THIN
        hStyle.borderLeft = BorderStyle.THIN
        hStyle.borderRight = BorderStyle.THIN
        hStyle.borderTop = BorderStyle.THIN
        hStyle.alignment = HorizontalAlignment.CENTER
        /** 헤더 FONT 셋팅   */
        val headerFont = workbook.createFont()
        headerFont.fontName = "맑은 고딕" // 폰트 이름
        headerFont.fontHeightInPoints = 10.toShort()
        headerFont.bold = false
        hStyle.setFont(headerFont)
        /** 바디 스타일 선언  */
        bStyle = workbook.createCellStyle()
        /** 바디 스타일  */
        bStyle.borderBottom = BorderStyle.THIN
        bStyle.borderLeft = BorderStyle.THIN
        bStyle.borderRight = BorderStyle.THIN
        bStyle.borderTop = BorderStyle.THIN
        bStyle.alignment = HorizontalAlignment.LEFT
        bStyle.verticalAlignment = VerticalAlignment.CENTER
        /** 헤더 FONT 셋팅   */
        val bodyFont = workbook.createFont()
        bodyFont.fontName = "맑은 고딕" // 폰트 이름
        bodyFont.fontHeightInPoints = 10.toShort()
        bodyFont.bold = false
        bStyle.setFont(bodyFont)
    }


    /**
     * 병합
     * @param sheet Sheet
     * @param cellList List<ExcelAddMergedRegion>
     * @param rowNum Int
     */
    private fun setMergedRegion(sheet: Sheet, mergedList: List<ExcelAddMergedRegion>) {
        mergedList.forEach {
            sheet.addMergedRegion(CellRangeAddress(it.firstRow, it.lastRow, it.firstCol, it.lastCol))
        }
    }

}