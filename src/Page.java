/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hamada
 */

public class Page {

    String URL;
    int[] kWordsFreq;
    int score;
    String cluster;
    int PageID;
    int ratio;
    public static int [][] weightValues;
    
    

    public Page(int ID, String url, int[] kw, int score,int ratio) {
        this.PageID = ID;
        this.URL = url;
        this.ratio=ratio;
        this.kWordsFreq = new int[kw.length];
        System.arraycopy(kw, 0, this.kWordsFreq, 0, kw.length);

        this.score = score;
    }

    public int[] getKWordsFreq() {
        return this.kWordsFreq;
    }
// Count the different number of keywords in the page for term 4.
    public int countKeyWords() {
        int count=0;
        for(int i=0;i<this.kWordsFreq.length;i++)
            if(this.kWordsFreq[i] !=0)
                count++;
        return count;
    }
// Calculate Weighted Value per Term Frequencey for better similarity measure
    
    public static void weightedValues() 
    {
       // calculating the total sum of term in the document collection .
        int [] TD=new int [Model.numberofKwords];
        
      
        for (int i=0;i<TD.length;i++) {
        
        for(int j=0;j<Model.numberOfPages;j++)
        {
           
        TD[i]+=Model.page[j].kWordsFreq[i];
        }
               

        }
        
     weightValues=new int[Model.numberOfPages][Model.numberofKwords];
     
     double d=0;
     double n=Model.numberOfPages;

        for(int i=0;i<Model.numberOfPages;i++) {
         //   System.out.println();
            for(int j=0;j<Model.numberofKwords;j++)
            {
                d=n/TD[j];
              //  System.out.println(log2(d));
               weightValues[i][j]=(int) (Model.page[i].kWordsFreq[j]*log2(d));
             //  System.out.print(weightValues[i][j]+" ");

            }
    }
    }
    

// This function to calculte Log base 2 as it's not implemented in Java . 
    
public static double log2(double num)
{
return (Math.log(num)/Math.log(2));
}  
    public void printKWordsFreq() {
        for (int i = 0; i < this.kWordsFreq.length; i++) {
            System.out.print(this.kWordsFreq[i] + " , ");
        }
    }

    public String getURL() {
        return this.URL;
    }

    public int getScore() {
        return this.score;
    }
}
