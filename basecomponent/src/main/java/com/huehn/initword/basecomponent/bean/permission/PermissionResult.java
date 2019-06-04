package com.huehn.initword.basecomponent.bean.permission;

public class PermissionResult {

    private int requestCode = 0;
    private String[] permissionString = null;
    private boolean granted = false;
    private boolean shouldShowRequestPermissionRationale;

    public PermissionResult() {
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String[] getPermissionString() {
        return permissionString;
    }

    public void setPermissionString(String[] permissionString) {
        this.permissionString = permissionString;
    }

    public boolean isGranted() {
        return granted;
    }

    public void setGranted(boolean granted) {
        this.granted = granted;
    }

    public boolean isShouldShowRequestPermissionRationale() {
        return shouldShowRequestPermissionRationale;
    }

    public void setShouldShowRequestPermissionRationale(boolean shouldShowRequestPermissionRationale) {
        this.shouldShowRequestPermissionRationale = shouldShowRequestPermissionRationale;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }else if (obj != null && this.getClass() == obj.getClass()){
            PermissionResult permission = (PermissionResult) obj;
            if (this.granted != permission.granted){
                return false;
            }else {
                return this.shouldShowRequestPermissionRationale != permission.shouldShowRequestPermissionRationale ? false : true;
            }
        }else {
            return false;
        }
    }
}
