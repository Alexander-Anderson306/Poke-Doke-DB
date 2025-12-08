package com.poke_backend;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;
import io.javalin.community.ssl.*;

import org.conscrypt.OpenSSLProvider;
import java.sql.SQLException;

import com.poke_backend.dto.request.*;
import com.poke_backend.dto.response.*;
import com.poke_backend.models.*;

import com.poke_backend.models.CardTypeQuant;
import java.security.Security;

import java.util.List;

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
            CreateAccountRequest req = ctx.bodyAsClass(CreateAccountRequest.class);
            IO.println("Received create account request for username: " + req.username);

            //try adding the user
            try {
                SQLHandler sqlHandler = new SQLHandler();
                sqlHandler.createAccount(req);
                //send response back to client
                ctx.json(new BaseResponse(true, "Successfully created account"));
            } catch (SQLException e) {
                //error response to client
                ctx.json(new BaseResponse(false, "Failed to create account", 500));
            }
        });

        //rout for logging in
        app.post("/login", ctx -> {
            LoginRequest req = ctx.bodyAsClass(LoginRequest.class);
            IO.println("Received login request for username: " + req.username);

            try{
                SQLHandler sqlHandler = new SQLHandler();
                User user = sqlHandler.login(req);
                if(user == null) {
                    ctx.status(401).json(new BaseResponse(false, "Failed to login", 401));
                } else {
                    ctx.json(new LoginResponse(user.getId()));
                }
            } catch (SQLException e) {
                ctx.status(401).json(new BaseResponse(false, "Failed to find user", 401));
            }

        });

        //rout for logging out
        app.post("/logout", ctx -> {
            LogoutRequest req = ctx.bodyAsClass(LogoutRequest.class);

            //do stuff
        });

        //rout for inventory search
        app.post("/inventory", ctx -> {
            InventoryRequest req = ctx.bodyAsClass(InventoryRequest.class);
            IO.println("Received inventory request for user id: " + req.userId);
            try {
                SQLHandler sqlHandler = new SQLHandler();
                List<CardTypeQuant> inventory = sqlHandler.getUserInventory(req);
                ctx.json(new InventoryResponse(inventory));
            } catch (SQLException e) {
                ctx.status(500).json(new BaseResponse(false, "Failed to find inventory", 500));
            }
        });

        //rout for database card search
        app.post("/cards", ctx -> {
            AllCardsRequest req = ctx.bodyAsClass(AllCardsRequest.class);
            IO.println("Received all cards request");

            try {
                SQLHandler sqlHandler = new SQLHandler();
                List<CardTypeQuant> cards = sqlHandler.getCards(req);
                ctx.json(new AllCardsResponse(cards));
            } catch (SQLException e) {
                ctx.status(500).json(new BaseResponse(false, "Failed to find cards", 500));
            }
        });

        //rout for pack search
        app.post("/store", ctx -> {
            PackRequest req = ctx.bodyAsClass(PackRequest.class);
            IO.println("Received store request");

            try {
                SQLHandler sqlHandler = new SQLHandler();
                List<CardPack> packs = sqlHandler.getPacks(req);
                ctx.json(new PackResponse(packs));
            } catch (SQLException e) {
                ctx.status(500).json(new BaseResponse(false, "Failed to find packs", 500));
            }
            
        });

        //rout for buying a pack
        app.post("/purchase", ctx -> {
            PackPurchaseRequest req = ctx.bodyAsClass(PackPurchaseRequest.class);
            IO.println("Received purchase request for user id: " + req.userId + " pack id: " + req.packId);

            Object userLock = UserLockManager.getLock(req.userId);
            synchronized (userLock) {
                try {
                    SQLHandler sqlHandler = new SQLHandler();
                    List<CardTypeQuant> cards = sqlHandler.purchasePack(req);
                    ctx.json(new PackPurchaseResponse(cards));
                } catch (SQLException e) {
                    ctx.status(500).json(new BaseResponse(false, "Failed to find packs", 500));
                }
            }
        });

    }

    void main() {
        Javalin app = createNGrokCompatableServer();
        registerRoutes(app);
    }
}