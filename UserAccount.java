/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Friendsbook;

import Friendsbook.Post.Comment;
import Friendsbook.Post.CreatePost;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.sql.Date;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Mohammad
 */
public class UserAccount 
{
    //attributes
     user u;
     private String id ;
     private ArrayList<String> request;
     private ArrayList<String> message;
     private ArrayList<String> friendlist;
     private int count;        
     int new_message=0;
     int new_request=0;
     

     public UserAccount(){}
     
     
     public UserAccount(String  i, user u)
     { 
         id =i;
         this.u = u;
         request = new ArrayList<String>();
         message = new ArrayList<String>();     
         friendlist = new ArrayList<String>();
         final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/azimm0699";
         Connection c = null;
         Statement s = null;
         ResultSet rs = null, r1 = null;
         
         try
         {
             c = DriverManager.getConnection(DATABASE_URL,"azimm0699","1641460");
             s = c.createStatement();
             rs = s.executeQuery("Select * from Notification where sendto = '"+id+"' and status = '1' ");
             while(rs.next())
                     {
                         count++;
                        
                          if(rs.getString("type").equals("r"))
                                {                    
                                    request.add(rs.getString("sender"));
                                    new_request++;
                                  }
                        if(rs.getString("type").equals("m"))
                        {
                            message.add(rs.getString("sender")+":"+rs.getString("content"));
                            new_message++;
                           //  int r = s.executeUpdate("update notification set status = '0'  where sendto ='"+id+"'");
                            // message.add(resultset.getString("sender"));
                            // int r = statement.executeUpdate("update notification set status = '1' ");
                          }
                          
                     }
             r1 =s.executeQuery("Select  *   from friends where sender= '"+id+"' or  receiver  = '"+id+"' ");
                while(r1 .next())
            {                
                if(r1 .getString("sender").equals(id))
                {
                        friendlist.add((r1 .getString("receiver")));
                }
                if(r1.getString("receiver").equals(id))
                {
                        friendlist.add(r1.getString("sender"));
                }
                
                }
                
                welcome();
         }
         catch (SQLException e)
                 {e.printStackTrace();}
         finally
         {
             try{
             rs.close();
             r1.close();
             s.close();
             c.close();
             }
             catch(Exception e)
             {
                 e.printStackTrace();
             }
         }
     }
            
     Scanner input = new Scanner(System.in);    
     String   selection ="" ;
        
    public void welcome() throws SQLException
    
