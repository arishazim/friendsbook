/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Friendsbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;

/**
 *
 * @author Mohammad
 */
public class UserSystem 
{
    
    public UserSystem()
    {
        //para less construct
    }

    
        public  void login()
    {
       
        //we need id and password
        Scanner input = new Scanner(System.in);
        String id="";
        String password="";
        boolean idFound = false;
        user u = new user();
        //get the login info.
        System.out.println("Please enter your ID");
        u.setId(input.nextLine());
       
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/azimm0699";        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;        
        try
        {
            
          connection = DriverManager.getConnection(DATABASE_URL,"azimm0699", "1641460");
            statement = connection.createStatement();
            resultSet = statement.executeQuery("Select * from useraccount "+ "where id = '" + u.getId() + "'");            
            if(resultSet.next())
            {
                System.out.println("Please enter your password for id for : " + u.getId());        
                u.setPassword(input.nextLine());
                //the id is found, check the password
//                System.out.print("this is password"+resultSet.getArray("password"));
                if(u.getPassword().equals(resultSet.getString(2)))
                {
                    //password is good
                     idFound = true;
                     u.setId(resultSet.getString(1));
                     u.setGender(resultSet.getString(3));
                     u.setName(resultSet.getString(4));
                     u.setBirthday(resultSet.getString(5));
                     u.setSchool(resultSet.getString(6));
                     UserAccount useraccount = new UserAccount(u.getId(), u);
                    
                }
                else
                {
                    //password is not correct
                    System.out.println("The password is not correct!");
                }
            }
            else
            {
                System.out.println("The login ID is not found! are you registered?");
            }
            
            
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        catch(Exception e){
            System.out.print(e.getStackTrace());
        }
        finally
        {
            //close the database
                try
                {
                    connection.close();
                    statement.close();
                    resultSet.close();
                }
                catch(SQLException e)
                {
                    e.printStackTrace();
                }
            } 
        
        }
      
    
    public  void register()
    {
        System.out.println("\n FriendsBook Registration page \n");
        //declare varaibles
        user u = new user( );
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/azimm0699";
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultset = null;
        
        Scanner input = new Scanner(System.in);
        
        //prompts and input
        boolean approved = false;
         boolean uc=false,lc=false,d=false,s=false;
            
        try
        {
            
            //connect to the databse
            connection = DriverManager.getConnection(DATABASE_URL,"azimm0699", "1641460");
            
            // the statement
            statement = connection.createStatement();
            
            //do a query
           
             while (  approved == false)      
             {
        System.out.println("Please enter your id");
        String id= input.next();
         resultset = statement.executeQuery("Select  id from useraccount ");
            if(id.equals(resultset.next()))
            {
                //if the id is used then
                System.out.println("user Id already taken");
            
           if(id.length()>=3 && id.length()<=10)
            {     
                for (char c : id.toCharArray())
                {
                    if(Character.isUpperCase(c))
                    {uc=true;}
                    if(Character.isLowerCase(c))
                    {lc = true;}
                    if(Character.isDigit(c))
                    {d = true;}
                    if(!Character.isLetterOrDigit(c))
                    {s = true;}
                }
               }
            }
           if (uc==true && lc==true && d==true && s == true)
            {
                 approved = true;
                 u.setId(id);
                System.out.println(" id validated");
            }        
        else 
            {
                
                System.out.println("Either Id registered already or \ntry a different Id \nhaving 3-8 characters"
                            + "a upperacse,lowercase,number and special charcter");
            }
           }
        
             boolean similar= true;
          while (  similar== true)
        {  
                System.out.println("Please enter your new password");
                String password =(input.next()); 

              //  System.out.println("ID: "+id+"pwd: "+password);
             if((!password.equals(u.getId())))
                    {
                        similar=false;
                        u.setPassword(password);
                    }
             else
             {
                  System.out.println("password cannot"
                                + " be same as id") ; 
             }
         
        }
          System.out.println("Please enter your gender");
          input.next();
       u.setGender(input.nextLine());
        
        System.out.println("Please enter your name");
        u.setName(input.nextLine());
        
        System.out.println("Please enter your birthday(MM/DD/YYYY)");
        
       u.setBirthday(input.nextLine());
        
        
        System.out.println("Please enter your school");
        u.setSchool(input.nextLine());

             
                //insert a record into useraccount
              int r = statement.executeUpdate("insert into useraccount values('" + u.getId() +"', '" + u.getPassword() +"','"+u.getGender()+"','"+u.getName()+"'"
                            + ",'"+u.getBirthday()+"','"+u.getSchool()+"')" );
                System.out.println("Account creation successful!");
                System.out.println();
               login();
               
                
        }
                    
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
             //close the database
             try
             {
                 resultset.close();
                 statement.close();
                 connection.close();
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }  
            
        }
    }
    
    
    public static boolean isDateValid(int year, int month, int day) 
    {
    boolean dateIsValid = true;
    try {
        LocalDate.of(year, month, day);
    } catch (DateTimeException e) {
        dateIsValid = false;
    }
    return dateIsValid;
    }
    
    
}
