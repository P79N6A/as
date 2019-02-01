package com.yaoguang.lib.common;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by baixiaokang on 16/5/6.
 */
public class RxSchedulers {
    public static final FlowableTransformer<?, ?> mTransformer
            = new FlowableTransformer<Object, Object>() {

        @Override
        public Publisher<Object> apply(Flowable<Object> upstream) {
            return upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    public static final ObservableTransformer<?, ?> mObservableTransformer
            = new ObservableTransformer<Object, Object>() {

        @Override
        public ObservableSource<Object> apply(Observable<Object> upstream) {
            return upstream
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    @SuppressWarnings("unchecked")
    public static <T> FlowableTransformer<T, T> io_main() {
        return (FlowableTransformer<T, T>) mTransformer;
    }

    public static <T> ObservableTransformer<T, T> io_ObservableMain() {
        return (ObservableTransformer<T, T>) mObservableTransformer;
    }

}
