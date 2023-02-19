package com.example.develop.utils

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class JsonUtils {

    private var gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .setLenient()
        .create()

    private var mapper: ObjectMapper = ObjectMapper()

    companion object {

        /**
         * Gets instance.
         *
         * @return the instance
         */
        private fun getInstance() = JsonUtils()

        fun getObjectMapper(): ObjectMapper = getInstance().mapper
            .registerModule(JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)

    }
}