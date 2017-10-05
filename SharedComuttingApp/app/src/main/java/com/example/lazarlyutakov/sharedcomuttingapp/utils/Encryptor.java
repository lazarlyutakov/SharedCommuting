package com.example.lazarlyutakov.sharedcomuttingapp.utils;

import se.simbio.encryption.Encryption;

/**
 * Created by Lazar Lyutakov on 5.10.2017 г..
 */

public class Encryptor {
    public Encryptor(){

    }

    public String encrypt(String password){
        String key = "KriptiramParoli";
        String salt = "encryptionSalt";
        byte[] iv = new byte[16];
        Encryption encryption = Encryption.getDefault(key, salt, iv);

        String encrypted = encryption.encryptOrNull(password);

        return encrypted;
    }
}
