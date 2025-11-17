package com.poke_backend;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.json.JavalinJackson;
import io.javalin.community.ssl.*;
import org.conscrypt.OpenSSLProvider;
import java.security.Security;
import java.util.Map;

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
        Security.addProvider(new OpenSSLProvider());
        Javalin app = Javalin.create(conf -> {
            conf.registerPlugin(new SslPlugin(ssl -> {
                //certificate
                ssl.pemFromPath("/app/src/main/resources/cert.pem", "/app/src/main/resources/key.pem" , "password");
                //connection settings
                ssl.host = "localhost";
                ssl.insecure = false;
                ssl.secure = true;
                ssl.securePort = 8080;
            }));
        }).start();
        return app;
    }

/**
 * Creates a Javalin server which is to be used with ngrok.
 * 
 * This method sets up the server to listen on localhost at port 8080 and
 * allows any host to connect.
 * 
 * @return the Javalin server
 */
    public static Javalin createNGrokCompatableServer() {
        return Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(it -> {
                    it.anyHost();
                });
            });

            config.jsonMapper(new JavalinJackson());
        }).start(8080);
    }

    private static void registerRoutes(Javalin app) {
        //rout for creating an account
        app.post("/create-account", ctx -> {
            //CreateAccountRequest request = ctx.body().asClass(CreateAccountRequest.class);
        });
        //rout for logging in
        app.post("/login", ctx -> {

        });

        //rout for logging out
         app.post("/logout", ctx -> {

        });

        //rout for inventory search
        app.post("/inventory", ctx -> {

        });

        //rout for pack search
        app.post("/store", ctx -> {

        });

        //rout for buying a pack
        app.post("/purchase", ctx -> {

        });

    }

    void main() {
        Javalin app = createNGrokCompatableServer();
    }
}