package com.example.develop.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.stereotype.Component

@ConstructorBinding
@ConfigurationProperties("binding.language")
class BeforeBindingProperties(
    var a: String,
    var b: String,
    var c: String,
)

//@Component
//@ConfigurationProperties("binding.language")
//class BeforeBindingProperties() {
//    lateinit var a: String
//    lateinit var b: String
//    lateinit var c: String
//}