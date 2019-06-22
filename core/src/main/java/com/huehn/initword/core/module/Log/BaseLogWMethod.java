package com.huehn.initword.core.module.Log;

import com.huehn.initword.core.utils.File.FileUtils;
import com.huehn.initword.core.utils.Log.BaseLogImpl;
import com.huehn.initword.core.utils.Log.LogManager;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 对于要写文件的话，则要多开辟一个接口，这个接口存在是为了ILogMethod不显得臃肿。
 * 如果文件的write接口也卸载ILogMethod的话，那么普通的log打印也要继承这个写文件的接口。
 *
 * 只要写log到本地储存相关的都继承这个类，外部实现只需要返回路径就好
 */
public abstract class BaseLogWMethod extends BaseLogImpl {
    private Disposable disposable;

    @Override
    public void write(int level, Class stackTraceClazz, String tag, Object object) {
        writeLog(object);
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
//                LogManager.d("huehn writeToFile : " + stringBuilder.toString());
                return Observable.create(new ObservableOnSubscribe<StringBuilder>() {
                    @Override
                    public void subscribe(ObservableEmitter<StringBuilder> emitter) throws Exception {
                        emitter.onNext(stringBuilder);
                        FileUtils.saveFile(getFilePath(), stringBuilder, true);
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
    @Override
    protected StringBuilder writeLog(Object object) {
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
     * 返回文件路径
     * @return
     */
    protected abstract String getFilePath();

    /**
     * 通过文件id来筛选文件名
     * @param type
     * @return
     */
    protected String getFilePathById(int type){
        String defaultPath = "huehn_logfile.txt";
        switch (type){
            case LogManager.DEFAULT_EXCEPTION_LOG_FILE_LOCAL:
                break;
            case LogManager.APP_EXCEPTION_LOG_FILE_LOCAL:
                defaultPath = "huehn_applogfile.txt";
                break;
            case LogManager.CORE_EXCEPTION_LOG_FILE_LOCAL:
                defaultPath = "huehn_corelogfile.txt";
                break;
        }
        return defaultPath;
    }

}
