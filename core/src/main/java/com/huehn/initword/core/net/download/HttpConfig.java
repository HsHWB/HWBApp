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

    /**
     * httpURLConnection.setRequestProperty
     * 设置传送类型
     */
    public enum HttpURLRequestProperty{

        //设定传送的内容类型是文件
        File("application/octet-stream", 1),
        Apk("application/vnd.android.package-archive", 2),
        // 设定传送的内容类型是可序列化的java对象(如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能
        // 抛java.io.EOFException)
        Object("application/x-java-serialized-object", 3);

        private String type;
        private int id;

        HttpURLRequestProperty(String type, int id) {
            this.type = type;
            this.id = id;
        }

        public String getType(int id) {
            for (HttpURLRequestProperty httpURLRequestProperty : HttpURLRequestProperty.values()){
                if (httpURLRequestProperty.getId() == id){
                    return httpURLRequestProperty.type;
                }
            }
            return null;
        }

        public int getId() {
            return id;
        }
    }
}
