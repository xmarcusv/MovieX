package com.xmarcusv.moviex.base

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

abstract class RepositoryProcessor<RequestType> {

    protected abstract fun createCall(): Single<RequestType>

    fun execute(): Flowable<Resource<RequestType>> {
        return Maybe.merge(getLoading(), getSources())
                .onErrorReturn { t: Throwable ->
                    Resource.error(t.message, null)
                }
    }

    protected abstract fun loadFromDb(): Maybe<RequestType>

    protected abstract fun saveCallResult(item: RequestType)

    protected abstract fun shouldFetch(data: RequestType?): Boolean

    open fun onFetchFailed() {}

    private fun fetchNetworkAndSaveTheResponse(): Maybe<RequestType> {
        return createCall()
                .toMaybe()
                .doOnError { onFetchFailed() }
                .doAfterSuccess { data -> saveCallResult(data) }
    }

    private fun loadCacheIfShould(): Maybe<RequestType> {
        return loadFromDb()
                .filter { data -> !shouldFetch(data) }
    }

    private fun getSources(): Maybe<Resource<RequestType>> {
        return Maybe
                .concat(loadCacheIfShould(), fetchNetworkAndSaveTheResponse())
                .firstElement()
                .map { t -> Resource.success(t) }
    }

    private fun getLoading(): Maybe<Resource<RequestType>> {
        return Maybe.just(Resource.loading())
    }
}