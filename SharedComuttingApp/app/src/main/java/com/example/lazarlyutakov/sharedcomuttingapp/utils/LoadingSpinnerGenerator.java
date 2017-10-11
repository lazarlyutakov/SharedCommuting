package com.example.lazarlyutakov.sharedcomuttingapp.utils;

import android.app.Activity;
import android.graphics.Color;

import cc.cloudist.acplibrary.ACProgressConstant;
import cc.cloudist.acplibrary.ACProgressFlower;

/**
 * Created by Lazar Lyutakov on 11.10.2017 г..
 */

public class LoadingSpinnerGenerator {

    private ACProgressFlower dialog;

    public LoadingSpinnerGenerator(Activity activity){

        dialog = new ACProgressFlower.Builder(activity)
                .direction(ACProgressConstant.DIRECT_ANTI_CLOCKWISE)
                .themeColor(Color.GREEN)
                .fadeColor(Color.RED)
                .text("Loading..")
                .build();

        dialog.setCanceledOnTouchOutside(true);

    }

/*    public ACProgressFlower generateLoadingSpinner(Activity activity){
         dialog = new ACProgressFlower.Builder(activity)
                .direction(ACProgressConstant.DIRECT_ANTI_CLOCKWISE)
                .themeColor(Color.GREEN)
                .fadeColor(Color.RED)
                .text("Loading..")
                .build();

        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }*/

    public void show(){
        dialog.show();
    }

    public void hide(){
        dialog.hide();
    }

    public void cancel(){
        dialog.cancel();
    }
}
