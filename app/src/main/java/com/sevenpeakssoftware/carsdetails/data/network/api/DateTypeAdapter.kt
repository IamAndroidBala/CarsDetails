package com.sevenpeakssoftware.carsdetails.data.network.api

import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTypeAdapter(
    format: String,
    timeZone: TimeZone
) : TypeAdapter<Date>() {

    private val dateFormat = SimpleDateFormat(format, Locale.US).apply {
        this.timeZone = timeZone
    }

    override fun write(out: JsonWriter, value: Date?) {
        if (value == null) {
            out.nullValue()
            return
        }
        synchronized(dateFormat) {
            val dateFormatAsString = dateFormat.format(value)
            out.value(dateFormatAsString)
        }
    }

    override fun read(`in`: JsonReader): Date? {
        if (`in`.peek() == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        return deserializeToDate(`in`.nextString())
    }

    private fun deserializeToDate(s: String): Date {
        synchronized(dateFormat) {
            try {
                return dateFormat.parse(s)!!
            } catch (e: ParseException) {
                throw JsonSyntaxException(s, e)
            }
        }
    }

}