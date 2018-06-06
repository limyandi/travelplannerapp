package com.mad.madproject.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isUsernameValid(String username) {
        String expression = "[a-zA-Z0-9\\._\\-]{3,}";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }

}
