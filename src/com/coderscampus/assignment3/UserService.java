package com.coderscampus.assignment3;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class UserService
{
    private final String
            FILE_NOT_FOUND = "Error. File Not Found.",
            IO_EXCEPTION = "Error. I/O Exception.",
            ENTER_EMAIL = "Enter your email:",
            ENTER_PASS = "Enter your password:",
            WELCOME = "Welcome: ",
            INVALID_LOGIN = "Invalid login, please try again",
            LOCKED_OUT = "Too many failed login attempts, you are now locked out.",
            FILE = "src/data.txt";

    private final int LOGIN_ATTEMPTS = 4;

    /**
     * Creates User
     * @param input array of string inputs for user information
     * @return User object created from inputs
     */
    public User createUser(String[] input)
    {
        return new User(input[0], input[1], input[2]);
    }

    /**
     * Reads file and outputs array of User objects from data in file
     * @return array of User objects
     */
    public User[] readFile()
    {
        int numUsers = 0;
        try {
            numUsers = numFileLines(FILE);
        } catch (IOException e) {
            System.out.println(IO_EXCEPTION);
            e.printStackTrace();
        }
        User[] users = new User[numUsers];
        BufferedReader fileReader = null;
        try
        {
            fileReader = new BufferedReader(new FileReader(FILE));

            String line = "";

            for(int i = 0; i < numFileLines(FILE); i++)
            {
                // reads line (already checked for null in numFileLines method)
                line = fileReader.readLine();

                // creates user obj and adds to array
                users[i] = createUser(parseLine(line));
            }

        } catch (FileNotFoundException e) {
            System.out.println(FILE_NOT_FOUND);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(IO_EXCEPTION);
            e.printStackTrace();
        }
        finally
        {
            // closes file reader

                if (fileReader != null)
                {
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        System.out.println(IO_EXCEPTION);
                        e.printStackTrace();
                    }
                }

        }

        return users;
    }

    /**
     * Gets the number of lines in the file
     * @param file file name
     * @return number of lines in the file
     */
    private int numFileLines(String file) throws IOException
    {
        BufferedReader fileReader = null;
        int numLines = 0;
        try {
            fileReader = new BufferedReader(new FileReader(file));

            while(fileReader.readLine() != null)
            {
                numLines++;
            }
        } finally {
            if (fileReader != null) {
                fileReader.close();
            }
        }

        return numLines;
    }

    /**
     * parses line separated by commas
     * @param line line to be parsed
     * @return String[] of parsed line
     */
    private String[] parseLine(String line)
    {
        return line.split(",");
    }

    private User validLogin(String username, String password)
    {
        // gets array of users
        User[] users = readFile();
        for (User user: users)
        {
            // compares usernames case-insensitive
            if(user.getUsername().equalsIgnoreCase(username))
            {
                //compares passwords case-sensitive
                if(user.getPassword().equals(password))
                {
                    return user;
                }
            }
        }
        return null;
    }

    /**
     * Prompts user for login and outputs response
     */
    public void login()
    {

        String inputUser, inputPass;

        for (int i = 0; i < LOGIN_ATTEMPTS; i++)
        {

            // scanner
            Scanner scanner = new Scanner(System.in);

            // get username
            System.out.println(ENTER_EMAIL);
            inputUser = scanner.next();

            //get password
            System.out.println(ENTER_PASS);
            inputPass = scanner.next();

            // user obj
            User user;

            if((user = validLogin(inputUser, inputPass)) != null)
            {
                System.out.println(WELCOME + user.getName());
                return;
            }
            else
            {
                System.out.println(INVALID_LOGIN);
            }
        }

        System.out.println(LOCKED_OUT);
    }

}
