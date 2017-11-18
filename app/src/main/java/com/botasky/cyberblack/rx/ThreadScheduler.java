package com.botasky.cyberblack.rx;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Description：
 * Created by Botasky on 2017/11/16.
 */

public class ThreadScheduler {

  private static final ObservableTransformer schedulersNewThread = scheduler ->
      scheduler
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread());

  private static final ObservableTransformer schedulersIoThread = scheduler ->
      scheduler
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());

  private static final ObservableTransformer schedulerCalThread = scheduler ->
      scheduler
          .subscribeOn(Schedulers.computation())
          .observeOn(AndroidSchedulers.mainThread());

  public static <T> ObservableTransformer<T, T> applyNewSchedulers() {
    return (ObservableTransformer<T, T>) schedulersNewThread;
  }

  public static <T> ObservableTransformer<T, T> applyIOSchedulers() {
    return (ObservableTransformer<T, T>) schedulersIoThread;
  }

  public static <T> ObservableTransformer<T, T> applyCalculaterSchedulers() {
    return (ObservableTransformer<T, T>) schedulerCalThread;
  }
}

