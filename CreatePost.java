/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Friendsbook.Post;
import Friendsbook.DBConnection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Mohammad
 */
public class CreatePost 
{
    public static void createpost(String post,String Author_id, String name, Date stamp )
    {
        Statement stmnt=DBConnection.connect();
        try{
            stmnt.executeUpdate("insert into  post ( `Post`, `Author_id`, `author_name`, `timestamp`,`Type`) values "
                    + "('"+post+"','"+Author_id+"','"+name+"','"+stamp+"','"+0+"')");
             
            String postcopy=post;
            //for extracting hashtags in the post created
            String[] split_post=postcopy.split(" ");
            for(String i : split_post)
            {
                if(i.startsWith("#"))
                {
                 try{
                  stmnt.executeUpdate("INSERT INTO hashtag(`user_id`, `post`, `hashword`) VALUES( '"+Author_id+"','"+postcopy+"','"+i.substring(1)+"')");  
                }
                  catch (SQLException e) {
           e.printStackTrace();
        }
                }
            }                
    }   catch (SQLException e)
    {
           e.printStackTrace();
        }
}
    
}

