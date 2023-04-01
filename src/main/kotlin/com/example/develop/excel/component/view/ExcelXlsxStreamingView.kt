package com.example.develop.excel.component.view

import com.example.develop.excel.component.service.ExcelWriter
import org.apache.poi.ss.usermodel.Workbook
import org.springframework.stereotype.Component
import org.springframework.web.servlet.view.document.AbstractXlsxStreamingView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class ExcelXlsxStreamingView: AbstractXlsxStreamingView() {
    override fun buildExcelDocument(
        model: MutableMap<String, Any>,
        workbook: Workbook,
        request: HttpServletRequest,
        response: HttpServletResponse
    ) {
        ExcelWriter(workbook, model, request, response).create()
    }
}