package com.xmarcusv.moviex.repository

import com.xmarcusv.moviex.model.Resource
import io.reactivex.Flowable
import io.reactivex.processors.BehaviorProcessor

abstract class NetworkProcessor<RequestType> {

    private val publishProcessor = BehaviorProcessor.create<Resource<RequestType>>()

    init {
        publishProcessor.onNext(Resource.loading(null))
    }

    protected abstract fun createCall(): Flowable<RequestType>

    fun asFlowable(): Flowable<Resource<RequestType>> {
        createCall().map { t: RequestType -> Resource.success(t) }.subscribe(publishProcessor)
        return publishProcessor.onErrorReturn { t: Throwable -> Resource.error(t.message, null) }
    }
}