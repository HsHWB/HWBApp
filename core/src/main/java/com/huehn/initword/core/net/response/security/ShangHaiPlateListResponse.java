package com.huehn.initword.core.net.response.security;

import java.util.List;

public class ShangHaiPlateListResponse {


    /**
     * showapi_res_error :
     * showapi_res_id : 73e324d9a6d24763bfd3403224881722
     * showapi_res_code : 0
     * showapi_res_body : {"ret_code":0,"list":
     * [{"name":"申万行业","childList":[{"name":"通信","code":"sw_tx"},{"name":"传媒","code":"sw_cm"},
     * {"name":"计算机","code":"sw_jsj"},{"name":"国防军工","code":"sw_gfjg"},{"name":"机械设备","code":"sw_jxsb"}],"code":""}
     * ,{"name":"创业板","childList":[],"code":"cyb"}]}
     */

//    private String showapi_res_error;
//    private String showapi_res_id;
//    private int showapi_res_code;
    private ShowapiResBodyBean showapi_res_body;

//    public String getShowapi_res_error() {
//        return showapi_res_error;
//    }
//
//    public void setShowapi_res_error(String showapi_res_error) {
//        this.showapi_res_error = showapi_res_error;
//    }
//
//    public String getShowapi_res_id() {
//        return showapi_res_id;
//    }
//
//    public void setShowapi_res_id(String showapi_res_id) {
//        this.showapi_res_id = showapi_res_id;
//    }
//
//    public int getShowapi_res_code() {
//        return showapi_res_code;
//    }
//
//    public void setShowapi_res_code(int showapi_res_code) {
//        this.showapi_res_code = showapi_res_code;
//    }

    public ShowapiResBodyBean getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(ShowapiResBodyBean showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class ShowapiResBodyBean {
        /**
         * ret_code : 0
         * list : [{"name":"申万行业","childList":[{"name":"通信","code":"sw_tx"},{"name":"传媒","code":"sw_cm"},
         * {"name":"综合类","childList":[],"code":"hangye_ZM"}],"code":""},{"name":"创业板","childList":[],"code":"cyb"}]
         */

        private int ret_code;
        private List<ListBean> list;

        public int getRet_code() {
            return ret_code;
        }

        public void setRet_code(int ret_code) {
            this.ret_code = ret_code;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * name : 申万行业
             * childList : [{"name":"通信","code":"sw_tx"},{"name":"传媒","code":"sw_cm"},{"name":"计算机","code":"sw_jsj"}
             * ,{"name":"采掘","code":"sw_cj"},{"name":"农林牧渔","code":"sw_nlmy"},{"name":"综合","code":"sw_zh"}]
             * code :
             */

            private String name;
            private String code;
            private List<ChildListBean> childList;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public List<ChildListBean> getChildList() {
                return childList;
            }

            public void setChildList(List<ChildListBean> childList) {
                this.childList = childList;
            }

            public static class ChildListBean {
                /**
                 * name : 通信
                 * code : sw_tx
                 */

                private String name;
                private String code;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }
            }
        }
    }
}
