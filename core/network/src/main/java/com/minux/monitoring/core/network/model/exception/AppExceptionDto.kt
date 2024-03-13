package com.minux.monitoring.core.network.model.exception

sealed class AppExceptionDto(description: String? = null) : Throwable(message = description) {
    data class BadRequestExceptionDto(val data: List<String>?) : AppExceptionDto()

    data object UnauthorizedExceptionDto : AppExceptionDto() {
        private fun readResolve(): Any = UnauthorizedExceptionDto
    }
}