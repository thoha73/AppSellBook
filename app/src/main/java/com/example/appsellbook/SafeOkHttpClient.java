package com.example.appsellbook;

import okhttp3.OkHttpClient;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.security.KeyStore;
import javax.net.ssl.TrustManagerFactory;
import java.security.NoSuchAlgorithmException;
import java.security.KeyManagementException;
import android.content.Context;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

public class SafeOkHttpClient {

    public static OkHttpClient getSafeOkHttpClient(Context context) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        try {
            // Đọc chứng chỉ từ file raw resources
            InputStream caInput = context.getResources().openRawResource(R.raw.rg4x9su); // Thay đổi tên chứng chỉ ở đây

            // Tạo CertificateFactory để đọc chứng chỉ
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            Certificate ca = cf.generateCertificate(caInput);

            // Đảm bảo đóng InputStream
            caInput.close();

            // Tạo KeyStore và thêm chứng chỉ vào
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null); // load keystore mặc định
            keyStore.setCertificateEntry("ca", ca);

            // Tạo TrustManagerFactory và cài đặt chứng chỉ
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            // Khởi tạo SSLContext với TrustManager đã cài đặt
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new java.security.SecureRandom());

            // Trả về OkHttpClient với SSLContext và TrustManager
            return new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagerFactory.getTrustManagers()[0])
                    .hostnameVerifier((hostname, session) -> true) // Bỏ qua kiểm tra hostname (nếu cần)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Error creating Safe OkHttpClient", e);
        }
    }
}
