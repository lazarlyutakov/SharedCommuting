package com.example.lazarlyutakov.sharedcomuttingapp.utils;

/**
 * Created by Lazar Lyutakov on 6.10.2017 г..
 */

public class SetTimeOut {

    public SetTimeOut(){

    }

    public static void setTimeout(final int  delay){
        new Thread(){
            public void run() {
                try {
                    Thread.sleep(delay);
                }
                catch (Exception e){
                    System.err.println(e);
                }
            }
        }.start();
    }
}

