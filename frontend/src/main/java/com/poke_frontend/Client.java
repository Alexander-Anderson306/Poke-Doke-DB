package com.poke_frontend;
import javax.net.ssl.*;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.net.http.HttpClient;
import java.security.cert.CertificateException;

public class Client {
    private HttpClient client;
    private String url = "https://changeme/";
    private String username;

    public Client(String username, String password) {
        this.username = username;
        //try to connect to login using username and password. Will implement later
        //throw an exception if login fails

        this.client = createSecureSclient();
    }

    /**
     * Creates an HTTPS client using the truststore located at truststore.p12
     * 
     * @return an HTTPS client
     * @throws RuntimeException if the truststore cannot be loaded or the HTTPS client cannot be created
     */
    private HttpClient createSecureSclient() {
        try {
            //first we load the truststore
            KeyStore trustStore = KeyStore.getInstance("PKCS12");
            trustStore.load(new FileInputStream("truststore.p12"), "password".toCharArray());

            //create a trust manager factory using the loaded truststore
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);

            return HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Failed to set up HTTPS client", e);
        }
    }
}