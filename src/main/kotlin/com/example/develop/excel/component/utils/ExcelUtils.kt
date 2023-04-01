package com.example.develop.excel.component.utils

import eu.bitwalker.useragentutils.Browser
import org.apache.poi.ss.formula.eval.ErrorEval
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.DateUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.*
import java.util.function.Function
import java.util.function.UnaryOperator
import java.util.stream.Collectors
import javax.servlet.http.HttpServletRequest

class ExcelUtils {

    companion object {
        val log: Logger = LoggerFactory.getLogger(this::class.java)

        fun getBrowser(request: HttpServletRequest): String {
            val header = request.getHeader("User-Agent")
            log.info("User-Agent = {}", header)
            return if (header != null) {
                if (header.contains("MSIE") || header.contains("Trident")) {
                    "MSIE"
                } else if (header.contains("Chrome")) {
                    "Chrome"
                } else if (header.contains("Opera")) {
                    "Opera"
                } else if (header.contains("Trident/7.0")) { //IE 11 이상 //IE 버전 별 체크 >> Trident/6.0(IE 10) , Trident/5.0(IE 9) , Trident/4.0(IE 8)
                    "MSIE"
                } else {
                    "Firefox"
                }
            } else {
                "MSIE"
            }
        }

        @Throws(Exception::class)
        fun getDisposition(filename: String, browser: String): String {
            val encodedFilename: String
            when (browser) {
                "MSIE" -> {
                    encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8)
                    //encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
                }
                "Firefox" -> {
                    //encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
                    encodedFilename = String(filename.toByteArray(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)
                }
                "Opera" -> {
                    //encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
                    encodedFilename = String(filename.toByteArray(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)
                }
                "Chrome" -> {
                    val sb = StringBuffer()
                    for (element in filename) {
                        val c = element
                        if (c > '~') {
                            sb.append(URLEncoder.encode("" + c, "UTF-8"))
                        } else {
                            sb.append(c)
                        }
                    }
                    encodedFilename = sb.toString()
                }
                else -> {
                    encodedFilename = String(filename.toByteArray(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)
                }
            }
            return encodedFilename
        }

        /**
         * 파일명 Encoder
         */
        private enum class FileNameEncoder(browser: Browser, encodeOperator: UnaryOperator<String>) {
            IE(Browser.IE, UnaryOperator<String> { it: String? ->
                try {
                    return@UnaryOperator URLEncoder.encode(it, StandardCharsets.UTF_8.name())
                        .replace("\\+".toRegex(), "%20")
                } catch (e: UnsupportedEncodingException) {
                    return@UnaryOperator it
                }
            }),
            FIREFOX(Browser.FIREFOX, defaultEncodeOperator), OPERA(
                Browser.OPERA,
                defaultEncodeOperator
            ),
            CHROME(Browser.CHROME, defaultEncodeOperator), UNKNOWN(Browser.UNKNOWN, UnaryOperator.identity<String>());

            private val browser: Browser
            val encodeOperator: UnaryOperator<String>

            companion object {
                private var FILE_NAME_ENCODER_MAP: Map<Browser, Function<String, String>>? = null
                private val defaultEncodeOperator: UnaryOperator<String>
                    get() = UnaryOperator { it: String ->
                        String(
                            it.toByteArray(StandardCharsets.UTF_8),
                            StandardCharsets.ISO_8859_1
                        )
                    }

                fun encode(browser: Browser, fileName: String): String {
                    return FILE_NAME_ENCODER_MAP!![browser]!!.apply(fileName)
                }

                init {
                    FILE_NAME_ENCODER_MAP = EnumSet.allOf(FileNameEncoder::class.java).stream()
                        .collect(
                            Collectors.toMap(
                                { obj: FileNameEncoder -> obj.getBrowser() },
                                { obj: FileNameEncoder -> obj.encodeOperator })
                        )
                }
            }

            protected fun getBrowser(): Browser {
                return browser
            }

            init {
                this.browser = browser
                this.encodeOperator = encodeOperator
            }
        }

        /**
         * Cell 데이터를 Type 별로 체크 하여 String 데이터로 변환함
         * String 데이터로 우선 변환해야 함
         * @param cell 요청 엑셀 파일의 cell 데이터
         * @return String 형으로 변환된 cell 데이터
         */
        fun getValue(cell: Cell): String? {
            if (Objects.isNull(cell) || Objects.isNull(cell.cellType)) return ""
            var value: String
            when (cell.cellType) {
                CellType.STRING -> value = cell.richStringCellValue.string
                CellType.NUMERIC -> {
                    if (DateUtil.isCellDateFormatted(cell)) value = cell.localDateTimeCellValue.toString() else value =
                        cell.numericCellValue.toString()
                    if (value.endsWith(".0")) value = value.substring(0, value.length - 2)
                }
                CellType.BOOLEAN -> value = cell.booleanCellValue.toString()
                CellType.FORMULA -> value = cell.cellFormula.toString()
                CellType.ERROR -> value = ErrorEval.getText(cell.errorCellValue.toInt())
                CellType.BLANK, CellType._NONE -> value = ""
                else -> value = ""
            }
            //log.debug("## cellType = {}, value = {}",cell.getCellType(),value);
            return value
        }

    }
}