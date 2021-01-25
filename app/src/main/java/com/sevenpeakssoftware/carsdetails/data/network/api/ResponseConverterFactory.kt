package com.sevenpeakssoftware.carsdetails.data.network.api

import com.google.gson.Gson
import com.sevenpeakssoftware.carsdetails.data.network.api.exception.ApiException
import com.sevenpeakssoftware.carsdetails.data.network.api.model.Response
import com.sevenpeakssoftware.carsdetails.data.network.api.model.ResponseStatus
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

@SuppressWarnings("unchecked")
class CarsWebserviceResponseConverterFactory(gson: Gson) : Converter.Factory() {

    private val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(gson)

    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>, retrofit: Retrofit): Converter<ResponseBody, *> {
        val wrappedType = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
            override fun getOwnerType(): Type? = null
            override fun getRawType(): Type = Response::class.java
        }
        val gsonConverter: Converter<ResponseBody, *>? = gsonConverterFactory.responseBodyConverter(wrappedType, annotations, retrofit)
        return ResponseConverter(gsonConverter as Converter<ResponseBody, Response<Any>>)
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<Annotation>, methodAnnotations: Array<Annotation>, retrofit: Retrofit): Converter<*, RequestBody>? {
        return gsonConverterFactory.requestBodyConverter(type!!, parameterAnnotations, methodAnnotations, retrofit)
    }
}

private class ResponseConverter<T>(private val converter: Converter<ResponseBody, Response<T>>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(responseBody: ResponseBody): T {
        val response = converter.convert(responseBody)
        return response?.let {
            if (response.status == ResponseStatus.SUCCESS)
                response.content else throw ApiException(response.status)
        } ?: throw ApiException(msg = "can't convert ResponseBody to Response")
    }
}


