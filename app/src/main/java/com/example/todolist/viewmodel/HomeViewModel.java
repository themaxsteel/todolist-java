package com.example.todolist.viewmodel;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.todolist.db.TaskDatabase;
import com.example.todolist.model.TaskResponse;
import com.example.todolist.services.ApiRepository;
import com.example.todolist.utils.ResponseDataListener;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class HomeViewModel extends AndroidViewModel {

    private ApiRepository apiRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private MutableLiveData<List<TaskResponse>> liveData;
    private MutableLiveData<TaskResponse> onSuccess;
    private TaskDatabase db = TaskDatabase.getInstance(getApplication().getApplicationContext());

    @ViewModelInject
    public HomeViewModel(@NonNull Application application, ApiRepository apiRepository) {
        super(application);
        this.apiRepository = apiRepository;
        liveData = new MutableLiveData<>();
        onSuccess = new MutableLiveData<>();
    }

    public MutableLiveData<List<TaskResponse>> getLiveData() {
        return liveData;
    }

    public MutableLiveData<TaskResponse> getOnSuccess() {
        return onSuccess;
    }

    public void getAllTask() {
        apiRepository.getAllTask(new ResponseDataListener<List<TaskResponse>>() {
            @Override
            public void doOnSubscribe(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(List<TaskResponse> data) {
                Log.e("TAG", "onSuccess : " + data.size());
                if (data.size() == 0 || db.taskDao().getAllTask().size() > data.size()) {
                    db.taskDao().deleteAllTask();
                    for (TaskResponse tr : data) {
                        db.taskDao().insertTask(tr);
                    }
                } else {
                    for (TaskResponse tr : data) {
                        db.taskDao().insertTask(tr);
                    }
                }

                liveData.postValue(db.taskDao().getAllTask());
            }

            @Override
            public void onError(Throwable throwable) {
                liveData.postValue(db.taskDao().getAllTask());
            }
        });
    }

    public void searchTask(String search) {
        apiRepository.searchTask(search, new ResponseDataListener<List<TaskResponse>>() {
            @Override
            public void doOnSubscribe(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(List<TaskResponse> data) {
                liveData.postValue(data);
            }

            @Override
            public void onError(Throwable throwable) {
                liveData.postValue(null);
            }
        });
    }

    public void postTask(TaskResponse taskResponse) {
        apiRepository.postTask(taskResponse, new ResponseDataListener<TaskResponse>() {

            @Override
            public void doOnSubscribe(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(TaskResponse data) {
                onSuccess.postValue(data);
                db.taskDao().insertTask(data);
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    public void updateTask(int id, TaskResponse taskResponse) {
        apiRepository.updateTask(id, taskResponse, new ResponseDataListener<TaskResponse>() {
            @Override
            public void doOnSubscribe(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(TaskResponse data) {
                onSuccess.postValue(data);
                db.taskDao().updateTask(data);
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    public void deleteTask(int id) {
        apiRepository.deleteTask(id, new ResponseDataListener<TaskResponse>() {
            @Override
            public void doOnSubscribe(Disposable disposable) {
                compositeDisposable.add(disposable);
            }

            @Override
            public void onSuccess(TaskResponse data) {
                onSuccess.postValue(data);
                db.taskDao().deleteTask(data);
            }

            @Override
            public void onError(Throwable throwable) {

            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
