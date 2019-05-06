/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Friendsbook;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Mohammad
 */
public class Update 
{
      public  void update(String id, user u)    
    {
         //   user u = new user();
            UserAccount a = new UserAccount(id,u);
            Scanner input = new Scanner(System.in);
            String option="";
            System.out.println("UPDATE Profile \nPlease make your selection: "+id);
            System.out.println("1: Update  Password");
            System.out.println("2: Update Gender");
            System.out.println("3: Update Name ");
            System.out.println("4: Update Birthday ");
            System.out.println("5: Update School ");
            System.out.println("6: Main Menu");
                    
              final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/azimm0699";
               Connection connection = null;
               Statement statement = null;                  
               try{ 
                        connection = DriverManager.getConnection(DATABASE_URL,"azimm0699", "1641460");
                        statement =connection.createStatement();
                        option=input.next();                        
                            if(option.equals("1"))
                           {
                                System.out.println("1: Enter New  Password for"+ id);
                               String password = input.next();
                                u.setPassword(password);
                                 //what if password is already similar. there can be another  function added to this.
                                int r = statement.executeUpdate("update useraccount set password = '"+u.getPassword()+"' where id = '"+id+"' "); 
                                System.out.println("Updated Sucecessfully\n \n");    
                               // statement.executeUpdate("insert into post (`Post`, `Author_id`, `author_name`, `Type`) values ('updated Password','"+u.getId()+"','"+u.getName()+"','1')");
                              }
                           if(option.equals("2"))
                           {
                               System.out.println("1: New Gender");
                                u.setGender(input.next());
                                int r = statement.executeUpdate("update useraccount set gender = '"+u.getGender()+"' where id = '"+id+"' ");
                                statement.executeUpdate("insert into post (`Post`, `Author_id`, `author_name`, `Type`) values ('updated Gender','"+u.getId()+"','"+u.getName()+"','1')");
                                System.out.println("Updated Sucecessfully\n \n");                          
                            }
                            if (option.equals("3"))
                       {
                               System.out.println("1: New Name");
                            u.setName(input.next());
                            int r = statement.executeUpdate("update useraccount set name = '"+u.getName()+"' where id = '"+id+"' ");
                            statement.executeUpdate("insert into post (`Post`, `Author_id`, `author_name`, `Type`) values ('updated Name','"+u.getId()+"','"+u.getName()+"','1')");
                            System.out.println("Updated Sucecessfully\n ");
                            
                       }
                            if (option.equals("4"))
                       {
                           System.out.println("1: New Birthday");
                           u.setPassword(input.next());
                            int r = statement.executeUpdate("update useraccount set birthday = '"+u.getBirthday()+"' where id = '"+id+"' ");
                            statement.executeUpdate("insert into post (`Post`, `Author_id`, `author_name`, `Type`) values ('updated Birthday','"+u.getId()+"','"+u.getName()+"','1')");
                            System.out.println("Updated Sucecessfully\n \n");
                            
                       }
                            if (option.equals("5"))
                       {
                           System.out.println("1: New School");
                            u.setSchool(input.next());
                            int r = statement.executeUpdate("update useraccount set school = '"+u.getSchool()+"' where id = '"+id+"' ");
                            statement.executeUpdate("insert into post (`Post`, `Author_id`, `author_name`, `Type`) values ('updated school','"+u.getId()+"','"+u.getName()+"','1')");
                            System.out.println("Updated Sucecessfully\n \n ");
                        }                    
           }
                    catch (SQLException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                       {
                            try
                            {
                                statement.close();
                                connection.close();
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }  
                        }
             }     

}
