package com.poke_backend;

import io.javalin.Javalin;
import io.javalin.community.ssl.*;

public class Server {
    /**
     * Creates a Javalin server with SSL enabled.
     *
     * This method loads the certificate and private key from the resources
     * directory, and sets up the server to listen on localhost at port 443.
     * 
     * @return the Javalin server
     */
    private static Javalin createServer() {

       Javalin app = Javalin.create(conf -> {
            conf.registerPlugin(new SslPlugin(ssl -> {
                ssl.pemFromPath("../../resources/cert.pem", "../../resources/key.pem");

                //connection settings
                ssl.host = "localhost";
                ssl.insecure = false;
                ssl.secure = true;
                ssl.securePort = 443;
            }));
        }).start();
        return app;
    }

    void main() {
        Javalin app = createServer();

        app.get("/", ctx -> ctx.result("Hello HTTPS"));
    }
}