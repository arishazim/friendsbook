/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Friendsbook.Post;

import Friendsbook.DBConnection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 *
 * @author Mohammad
 */
public class Comment {
    
    public static void comment(int  Post_id,String User_id, String Comment) 
    {
         Statement s=DBConnection.connect();
        try{
             s.executeUpdate("Insert into comment values('"+Post_id+"','"+User_id+"','"+Comment+"')");
        }
         catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static void commentonpost(int Post_id, String id)
     {
         Scanner input = new Scanner(System.in);
                 
                    if(Post_id >= 1  )
                    {
                    Statement s=DBConnection.connect();
                    ResultSet    rs = null;
                        try
                         {
                                    rs =s.executeQuery("Select * from comment  where Post_ID= '"+Post_id+"'");
                                    while(rs.next())
                                    {
                                    System.out.println(rs.getString("User_id")+"- Commented :-"+rs.getString("comment"));
                                    }
                                    System.out.println("Enter your comment (Limited 20 Char) OR enter X to go back ");
                                    String comment=input.nextLine();
                                    comment = input.nextLine();
                                    if(!comment.equals("x"))
                                    {
                                     comment(Post_id,id,comment);
                                     System.out.println("Commented Sucessfully");
                                    }
                       }
                        catch (Exception e) 
                            {
                                e.printStackTrace();
                            }
                          }
                 }
    
}
