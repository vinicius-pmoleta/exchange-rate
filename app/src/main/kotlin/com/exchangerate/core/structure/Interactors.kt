package com.exchangerate.core.structure

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Interfaces for Interactors. This interfaces represent use cases (this means any use case in the application should implement this contract).
 */
interface ReactiveInteractor {

    /**
     * Sends changes to data layer.
     * It returns a [Single] that will emit the data of the send operation.
     *
     * @param <Result> the type of the send operation data.
     * @param <Params> required parameters for the send.
    </Params></Result> */
    interface SendInteractor<in Params, out Result> : ReactiveInteractor {

        fun getSingle(params: Params?): Single<out Result>
    }

    /**
     * Retrieves changes from the data layer.
     * It returns an [Flowable] that emits updates for the retrieved object. The returned [Flowable] will never complete, but it can
     * error if there are any problems performing the required actions to serve the data.
     *
     * @param <Object> the type of the retrieved object.
     * @param <Params> required parameters for the retrieve operation.
    </Params></Object> */
    interface RetrieveInteractor<in Params, out Object> : ReactiveInteractor {

        fun getBehaviorStream(params: Params?): Flowable<out Object>
    }

    /**
     * The request interactor is used to request some data once. The returned observable is a single, emits once and then completes or errors.
     *
     * @param <Params> the type of the returned data.
     * @param <Result> required parameters for the request.
    </Result></Params> */
    interface RequestInteractor<in Params, out Result> : ReactiveInteractor {

        fun getSingle(params: Params?): Single<out Result>
    }

    /**
     * The delete interactor is used to delete entities from data layer. The response for the delete operation comes as onNext
     * event in the returned observable.
     *
     * @param <Result> the type of the delete response.
     * @param <Params>   required parameters for the delete.
    </Params></Result> */
    interface DeleteInteractor<in Params, out Result> : ReactiveInteractor {

        fun getSingle(params: Params?): Single<out Result>
    }

    /**
     * The refresh interactor is used to refresh the reactive store with new data. Typically calling this interactor will trigger events in its
     * get interactor counterpart. The returned observable will complete when the refresh is finished or error if there was any problem in the process.
     *
     * @param <Params> required parameters for the refresh.
    </Params> */
    interface RefreshInteractor<in Params> : ReactiveInteractor {

        fun getRefreshSingle(params: Params?): Completable
    }

}

