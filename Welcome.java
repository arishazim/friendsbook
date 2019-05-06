/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Friendsbook;

import java.util.Scanner;

/**
 *
 * @author Mohammad
 */
public class Welcome  
{
    public static  void main (String[] args )
    {
                String selection = "";
        while(!selection.equals("x")){
        UserSystem usersystem = new UserSystem();
        System.out.println("Welcome to friendsbook \n Please make your selection");
        System.out.println("1: Login");
        System.out.println("2: Register");
        System.out.println("x: Finish the simulation");

        Scanner input = new Scanner(System.in);             
            //get the selection from the user
            selection = input.nextLine();
            System.out.println();
            
            if(selection.equals("1"))
            {
                usersystem.login();
            }
            if(selection.equals("2"))
            {
                usersystem.register();
            }
          
            }

    }
    }


