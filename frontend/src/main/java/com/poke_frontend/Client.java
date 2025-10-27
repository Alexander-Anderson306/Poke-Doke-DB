package com.poke_frontend;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

public class Client {
    /**
     * Send a request to the server and return the response.
     * 
     * 
     * @param request the request to send to the server
     * @return the response from the server
     * @throws Exception if there is an issue with loading the certificate or sending the request
     */
    public static String sendRequest(String request) throws Exception {
        //load the public key
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert = null;
        try {
            InputStream certFile = new FileInputStream("../../../resources/server.cer");
            cert = (X509Certificate) cf.generateCertificate(certFile);
            certFile.close();
        } catch (FileNotFoundException e) {
            throw new Exception("Failed to load certificate, certificate may not exist", e);
        } catch (CertificateException e) {
            throw new Exception("Failed to load certificate", e);
        }

        //truststore with certificate (holds server public key)
        KeyStore trustStore = KeyStore.getInstance("JKS");
        trustStore.load(null, null);
        trustStore.setCertificateEntry("server", cert);

        //set up the SSL context
        TrustManagerFactory tmf = TrustManagerFactory.getInstance("TLS");
        tmf.init(trustStore);
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, tmf.getTrustManagers(), null);

        //now create socket factory and connect
        SSLSocketFactory ssf = sc.getSocketFactory();
        try (SSLSocket clientSocket = (SSLSocket) ssf.createSocket("localhost", 5001)) {
            //in and out for reading and writing
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            //send request (temporary for testing)
            if (request == null) {
                out.write("GET / HTTP/1.1\r\n");
                out.write("Host: " + "localhost" + "\r\n");
                out.write("Connection: close\r\n");
                out.write("\r\n");
            } else {
                out.write("POST / HTTP/1.1\r\n");
                out.write("Host: " + "localhost" + "\r\n");
                out.write("Content-Type: application/json\r\n");
                out.write("Content-Length: " + request.length() + "\r\n");
                out.write("Connection: close\r\n");
                out.write("\r\n");
                out.write(request);
            }

            out.flush();

            //read the response from the server
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line).append("\n");
            }

            return response.toString();
        }
    }
}