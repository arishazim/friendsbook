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

public  class user {
    private String  id;
     private String password;
     private String gender;
     private String name;
     private String birthday;
     private String school;

    public user()
    {
        //parameter less constructor
    }
    public void viewprofile(String id)
    {
        System.out.println("Viewing profile of "+id);
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/azimm0699";        
                Connection c = null;
                Statement s = null;
                ResultSet rs= null;        
        try
        {           
        c = DriverManager.getConnection(DATABASE_URL,"azimm0699", "1641460");
        s = c.createStatement();
         rs = s.executeQuery("select * from useraccount where id = '"+id+"'");                 
        System.out.println();
        while(rs.next())
        {
            System.out.println("Name-"+rs.getString("name"));
            System.out.println("Birthday-"+rs.getString("birthday"));
            System.out.println("School-"+rs.getString("school"));  
        }
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
                            c.close();
                            s.close();
                            rs.close();
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                         }
                    }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
    
    
}
