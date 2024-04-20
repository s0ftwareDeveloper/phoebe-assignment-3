package com.coderscampus.assignment3;

import java.util.Scanner;

public class UserLoginApplication
{

    public static void main(String[] args)
    {
        // service
        UserService service = new UserService();

        // login
        service.login();
    }
}
