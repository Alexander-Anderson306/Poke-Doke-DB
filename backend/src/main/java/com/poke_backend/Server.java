package com.poke_backend;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.concurrent.*;

//TODO change this to use Javalin API

public class Server {
    private static int port = 5001;

    /**
     * Key starting point for the server. This method sets up the SSL context
     * for the server, which includes loading the keystore and getting the
     * password from the admin.
     *
     * @return the SSLContext for the server
     * @throws Exception if there is an issue with loading the keystore
     */
    private static SSLContext keyStart() throws Exception {
        //getting acess to the keystore file for certificate authentication
        String keystorePath = "../../resources/serverkeystore.jks";

        Console console = System.console();

        if (console == null) {
            throw new Exception("No console");
        }

        char[] passwordChars = console.readPassword("Enter the password for the keystore file: ");

        KeyStore ks = KeyStore.getInstance("JKS");
        try {
            ks.load(new FileInputStream(keystorePath), passwordChars);
        } catch (IOException e) {
            throw new Exception("Failed to load keystore, check password", e);
        } catch (CertificateException e) {
            throw new Exception("Failed to load keystore, keystore may not exist", e);
        }

        //key manager handles the key within keystore
        KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
        kmf.init(ks, passwordChars);

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(kmf.getKeyManagers(), null, null);

        //the sc is our sever context (it contains our cert stuff
        //making sure our http is https 😎)
        return sc;
    }

    private static void serverStart(SSLContext sc) {
        try {
            //open our secure socket here
            SSLServerSocketFactory ssf = sc.getServerSocketFactory();
            SSLServerSocket serverSocket = (SSLServerSocket) ssf.createServerSocket(port);

            //we use a thread pool with one thread per core to handle traffic
            int cores = Runtime.getRuntime().availableProcessors();
            ExecutorService pool = Executors.newWorkStealingPool(cores);

            while (true) {
                SSLSocket clientSocket = (SSLSocket) serverSocket.accept();
                pool.submit(() -> handleClient(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void handleClient(SSLSocket clientSocket) {
        try (clientSocket) {
            //in and out for reading and writing
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            //read the request from the client
            String line;
            while ((line = in.readLine()) != null && !line.isEmpty()) {
                System.out.println(line); // log request headers
            }

            out.write("HTTP/1.1 200 OK\r\n");
            out.write("Content-Type: text/plain\r\n");
            out.write("Connection: close\r\n");
            out.write("\r\n");
            out.write("Hello from Java HTTPS server!\n");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            SSLContext sc = keyStart();
            serverStart(sc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}