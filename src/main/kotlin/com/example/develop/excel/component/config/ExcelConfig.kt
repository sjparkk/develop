package com.example.develop.excel.component.config

import com.example.develop.excel.component.view.ExcelXlsxStreamingView
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

/**
 * 컨트롤러에서 엑셀다운로드를 간편하게 하기 위한 설정
 */
@Configuration
class ExcelConfig(
    private val excelXlsxStreamingView: ExcelXlsxStreamingView
): WebMvcConfigurer {

    override fun configureViewResolvers(registry: ViewResolverRegistry) {
        registry.enableContentNegotiation(excelXlsxStreamingView)
    }
}