    {
         
        while(!selection.toUpperCase().equals("X"))
            {
                System.out.println("\n Welcome "+id+" to friendsbook \n\n");
                System.out.println();
                 System.out.println("5 Recent post or update from your friend");
        
                Statement stmnt=DBConnection.connect();
                try{
                    if(!friendlist.isEmpty())
                    {
                        for(int i=0;i<friendlist.size(); i++)
                        {

                  ResultSet rs=  stmnt.executeQuery("SELECT * from `post` WHERE  Author_id='"+friendlist.get(i)+"'order by Post_ID DESC limit 0,5 ");
                          while(rs.next())
                          {
                             System.out.println(rs.getString("Post_ID")+":- "+rs.getString("Author_id")+"post/Update "+rs.getString("Post"));
                            }
                      }       
                      }
                    else
                    {System.out.println("No post from friends");}
                }
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
                System.out.println("Select a post number to comment or Select from option below");
                System.out.println("A :Check  Notification (New:-"+ count);
                System.out.println("B: Create post  ");
                System.out.println("C: Friends");
                System.out.println("D: Update profile");
                System.out.println("E: Trending hashtag#");
                System.out.println("X: Logout");
                selection = input.next();
               
                if(isInteger(selection))
                    
                        {
                            int  intSelection=0;
                      intSelection = Integer.parseInt(selection);
                      
                             Comment.commentonpost( intSelection, id);
                        }
            
            if(selection.equals("A") ||selection.equals("a") )
            {
                System.out.println("1: See Friend request -"+new_request);
                System.out.println("2: See Message-"+new_message);
                System.out.println("X: Return Main Page  ");
                 String select =input.next();  
                  if(!select.toUpperCase().equals("x"))
                   {
                if(select.equals("1"))
                {                    
                    acceptfriend(id);
                    new_request=0;
                }
                if(select.equals("2"))
                {
                     if (new_message==0) 
                            {
                                     System.out.printf("You dont  have any message  yet." + id);                                    
                            }
                     else
                     {
                           Statement s=DBConnection.connect();
                        for (int i = 0;i<message.size();i++)
                        {
                            System.out.println("ID-"+"-"+message.get(i));
                        }
                         System.out.println("Select id to reply or Enter  x to go back ");
                     String reply="";
                     reply=input.next();
                      s.executeUpdate("update notification set status = '0'  where sendto ='"+id+"' and sender = '"+reply+"'");
                     if(!reply.equals("x"))
                     {
                         try{
                             sendmessage(id,reply);
                                 }
                         catch(Exception e)
                            {
                            e.printStackTrace();
                            }
                     }                    
                     new_message=0;
                     }
                     count=0;
                    }
                 }
            }
          if(selection.equals("B")||selection.equals("b"))
                {     
                       Scanner input = new Scanner(System.in);    
                    String   post ="" ;
                    System.out.print("enter your post.");
                     post= input.nextLine();
                    Date timestamp = new Date(System.currentTimeMillis());                        
                    CreatePost.createpost(post,u.getName(), id,timestamp);
                    System.out.print("Successfully Posted by."+id);
                }     
            if(selection.equals("C") || selection.equals("c"))
            {
                    System.out.println(" friends\n select an option");
                     System.out.println("1: See Friends ");
                     System.out.println("2: Add friends  ");
                     System.out.println("3: Message friends  ");
                     System.out.println("X: Return Main Page  ");
                     String  se = input.next();
                    if(!se.toUpperCase().equals("x"))
                     {
                      if(se.equals("1"))
                        {
                            seefriend(id);
                        }
                        if(se.equals("2"))
                        {
                            addfriend(id);
                        }
                         if(se.equals("3"))
                        {
                             if (friendlist.isEmpty()) 
                            {
                                     System.out.printf("You dont  have any friend's  yet.\n" + id);                                    
                            }
                             else
                             {
                                 System.out.println("Enter id of friend to send message");
                                  String friend_id = "";
                                  friend_id = input.next();
                                  sendmessage(id,friend_id);
                             }
                        }
                        }
                                           
                 }
            
            if(selection.equals("D") ||selection.equals("d"))
            {
                Update update = new Update();
                update.update(id,u);
            }
            
            if(selection.toUpperCase().equals("E"))
            {
               hashcode();
              }
                
             }
    }  
   
     
    public void addfriend (String id)
     {
        
         String nid ="", sender =id, sendto,type="r",content="Please add "+id+" to your friendlist"; 
        
        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/azimm0699";
        
        Connection connection = null;
        Statement statement = null;
       ResultSet rs= null;
   
        System.out.println("Please enter  ID of your friend");
        Scanner input = new Scanner(System.in);
        sendto = input.next();
        try
        {
          connection = DriverManager.getConnection(DATABASE_URL,"azimm0699", "1641460");
           statement = connection.createStatement();
            rs = statement.executeQuery("Select * from friends  where sender = '"+sender+"' and  receiver  = '"+sendto+"' "
                    + "or sender ='"+sendto+"' and  receiver = '"+sender+"'");
            if(rs.next())
            {
                System.out.println(" Already friends");
            }
            else
            {
                rs = statement.executeQuery("Select  *  from useraccount "
                        + "where id = '" + sendto+ "'");
            
                    if(rs.next() )
                    {
                            System.out.println("The ID is  found!");
                            System.out.println("1: Confirm to send\t 2: Return to homepage");
                            Scanner i= new Scanner(System.in);
                            String  confirm = i.next();
                            if (confirm.equals("1"))
                            {
                     int r = statement.executeUpdate("Insert into notification values ('"+id+"','"+sendto+"',"
                             + "'"+type+"','"+content+"','1'  )");
                     System.out.println("Request Successfully set to "+sendto+" \n");                    
                    System.out.println();
                            }
                    }
            else 
            {                
                System.out.println("Id not found \n");
                
            }
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
                            connection.close();
                            statement.close();
                            rs.close();
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                         }

              }        
    }
     
