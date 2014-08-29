
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

public class KMeanCluster {

  // Total DataPoints i points ,   j Coordinates
    private int[][] data;
 // Number of Clusters
    private int groups;

 // Centroid Points of i Clusters , j Coordinates
    private int[][] centers;

    private double[] distances;

 // Point Index in the clusters . 
    public int[] indices;

    public KMeanCluster(Page[] p,int groups){
   
        this.data=new int[p.length][p[0].kWordsFreq.length];
  
// **** Mapping the Page Vectors to the data vector ****.
        for (int i=0;i<data.length;i++)
            this.data[i]=p[i].getKWordsFreq();
        
        this.groups=groups;

        this.centers=new int[groups][data[0].length];

        distances=new double[groups];

        indices=new int[data.length];
   // Just an initilization of indices
        for (int i=0;i<indices.length;i++)
            indices[i]=9;
        int m;
   // Just an initilization of Centroid points of the first pages
        for (int i=0;i<this.centers.length;i++){
            centers[i]=data[i];
        }} 
        
    public static double euclid(int [] d1,int [] d2){
        double result=0.0;
        for (int i=0;i<d1.length;i++)
            result+=Math.pow(d1[i]-d2[i],2.0);
        return (Math.sqrt(result));
    }
    
    public int [] Sum(int [] d1,int [] d2){
        int [] result=new int [d1.length];
        for (int i=0;i<d1.length;i++)
            result[i]=d1[i]+d2[i];
        return (result);
    }
    
    public int [] Devide(int [] d1,int  b){
       
        int [] result=new int [d1.length];
        for (int i=0;i<d1.length;i++)
            result[i]=d1[i]/b;
        return (result);
    }
    
   // Calculate Similarity Distance using Jaccard Coefficent.
    
    public static double jaccard(int d1[], int d2[]) 
    {
        
    double result=0;
    double sum=0;
    double sumOfSquare1=0;
    double sumOfSquare2=0;
    
    for (int i=0;i<d1.length;i++) 
    {
        sum+=d1[i]*d2[i];
        sumOfSquare1+=Math.pow(d1[i], 2);
        sumOfSquare2+=Math.pow(d2[i], 2);
    
    }
    
    result=sum/(sumOfSquare1+sumOfSquare2-sum);
    
    return result;
    }
    
    
    public void updateCenters(){
        double mindis;
        int[] ns=new int[groups];
        double distance;
        for (int i=0;i<data.length;i++){
            mindis=999999999;
            //indices[i]=-1;
            for (int j=0;j<centers.length;j++){
         // Try to replace the distances measure by the Jaccarod Coefficient       
                distance=euclid(data[i],centers[j]);
                if (distance<mindis) {
                    //System.out.println(distance+" is smaller than "+mindis+" and obs "+i+" is belong to grp "+j );
                    mindis=distance;
                    indices[i]=j;
                }
            }
                        
        }
        
        centers=new int[groups][data[0].length];
        
        for (int i=0;i<indices.length;i++){
            centers[indices[i]]=Sum(centers[indices[i]],data[i]);
            ns[indices[i]]++;
         
        }
               
        for (int i=0;i<ns.length;i++){
            centers[i]=Devide(centers[i],ns[i]);
        }
    }
    
//    public void printIndices(){
//        for (int i=0;i<data.length;i++)
//            System.out.print(indices[i]);
//        System.out.print(" - ");
//        for (int i=0;i<centers.length;i++) {
//            for (int j=0;j<centers[0].length;j++){
//                System.out.print(centers[i][j]+"..");
//            }
//
//        }
//        System.out.println();
//    }
    
    public void doIt(){
        for (int iter=0;iter<50;iter++){
            
            this.updateCenters();
         //   this.printIndices();
            
        }
    }
// This method for sorting the pages in the cluster in order to create chromosomes easily
 public static void ClusterSorting(Vector cluster)
{

Page temp;

    for (int pass=1; pass < cluster.size(); pass++) {  // count how many times
        // This next loop becomes shorter and shorter
        for (int i=0; i < cluster.size()-pass; i++) {
            if (((Page)cluster.elementAt(i)).score   < ((Page)cluster.elementAt(i+1)).score) {
                // exchange elements
                temp=(Page)cluster.elementAt(i);
                cluster.setElementAt((Page)cluster.elementAt(i+1),i);
                cluster.setElementAt(temp,i+1);
            }
        }
    }
}

    }
    
