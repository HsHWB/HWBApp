package com.huehn.initword.core.net.download;

public class HttpConfig {


    public enum HttpURLType {
        POST("POST", 1),
        GET("GET", 2);

        private String name;
        private int id;

        HttpURLType(String name, int id) {
            this.name = name;
            this.id = id;
        }

        public static String getName(int index) {
            for (HttpURLType httpURLType : HttpURLType.values()) {
                if (httpURLType.getId() == index) {
                    return httpURLType.name;
                }
            }
            return null;
        }

        public int getId() {
            return id;
        }
    }
}
