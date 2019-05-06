/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Friendsbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 *
 * @author Mohammad
 */
public class DBConnection {
    final static String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/azimm0699";
        static Connection connection = null;
        static Statement statement = null;
        
        public static Statement connect(){
            try
            {
             //connect tothe databse
             connection = DriverManager.getConnection(DATABASE_URL,"azimm0699","1641460");
             //create a statement
             statement = connection.createStatement();
             return statement;
             
            }catch(Exception e){
                e.printStackTrace();
            }
            return statement;
        }
}