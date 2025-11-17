package com.poke_backend;

import org.mindrot.jbcrypt.BCrypt;

public class Hash {
    public static String hashPassword(String password) {
        //Generate a salt
        String salt = BCrypt.gensalt();

        //Hash the password with the salt
        String hashedPassword = BCrypt.hashpw(password, salt);

        return hashedPassword;
    }

    public static boolean checkPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}