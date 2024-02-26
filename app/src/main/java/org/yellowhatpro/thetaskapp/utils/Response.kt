package org.yellowhatpro.thetaskapp.utils

class Response<T>(val status: Status, val data: T?) {

    enum class Status {
        LOADING, FAILED, SUCCESS
    }

    companion object {
        fun <S> failure(): Response<S> {
            return Response(Status.FAILED, null)
        }

        fun <S> loading(): Response<S> {
            return Response(Status.LOADING, null)
        }

        fun <S> success(data: S): Response<S> {
            return Response(Status.SUCCESS, data)
        }
    }
}