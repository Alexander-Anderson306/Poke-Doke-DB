package com.poke_backend;

import javax.net.ssl.*;
import java.io.*;
import java.security.*;

public class Server {
    private static int port = 5001;

    private static void start() throws Exception {
        SSLServerSocket serverSocket = new SSLServerSocket(port);
        serverSocket.setNeedClientAuth(false);
        IO.println("Server started on port " + port);
    }
}