package com.lsqidsd.hodgepodge.http;

import io.reactivex.ObservableTransformer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;

public interface BaseSchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();

    @NonNull
    <T> ObservableTransformer<T, T> applySchedulers();
}
