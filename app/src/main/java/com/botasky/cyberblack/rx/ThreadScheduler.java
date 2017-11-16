package com.botasky.cyberblack.rx;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Description：
 * Created by 苏良波 on 2017/11/16.
 */

public class ThreadScheduler {
  private static final Observable.Transformer schedulersTransformer = new Observable.Transformer() {
    @Override
    public Object call(Object observable) {
      return ((Observable) observable)
          .subscribeOn(Schedulers.newThread())
          .observeOn(AndroidSchedulers.mainThread());
    }
  };

  private static final Observable.Transformer scheduldersTransformerIO = new Observable.Transformer() {
    @Override
    public Object call(Object o) {
      return ((Observable)o)
          .subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread());
    }
  };

  public static final Observable.Transformer schedulersTransformerCal = new Observable.Transformer() {
    @Override
    public Object call(Object o) {
      return ((Observable)o)
          .subscribeOn(Schedulers.computation())
          .subscribeOn(AndroidSchedulers.mainThread());
    }
  };

  public static <T> Observable.Transformer<T, T> applySchedulers() {
    return (Observable.Transformer<T, T>) schedulersTransformer;
  }

  public static <T> Observable.Transformer<T,T> applyIOSchedulers(){
    return (Observable.Transformer<T,T>) scheduldersTransformerIO;
  }

  public static <T> Observable.Transformer<T,T> applyCalculaterSchedulers(){
    return (Observable.Transformer<T,T>) schedulersTransformerCal;
  }

}

  //static final Observable.Transformer schedulersTransformer = new Observable.Transformer() {
  //
  //  @Override
  //  public Object call(Object observable) {
  //    return ((Observable) observable).subscribeOn(ThreadScheduler.newThread())
  //        .observeOn(AndroidSchedulers.mainThread());
  //  }
  //};
  //
  //<T> Observable.Transformer<T, T> applySchedulers() {
  //  return (Observable.Transformer<T, T>) schedulersTransformer;
  //}