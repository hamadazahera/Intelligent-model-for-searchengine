
import java.util.Vector;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *  Testing Semantic Model for Improving Search Engine Results into three steps :
 *  1 - Page Vectorization
 *  2-  Page Clustering
 *  3   Optimization Techniques (GA)
 *
 * @author Hamada
 */
public class Model {

    static int numberOfClusters;
    static int numberofKwords = 40;
    static int numberOfPages;
    // vector of all pages
    static Page[] page;
    Page[] RPages;

    public Model(int numOfClusters, int numberofKwords, int numberOfPages) {
        this.numberOfClusters = numOfClusters;
        this.numberOfPages = numberOfPages;
        this.numberofKwords = numberofKwords;

        System.out.println("\n Number of Clusters" + numOfClusters + " Number of Pages" + numberOfPages + " Number of Kewords " + numberofKwords);
        // Get the Search Results from ResultSimulation (Simulation)
        page = new Page[numberOfPages];
        
        ResultSimulation rs = new ResultSimulation();
        // retrieve the results from the D.B    
        page = rs.getSearchResults();


    }

    public static void main(String[] args) {
// ******************** Step1 : Page Vectorization ********************

        Model SM = new Model(6, 40, 1000);
        // compute thet TDF-ITF for weighting count of keywords

        Page.weightedValues();

        // System.out.print("\n Step (1) Page Vectoriztion Retrieved ... ");
// ******************** Step2 : Page Clsuerting Using KMean********************
        KMeanCluster KM = new KMeanCluster(page, numberOfClusters);

        KM.doIt();

        int indices[] = KM.indices;

// assume that the indices are sorted (Matched). // customizing the pages into the proper cluster.
        double c = indices[0];
        Vector[] cluster = new Vector[numberOfClusters];

        for (int k = 0; k < numberOfClusters; k++) {
            cluster[k] = new Vector();
            for (int i = 0; i < indices.length; i++) {
                if (indices[i] == k) {
                    cluster[k].add(page[i]);
                }
            }
        } // end of Loop k


// --- Just for printing the pages of each cluster .
////
//
//        Page x;
//        int k = 0;
//        while (k < numberOfClusters) {
// //--- Sorting the clusters -----
//            KM.ClusterSorting(cluster[k]);
//            System.out.println("\n******************Cluster of (" + k + ")******************");
//            for (int j = 0; j < cluster[k].size(); j++) {
//                System.out.println();
//                x = (Page) cluster[k].elementAt(j);
//
//                System.out.println(x.getURL());
//            }
//            k++;
//        }
//        System.out.println();
//


// --- Sorting element inside the cluster --- // implement Bubble sort Method
        int n = 0;
        while (n < numberOfClusters) {
            KM.ClusterSorting(cluster[n]);
            n++;
        }
// ******************** Step3 : Page Clsuerting Using Genetic Algorithm********************

//        double average = 0;
//        int sum = 0;
//
//        for (int i = 0; i < 10; ++i) {
////
        GeneticAlgorithm s = new GeneticAlgorithm(cluster, 20, numberOfPages);

        s.start();

//            sum = sum + s.numRuns;
//            average = (double) sum / (double) (i + 1);
////            System.out.println("Number of runs: " + s.numRuns);
//        }
//        System.out.println("average runs: " + average);

    } // end of main method.
} // end of the main class.

