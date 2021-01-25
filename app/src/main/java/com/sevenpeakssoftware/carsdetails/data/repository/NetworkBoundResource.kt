package com.sevenpeakssoftware.carsdetails.data.repository

import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

abstract class NetworkBoundResource<DatabaseType, NetworkType, RepositoryType>(private val remoteScheduler: Scheduler, private val databaseScheduler: Scheduler) {

    abstract fun createCall(): Single<NetworkType>

    abstract fun loadFromDB(): Flowable<DatabaseType>

    abstract fun saveCallResult(data: DatabaseType)

    abstract fun remoteToDatabaseMapper(): io.reactivex.functions.Function<NetworkType, DatabaseType>

    abstract fun databaseToRepositoryMapper(): io.reactivex.functions.Function<DatabaseType, RepositoryType>

    fun toFlowable(): Flowable<Resource<RepositoryType>> {
        return Flowable.create<Resource<RepositoryType>>({ emitter: FlowableEmitter<Resource<RepositoryType>> ->
            val disposable = CompositeDisposable()
            emitter.setDisposable(disposable)
            emitter.onNext(Resource.loading())
            val loadingDisposable = loadFromDB()
                .map(databaseToRepositoryMapper())
                .map { Resource.loading(it) }
                .subscribe { emitter.onNext(it) }
            disposable.add(loadingDisposable)
            createCall()
                .subscribeOn(remoteScheduler)
                .map(remoteToDatabaseMapper())
                .observeOn(databaseScheduler)
                .doOnEvent { _, _ -> loadingDisposable.dispose() }
                .doOnSuccess { saveCallResult(it) }
                .flatMapPublisher {
                    loadFromDB()
                        .map(databaseToRepositoryMapper())
                        .map { Resource.success(it) }
                }
                .onErrorResumeNext { e: Throwable ->
                    loadFromDB()
                        .map(databaseToRepositoryMapper())
                        .map { Resource.error(e, it) }
                }.subscribe { emitter.onNext(it) }
                .addTo(disposable)
        }, BackpressureStrategy.LATEST)
    }

}