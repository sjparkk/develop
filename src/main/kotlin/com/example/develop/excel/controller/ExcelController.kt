package com.example.develop.excel.controller

import com.example.develop.excel.component.dto.ReqExcelDownloadDTO
import com.example.develop.excel.component.service.ExcelService
import com.example.develop.excel.dto.ResDataExcelDownloadDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/api/excel/")
class ExcelController(
    val excelService: ExcelService
) {

    @GetMapping("download")
    fun getDataExcel(): ModelAndView {

        //필요 데이터 가지고 오는 service 생성해서 사용
        val data1 = ResDataExcelDownloadDTO(
            name = "test",
            email = "test",
            tel = "test",
            fax = "test",
            postcode = "test",
            address1 = "test",
            address2 = "test",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        val data2 = ResDataExcelDownloadDTO(
            name = "test",
            email = "test",
            tel = "test",
            fax = "test",
            postcode = "test",
            address1 = "test",
            address2 = "test",
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )

        var dataList = mutableListOf<ResDataExcelDownloadDTO>()
        dataList.add(data1)
        dataList.add(data2)

        return excelService.download(
            dataList,
            ReqExcelDownloadDTO(
                header = arrayOf(
                    "이름",
                    "이메일",
                    "연락처",
                    "팩스",
                    "우편번호",
                    "주소1",
                    "주소2",
                    "등록일시",
                    "수정일시"
                ),
                fileName = "데이터_${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}"
            )
        )
    }
}