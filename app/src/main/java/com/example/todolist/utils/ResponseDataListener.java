package com.example.todolist.utils;

import io.reactivex.disposables.Disposable;

public interface ResponseDataListener<T> {
    void doOnSubscribe(Disposable disposable);

    void onSuccess(T data);

    void onError(Throwable throwable);
}
