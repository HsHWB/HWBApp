package com.huehn.initword.core.net.download;

import io.reactivex.functions.Consumer;

public class FileDownLoad<T> {

    private String url;
    private T t;
    private Consumer<T> onSuccessListener;
    private Consumer<T> onErrorListener;

    private FileDownLoad(Builder builder){
        if (builder != null){
            this.url = builder.url;
            this.onSuccessListener = builder.onSuccessListener;
            this.onErrorListener = builder.onErrorListener;
        }
    }




    public class Builder<T>{
        private String url;
        private Consumer<T> onSuccessListener;
        private Consumer<T> onErrorListener;

        public Builder() {
        }

        public Builder<T> setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder<T> setOnSuccessListener(Consumer<T> onSuccessListener) {
            this.onSuccessListener = onSuccessListener;
            return this;
        }

        public Builder<T> setOnErrorListener(Consumer<T> onErrorListener) {
            this.onErrorListener = onErrorListener;
            return this;
        }

        public FileDownLoad<T> build(){
            return new FileDownLoad<>(this);
        }
    }
}
