package com.xmarcusv.moviex.repository

import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import com.xmarcusv.moviex.model.Resource
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

abstract class RepositoryProcessor<RequestType> {

    protected abstract fun createCall(): Single<RequestType>

    fun execute(): Flowable<Resource<RequestType>> {
        return Maybe.merge(getLoading(), getMappedSource())
                .onErrorReturn { t: Throwable ->
                    Resource.error(t.message, null)
                }
    }

    @WorkerThread
    protected abstract fun loadFromDb(): Maybe<RequestType>

    @WorkerThread
    protected abstract fun saveCallResult(item: RequestType)

    @MainThread
    protected abstract fun shouldFetch(data: RequestType?): Boolean

    open fun onFetchFailed() {}

    private fun fetchNetworkAndSaveTheResponse(): Maybe<RequestType> {
        return createCall()
                .toMaybe()
                .doOnError { onFetchFailed() }
                .doAfterSuccess { data -> saveCallResult(data) }
    }

    private fun loadCacheIfIsStillValid(): Maybe<RequestType> {
        return loadFromDb()
                .filter { data -> !shouldFetch(data) }
    }

    private fun getSources(): Maybe<RequestType> {
        return Maybe
                .concat(loadCacheIfIsStillValid(), fetchNetworkAndSaveTheResponse())
                .firstElement()
    }

    private fun getMappedSource(): Maybe<Resource<RequestType>> {
        return getSources()
                .map { t -> Resource.success(t) }
    }

    private fun getLoading(): Maybe<Resource<RequestType>> {
        return Maybe.just(Resource.loading())
    }
}