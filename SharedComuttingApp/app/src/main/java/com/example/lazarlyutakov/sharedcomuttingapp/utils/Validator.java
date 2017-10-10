package com.example.lazarlyutakov.sharedcomuttingapp.utils;

import android.media.MediaCodec;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lazar Lyutakov on 5.10.2017 г..
 */

public class Validator {

    String regex = "[A-Za-z0-9]";
    Pattern pattern = Pattern.compile(regex);
   // private final Pattern pattern = Pattern.compile("^[~*/< >?!`]");
    private final Pattern patternNames = Pattern.compile("^a-zA-Z");

    private final String PASSWORD_NEEDED = "Please, enter a password!";
    private final String USERNAME_NEEDED = "Please, enter username!";
    private final String NAME_NEEDED = "Please, enter first / last name!";
    private final String PHONE_NUMBER_NEEDED = "Please, enter telephone number!";
    private final String SEARCH_RADIUS_NEEDED = "Please, enter radius of search!";
    private final String CONTACTNAME_NEEDED = "Please, enter contact name!";


    private final String PASSWORD_NOT_LONG_ENOUGH = "Password must be at least 6 chars long!";
    private final String USERNAME_NOT_LONG_ENOUGH = "Username must be at least 3 chars long!";
    private final String PHONE_NUMBER_NOT_LONG_ENOUGH = "Phone number must be at least 8 digits long!";

    private final String PASSWORD_CONTAINS_FORBIDDEN_CHARS = "Password contains forbidden chars";
    private final String USERNAME_CONTAINS_FORBIDDEN_CHARS = "Username contains forbidden chars";
    private final String PERSONAL_NAME_CONTAINS_FORBIDDEN_CHARS = "Your names must contain only latin characters!";

    public Validator(){

    }

    public boolean validateSearchDistance(TextView textView){
        Matcher matcher = pattern.matcher(textView.getText().toString());

        if(textView.getText().length() == 0 || textView.getText().toString() == null){
            textView.setError(SEARCH_RADIUS_NEEDED);
            return false;
        }
        return true;
    }

    public boolean validatePassword(TextView textView){
        Matcher matcher = pattern.matcher(textView.getText().toString());

        if(textView.getText().length() == 0 || textView.getText().toString() == null){
            textView.setError(PASSWORD_NEEDED);
            return false;
        }
        else if(textView.getText().length() < 6){
            textView.setError(PASSWORD_NOT_LONG_ENOUGH);
            return false;
        }
        /*else if(!matcher.matches()){
            System.out.println(matcher.matches());
            textView.setError(PASSWORD_CONTAINS_FORBIDDEN_CHARS);
            return false;
        }*/
        return true;
    }

    public boolean validateUsername(TextView textView){
        if(textView.getText().length() == 0 || textView.getText().toString() == null){
            textView.setError(USERNAME_NEEDED);
            return false;
        }
        else if(textView.getText().length() < 3){
            textView.setError(USERNAME_NOT_LONG_ENOUGH);
            return false;
        }
       /* else if(pattern.matcher(textView.getText().toString()).matches()){
            textView.setError(USERNAME_CONTAINS_FORBIDDEN_CHARS);
            return false;
        }*/
        return true;
    }

    public boolean validateContact(TextView textView){
        if(textView.getText().length() == 0 || textView.getText().toString() == null){
            textView.setError(CONTACTNAME_NEEDED);
            return false;
        }
        return true;
    }

    public boolean validatePersonalNames(TextView textView){
        if(textView.getText().length() == 0 || textView.getText().toString() == null){
            textView.setError(NAME_NEEDED);
            return false;
        }
        /*else if(!patternNames.matcher(textView.getText().toString()).matches()){
            textView.setError(PERSONAL_NAME_CONTAINS_FORBIDDEN_CHARS);
            return false;
        }*/
        return true;
    }

    public boolean validatePhoneNumber(TextView textView){
        if(textView.getText().length() == 0 || textView.getText().toString() == null){
            textView.setError(PHONE_NUMBER_NEEDED);
            return false;
        }
        else if(textView.getText().length() < 8){
            textView.setError(PHONE_NUMBER_NOT_LONG_ENOUGH);
            return false;
        }

        return true;
    }
}
