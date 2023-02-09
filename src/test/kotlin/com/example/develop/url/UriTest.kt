package com.example.develop.url

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.net.MalformedURLException
import java.net.URI
import java.net.URISyntaxException
import java.net.URL

class UriTest {

    private var target = "https://www.google.com/search?q=java+uri+url&oq=java+uri+url"


    @Test
    fun `hostname 만 가지고 오는 정규식 및 URI 조합 `() {

        //정규식으로 가지고 오는 방법
        val results = Regex("^(?:\\w+://)?((?:[^./?#]+\\.)?([^/?#]+))").find(target)
        println(results?.groupValues)
        Assertions.assertEquals(results?.groupValues?.get(0), "https://www.google.com")
        Assertions.assertEquals(results?.groupValues?.get(1), "www.google.com")
        Assertions.assertEquals(results?.groupValues?.get(2), "google.com")

        //URI 사용
        val result = "${URL(target).protocol}://${URL(target).host}"
        Assertions.assertEquals(result, "https://www.google.com")
    }

    @Test
    fun `url 형식 안 맞을 시 MalformedURLException 발생`() {
        Assertions.assertThrows(MalformedURLException::class.java) {
            target = "exce@$target"

            val url = URL(target)
        }
    }

    @Test
    fun `uri 형식 안 맞을 시 URISyntaxException 발생`() {
        Assertions.assertThrows(URISyntaxException::class.java) {
            target = "exce@$target"

            val url = URI(target)
        }
    }

    @Test
    fun `java url & uri test`() {

        val url = URL(target)
        val uri = URI(target)

        val urlAuthority = url.authority
        val uriAuthority = uri.authority
        println(urlAuthority)
        println(uriAuthority)

        val urlHost = url.host
        val uriHost = uri.host
        println(urlHost)
        println(uriHost)

        val urlProtocol = url.protocol
        println(urlProtocol)

        val urlPath = url.path
        val uriPath = uri.path
        println(urlPath)
        println(uriPath)

        val urlUserinfo = url.userInfo
        val uriUserinfo = uri.userInfo
        println(urlUserinfo)
        println(uriUserinfo)

        val urlPort = url.port
        val uriFragment = uri.fragment
        println(urlPort)
        println(uriFragment)
    }

}