package com.huehn.initword.core.utils.Log;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.huehn.initword.core.utils.File.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LogWImpl extends BaseLogImpl {

    private Disposable disposable;

    @Override
    public StringBuilder writeLog(Object object) {
        StringBuilder stringBuilder = null;

        stringBuilder = new StringBuilder();

        if (object == null){
            return stringBuilder.append("null");
        }

        if (object instanceof String){
            String content = (String) object;
            stringBuilder.append(content);
        }else if (object instanceof List){
            listToString((List) object, stringBuilder);
        }else if (object instanceof Map){
            mapToString((Map) object, stringBuilder);
        }else {
            stringBuilder = objectToString(object);
        }
        writeToFile(stringBuilder);
        return stringBuilder;
    }

    /**
     * 把log写入文件
     * @param stringBuilder
     */
    private void writeToFile(final StringBuilder stringBuilder){
        disposable = Observable.create(new ObservableOnSubscribe<StringBuilder>() {
            @Override
            public void subscribe(ObservableEmitter<StringBuilder> emitter) throws Exception {
                    emitter.onNext(stringBuilder);
                }
            })
            .flatMap(new Function<StringBuilder, ObservableSource<StringBuilder>>() {
                @Override
                public ObservableSource<StringBuilder> apply(final StringBuilder stringBuilder) throws Exception {
                    LogManager.d("huehn writeToFile : " + stringBuilder.toString());
                    return Observable.create(new ObservableOnSubscribe<StringBuilder>() {
                        @Override
                        public void subscribe(ObservableEmitter<StringBuilder> emitter) throws Exception {
                            emitter.onNext(stringBuilder);
                            FileUtils.saveFile("huehn_file.txt", stringBuilder);
                        }
                    });
                }
            })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<StringBuilder>() {
                @Override
                public void accept(StringBuilder StringBuilder) throws Exception {
                    disposable.dispose();
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                    disposable.dispose();
                }
            });
    }



}
