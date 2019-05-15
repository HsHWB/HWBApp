package com.huehn.initword.core.net;

import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * okhttp设置https证书问题
 */

public class RequestTrustX509Manager implements X509TrustManager {
    //证书中提取相关值
    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }
    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String authType) throws CertificateException {

        if (x509Certificates == null) {
            throw new IllegalArgumentException("checkServerTrusted:x509Certificate array isnull");
        }
        if (!(x509Certificates.length > 0)) {
            throw new IllegalArgumentException("checkServerTrusted: X509Certificate is empty");
        }
        //ECDHE_ECDSA这个是facebook登录的https
        if (!(null != authType && (authType.equalsIgnoreCase("ECDHE_RSA")
                || authType.equalsIgnoreCase("RSA")
                || authType.equalsIgnoreCase("ECDHE_ECDSA")))) {
            throw new CertificateException("checkServerTrusted: AuthType is not RSA");
        }
        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("X509");
            tmf.init((KeyStore) null);
            for (TrustManager trustManager : tmf.getTrustManagers()) {
                ((X509TrustManager) trustManager).checkServerTrusted(x509Certificates, authType);
            }
        } catch (Exception e) {
            throw new CertificateException(e);
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
