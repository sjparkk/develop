package com.example.develop.component.excel.service

import com.example.develop.component.excel.constant.ExcelConstant.Companion.EXCEL_XLSX_STREAMING_VIEW
import com.example.develop.component.excel.dto.ReqExcelDownloadDTO
import org.springframework.stereotype.Service
import org.springframework.web.servlet.ModelAndView

@Service
class ExcelService(
    val excelConverter: ExcelConverter
) {

    /**
     * 엑셀 다운로드 서비스
     * Map 이나 객체 형태로 받아서 엑셀 파일로 만들어 리턴함
     * 엑셀 다운로드 서비스를 이용하기 위해서 요청 파라미터는 ReqExcel 엑셀 객체를 상속 받아서 파라미터로 전달
     * @param list List<T> 엑셀 변환 데이터
     * @param req ReqExcelDownloadDTO 엑셀 파라미터
     */
    fun <T> download(list: List<T>, req: ReqExcelDownloadDTO): ModelAndView {
        return ModelAndView(EXCEL_XLSX_STREAMING_VIEW, excelConverter.convertList(list, req))
    }

}