     public ArrayList<String> seefriend( String id)
     {
         Scanner input = new Scanner(System.in);
         System.out.println("List of your friends : "+id);  
              if (friendlist.isEmpty()) 
                       {
                            System.out.printf("You dont  have any friend's  yet.\n" + id);                                    
                        }
               else
                  {
                   for(int i=0; i<friendlist.size(); i++)
                     {
                       System.out.printf("%s: %s \n", i+1,friendlist.get(i));
                     }
                     System.out.printf("enter the number to view profile\nor 0 to return back");
                                              
                       String select = "";
                       select = input.next();
                       if(isInteger(select))
                        {
                             int intselection = 0;
                             int intSelection = Integer.parseInt(select);
                            if(intSelection >= 1 && intSelection<=friendlist.size())
                             {
                                 {
                                      user u = new user();
                                     u.viewprofile(friendlist.get(intSelection-1));
                                 }
                             }
                        }
                  }
          return friendlist;
    }
     
     public void acceptfriend(String id)
     {
            String sender="";
            Scanner input = new Scanner(System.in);
            String selection = "";
            UserAccount useraccount = new UserAccount(id,u);
            if(useraccount.request.size()==0)
            {
                System.out.println("No new request");
            }
            else 
            {
            for(int i=0; i<useraccount.request.size(); i++)
            {
                System.out.printf("%s: Friend Request:- %s \n", i+1, useraccount.request.get(i));
                sender = useraccount.request.get(i);
             }
            System.out.println("Enter the number  to acccept request or X to go back");
            selection = input.next();
            if(isInteger(selection))
            {
                int intSelection = Integer.parseInt(selection);
               if(intSelection >= 1 && intSelection<=useraccount.request.size())
                {
                    //now view the statement
               final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/azimm0699";        
                Connection c = null;
                Statement s = null;
       // ResultSet rs= null;        
        try
        {           
                    c = DriverManager.getConnection(DATABASE_URL,"azimm0699", "1641460");
                    s = c.createStatement();
                   int j =s.executeUpdate("insert into friends values('"+sender+"', '"+id+"')");
                    System.out.println("Now you are friends with "+ sender);
                    int r = s.executeUpdate("update notification set status = '0'  where sendto ='"+id+"' and sender = '"+sender+"'");
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
                          //  rs.close();
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                         }
                    }
        count--;
            }
            }
             else
                  {
                      System.out.println("Id not found");
                  }
            }
        }
     
     public void sendmessage(String id,String friend_id) throws SQLException
             {
            Statement s = DBConnection.connect();
            ResultSet resultset=null;                   
            try
            {              
        resultset= s.executeQuery("Select * from notification"
                + " where  type ='m' and sender = '"+id+"'and sendto='"+friend_id+"'"
                        + "or sender ='"+friend_id+"' and  sendto = '"+id+"'");
                                while(resultset.next())
                                {
                                    System.out.println(resultset.getString("sender")+"-"+resultset.getString("content"));
                                  //  System.out.println(resultset.getString("sendto")+"-"+resultset.getString("content"));
                                 } 
                                    String Content= input.nextLine();
                                     System.out.println("Enter your message");                                    
                                    Content = input.nextLine();
                                     s.executeUpdate("Insert into notification values ('"+id+"','"+friend_id+"',"
                                     + "'m','"+Content+"','1'  )");
                                    System.out.println("Message Successfully set to "+friend_id+"\n");
                                    System.out.println();                        
            }
            catch(Exception e)
            {
            e.printStackTrace();
            }

                  
            }
     
     public void hashcode()
     {
         Statement s = DBConnection.connect();
          ResultSet resultset ;
                
                    try {                       
                        resultset =s.executeQuery("select hashword, count(hashword)c from hashtag group by hashword order by c DESC limit 0,3");
                        while(resultset.next())
                        {
                            System.out.println(resultset.getString("hashword"));
                        }

                        //for displaying friends post with the desired hashtag
                        System.out.println("Enter the hashtag to view related post");
                        //Scanner input = new Scanner(System.in);
                         String hashword = input.next();
                        ResultSet r= s.executeQuery("select * from hashtag");
                        while(r.next())
                        {                           
                            for (String f :friendlist)
                            {
                                if(f.equals(r.getString("user_id")) && hashword.equals(r.getString("hashword")))
                                {
                                    System.out.println(r.getString("user_id")+" posted "+r.getString("post"));
                                }
                            }
                        }
                         if(resultset==null)
                         {
                             System.out.println("NO trending #");
                         }
                        
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
     }
     
     private boolean isInteger(String a)
    {
        try
        {
            //if a is not an integer, an exception will be thrown out
            int i = Integer.parseInt(a);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

}
