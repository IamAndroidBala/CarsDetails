package com.sevenpeakssoftware.carsdetails.data.network.api.exception

import com.sevenpeakssoftware.carsdetails.data.network.api.model.ResponseStatus

class ApiException(status: ResponseStatus? = null, msg: String? = null) : RuntimeException(msg ?: status?.name)