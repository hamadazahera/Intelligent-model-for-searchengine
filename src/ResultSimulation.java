
/**
 * 
 * 
 */
import java.sql.*;

public class ResultSimulation {

    static  Page[] p;
 // counting number of pages that it's ratio more than or equal : 25   
    int count25=0;
 // counting number of pages that it's ratio more than or equal : 50   
    int count50=0;
    
    public ResultSimulation() {
    // 500 : Number of Pages in Data Set 1 , 20: Number of Keywords in Vectorization
         p = new Page[1000];
        int kw[] = new int[40];

        int PageID, Score;
        String URL;
        int ratio;
        
        Connection con = null;
        try {

// Loading BD SQL Driver and create Connections
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost/standardresults","root","admin");

// Loading BD Access Driver and create Connections
//            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//            con = DriverManager.getConnection("jdbc:odbc:DataSource");

            Statement sta = con.createStatement();

//            String sql="SELECT * FROM Pages, PageVectorization WHERE Pages.PageID = PageVectorization.PageID";
//            sql = "SELECT Pages.PageID, Pages.URL, Pages.Score, PageVectorization.KW1, PageVectorization.KW2, PageVectorization.KW3, PageVectorization.KW4, PageVectorization.KW5, PageVectorization.KW7, PageVectorization.KW8, PageVectorization.KW9, PageVectorization.KW10, PageVectorization.KW11, PageVectorization.KW12, " +
//                    "PageVectorization.KW13, PageVectorization.KW14, PageVectorization.KW15, PageVectorization.KW16, PageVectorization.KW17, PageVectorization.KW18, PageVectorization.KW19, PageVectorization.KW20 FROM Pages, PageVectorization" +
//                    " WHERE Pages.PageID = PageVectorization.PageID";

            String sql2="SELECT * FROM Pages1000, PageVectorization40 WHERE Pages1000.PageID = PageVectorization40.PageID";
            ResultSet rs = sta.executeQuery(sql2);

            //STEP 5: Extract data from result set
            int i = 0;
            int k=0;
            while (rs.next()) {
                //Retrieve by column name
                PageID = rs.getInt(1);
                URL = rs.getString(2);
                Score = rs.getInt(3);
                ratio=rs.getInt(4);

                if(ratio>=50)
                    count50++;
                else
                    if(ratio>=25)
                        count25++;
                for (int j = 0; j < 20; j++) {
                    k=j+5;
                 kw[j] = rs.getInt(k);
                       }

                //Adding SearchResults Data into Page Objects
                p[i] = new Page(PageID, URL, kw, Score,ratio);
                // Testing Page Data Retrieval
                //    System.out.println(p[i].PageID);
                 i++;
                 } // end of While
                
            sta.close();
            con.close();
// print the Ratio of Relevances:
            //   System.out.println("Number of Pages with ratio more than 25 %" +count25);
            //   System.out.println("\nNumber of Pages with ratio more than 50 %" +count50);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    } // End of Constructor

    public ResultSimulation(String s)
    {
    System.out.println(s);
    } // end of argument constructor

    public static void generateRandomSearchResults() {
        
        Connection con = null;
       double totalwords=0;
        int RelevanceRatio=0;
        double sumOfKwords;
        int temp;
        double r;
        
        try {
// Loading BD SQL Driver and create Connections
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection("jdbc:mysql://localhost/standardresults","root","admin");

// Loading BD Access Driver and create Connections
//            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
//            con = DriverManager.getConnection("jdbc:odbc:DataSource");
            Statement sta = con.createStatement();

              sta.executeUpdate("delete from Pages1000");
              sta.executeUpdate("delete from PageVectorization40");
              
//              sta.executeUpdate("delete from Pages");
//              sta.executeUpdate("delete from PageVectorization");
              
            int s = 0;
            int lowscore=0;
         //--- 20 : Number of Keywords in this Data SET
//            int[] freq = new int[20];

//            // 40 : Number of Keywords in this Data SET
            int[] freq40 = new int[40];
            int rowcount=0;
// --- Part 1 : of inserting Pages            
            // for each row of 1000
            for (int i = 1; i <= 700; i++) {
                sumOfKwords=0;
                totalwords=0;
                 temp=0;
              //int number = (int) (a + Math.random( ) * (b - a + 1));  
                lowscore = (int) (Math.random() * 30 + 1);
                
//              used in case : 20 keywords
//                for (int j = 0; j < 20; j++) {
//                    freq[j] = (int) (Math.random() * 50 + 1);
//                    temp=freq[j];
//                    sumOfKwords+=freq[j];
//                }
                
//                //  used in case : 40 keywords
                for (int j = 0; j < 40; j++) {
                    freq40[j] = (int) (Math.random() * 10 + 40);
                    temp=freq40[j];
                    sumOfKwords+=freq40[j];
                }
                
                                
                totalwords= (int) (Math.random() * 9001 + sumOfKwords);
                RelevanceRatio=(int) ((((sumOfKwords/totalwords)*100)+lowscore)/2);        
           //     System.out.println(RelevanceRatio);
                sta.executeUpdate("INSERT INTO Pages1000 VALUES (" + i + ", 'www.s" + i + ".com', " + lowscore +", "+ RelevanceRatio+ ")");
                rowcount++;
                
//                sta.executeUpdate("INSERT INTO PageVectorization" + " VALUES (" + i + ", " + freq[0] + ", " + freq[1] + ", " + freq[2] + ", " + freq[3] + ", " + freq[4] + ", " + freq[5] + ", " + freq[6] + ", " + freq[7] + ", " + freq[8] + ", " + freq[9] + ", " + freq[10] + ", " + freq[11] + ", " + freq[12] + ", " + freq[13] + ", " + freq[14] + ", " + freq[15] 
//                        + ", " + freq[16] + ", " + freq[17] + ", " + freq[18] + ", " + freq[19]+ ")");
                
               sta.executeUpdate("INSERT INTO PageVectorization40" + " VALUES (" + i + ", " + freq40[0] + ", " + freq40[1] + ", " + freq40[2] + ", " + freq40[3] + ", " + freq40[4] + ", " + freq40[5] + ", " + freq40[6] + ", " + freq40[7] + ", " + freq40[8] + ", " + freq40[9] + ", " + freq40[10] + ", " + freq40[11] + ", " + freq40[12] + ", " + freq40[13] + ", " + freq40[14] + ", " + freq40[15] + ", " + freq40[16] + ", " + freq40[17] + ", " + freq40[18] + ", " + freq40[19]
                + ", " + freq40[20]+ ", " + freq40[21]+ ", " + freq40[22]+ ", " + freq40[23]+ ", " + freq40[24]+ ", " + freq40[25]+ ", " + freq40[26]+ ", " + freq40[27]+ ", " + freq40[28]+ ", " + freq40[29]+ ", " + freq40[30]+ ", " + freq40[31]+ ", " + freq40[32]+ ", " + freq40[33]+ ", " + freq40[34]+ ", " + freq40[35]+ ", " + freq40[36]+ ", " + freq40[37]+ ", " + freq40[38]+ ", " + freq40[39]+ ")");
            }

            
// part 2 : of inserting Pages
            
                for (int i = 701; i <=1000; i++) {
                sumOfKwords=0;
                totalwords=0;
                 temp=0;
              //int number = (int) (a + Math.random( ) * (b - a + 1));  
                s = (int) (Math.random() * 100 + 1);
                
//              used in case : 20 keywords
//                for (int j = 0; j < 20; j++) {
//                    freq[j] = (int) (Math.random() * 50 + 1);
//                    temp=freq[j];
//                    sumOfKwords+=freq[j];
//                }
//                
//                //  used in case : 40 keywords
                for (int j = 0; j < 40; j++) {
                    freq40[j] = (int) (Math.random() * 50 + 1);
                    temp=freq40[j];
                    sumOfKwords+=freq40[j];
                }
                
                                
                totalwords= (int) (Math.random() * 9001 + sumOfKwords);
                // The relevance is average between keywords ratio and the score.
                RelevanceRatio=(int) ((((sumOfKwords/totalwords)*100)+s)/2);        
           //     System.out.println(RelevanceRatio);
                sta.executeUpdate("INSERT INTO Pages1000 VALUES (" + i + ", 'www.s" + i + ".com', " + s +", "+ RelevanceRatio+ ")");
                rowcount++;
                
//                sta.executeUpdate("INSERT INTO PageVectorization" + " VALUES (" + i + ", " + freq[0] + ", " + freq[1] + ", " + freq[2] + ", " + freq[3] + ", " + freq[4] + ", " + freq[5] + ", " + freq[6] + ", " + freq[7] + ", " + freq[8] + ", " + freq[9] + ", " + freq[10] + ", " + freq[11] + ", " + freq[12] + ", " + freq[13] + ", " + freq[14] + ", " + freq[15] 
//                        + ", " + freq[16] + ", " + freq[17] + ", " + freq[18] + ", " + freq[19]+ ")");
                
               sta.executeUpdate("INSERT INTO PageVectorization40" + " VALUES (" + i + ", " + freq40[0] + ", " + freq40[1] + ", " + freq40[2] + ", " + freq40[3] + ", " + freq40[4] + ", " + freq40[5] + ", " + freq40[6] + ", " + freq40[7] + ", " + freq40[8] + ", " + freq40[9] + ", " + freq40[10] + ", " + freq40[11] + ", " + freq40[12] + ", " + freq40[13] + ", " + freq40[14] + ", " + freq40[15] + ", " + freq40[16] + ", " + freq40[17] + ", " + freq40[18] + ", " + freq40[19]
                + ", " + freq40[20]+ ", " + freq40[21]+ ", " + freq40[22]+ ", " + freq40[23]+ ", " + freq40[24]+ ", " + freq40[25]+ ", " + freq40[26]+ ", " + freq40[27]+ ", " + freq40[28]+ ", " + freq40[29]+ ", " + freq40[30]+ ", " + freq40[31]+ ", " + freq40[32]+ ", " + freq40[33]+ ", " + freq40[34]+ ", " + freq40[35]+ ", " + freq40[36]+ ", " + freq40[37]+ ", " + freq40[38]+ ", " + freq40[39]+ ")");
            }
            
            sta.close();
            con.close();
            
           System.out.println("Number of Rows inserted "+rowcount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Page[] getSearchResults(){
        return p;
    }

    public static void main(String[] args) {

      ResultSimulation rs=new ResultSimulation("Randomly Generated Search Results");
      rs.generateRandomSearchResults();

//    ResultSimulation rs=new ResultSimulation();
      

    }
}
