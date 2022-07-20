package com.example.todolist.services.impl;

import com.example.todolist.model.TaskResponse;
import com.example.todolist.services.ApiRepository;
import com.example.todolist.services.ApiService;
import com.example.todolist.utils.ResponseDataListener;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ApiServiceImpl implements ApiRepository {
    private ApiService apiService;
    private CompositeDisposable compositeDisposable;

    public ApiServiceImpl(ApiService apiService, CompositeDisposable compositeDisposable) {
        this.apiService = apiService;
        this.compositeDisposable = compositeDisposable;
    }


    @Override
    public void getAllTask(ResponseDataListener<List<TaskResponse>> listener) {
        Disposable disposable = apiService.getAllTask()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> {
                    if (listener != null)
                        listener.doOnSubscribe(d);
                })
                .subscribe(data -> {
                    if (listener != null)
                        listener.onSuccess((List<TaskResponse>) data);
                }, throwable -> {
                    if(listener!=null)
                        listener.onError(throwable);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void searchTask(String search, ResponseDataListener<List<TaskResponse>> listener) {
        Disposable disposable = apiService.searchTask(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> {
                    if (listener != null)
                        listener.doOnSubscribe(d);
                })
                .subscribe(data -> {
                    if (listener != null)
                        listener.onSuccess(data);
                }, throwable -> {
                    if(listener!=null)
                        listener.onError(throwable);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void postTask(TaskResponse taskResponse, ResponseDataListener<TaskResponse> listener) {
        Disposable disposable = apiService.postTask(taskResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> {
                    if (listener != null)
                        listener.doOnSubscribe(d);
                })
                .subscribe(data -> {
                    if (listener != null)
                        listener.onSuccess((TaskResponse) data);
                }, throwable -> {
                    if(listener!=null)
                        listener.onError(throwable);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void updateTask(int id, TaskResponse taskResponse, ResponseDataListener<TaskResponse> listener) {
        Disposable disposable = apiService.updateTask(id, taskResponse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> {
                    if (listener != null)
                        listener.doOnSubscribe(d);
                })
                .subscribe(data -> {
                    if (listener != null)
                        listener.onSuccess(data);
                }, throwable -> {
                    if(listener!=null)
                        listener.onError(throwable);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void deleteTask(int id, ResponseDataListener<TaskResponse> listener) {
        Disposable disposable = apiService.deleteTask(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> {
                    if (listener != null)
                        listener.doOnSubscribe(d);
                })
                .subscribe(data -> {
                    if (listener != null)
                        listener.onSuccess(data);
                }, throwable -> {
                    if(listener!=null)
                        listener.onError(throwable);
                });
        compositeDisposable.add(disposable);
    }
}
