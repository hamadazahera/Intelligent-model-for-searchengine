
import java.util.Collections;
import java.util.Vector;

/*
 * 
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
/**
 * 
 */
public class GeneticAlgorithm {

     static int NUM_CHROMOSOMES = 20;
// Length of Chromosome : Ideal number of pages in the chromosome.= Number of pages / number of clusters  
     static int MAX_POWER = 167;
// Max value of the chromsome which could be .
    private static int MAX_NUMBER = (int) Math.pow(2, MAX_POWER) - 1;
// To determine the final solution
    //private static int FITNESS_THRESHOLD = 5;
    private static float MUTATE = (float) .05;
     Vector population;
    private boolean done = true;
    int numRuns = 0;

    double max1=0;
    double max2=0;
 // Solution value by Fitness 1    
    Chromosome best1;
 // Solution value by Fitness 2   
    Chromosome best2;

    public GeneticAlgorithm() {

        generateRandomPopulation();
    }
// Clustered Data "Clusters" , number of the chromosome in the population , Total number pages . 
    public GeneticAlgorithm(Vector[] clusters, int NumberOFChromosomes, int TotalPages) {
        this.NUM_CHROMOSOMES = NumberOFChromosomes;
        this.MAX_POWER = TotalPages;
        generateInitialPopulation(clusters);
    }
// It should be replaced with the ranked chromosomes (Initial Population)

    public void generateInitialPopulation(Vector[] clusters) {
    System.out.println("\n *** Generating population with: " + NUM_CHROMOSOMES + " Chromosome(s)***");
         population = new Vector();
// Create N Chromosomes
        Chromosome[] c = new Chromosome[NUM_CHROMOSOMES];
        for (int i = 0; i < NUM_CHROMOSOMES; i++) {
// 0 : value , 10 : chromosome length ( total number of Pages).
            c[i] = new Chromosome(0, MAX_POWER);
        }
        
        for(int i=0;i<NUM_CHROMOSOMES;i++) {
             for(int k=0;k<clusters.length;k++)
                c[i].setBitValue(((Page) clusters[k].elementAt(i)).PageID, 1);
                      
      population.add(c[i]);
      System.out.println("Chromosome ("+i+") "+c[i].getBitString());
      
    }
    }// end of i Loop
// Randomly generate the initial population
    private void generateRandomPopulation() {
        System.out.println("***Randomly Generating population with: " + NUM_CHROMOSOMES + " Chromosome(s)***");

        population = new Vector();

        for (int i = 0; i < NUM_CHROMOSOMES; ++i) {
            // get Random Numbers between 0 and MAX_NUMBER (10)
            int randomValue = (int) (Math.random() * MAX_NUMBER);
            // create chromosome
            population.add(new Chromosome(randomValue, MAX_POWER));
        }
       System.out.println("First Population: " + population + "\n");
    }

    public void start() {
        //  to get the max fitness and compare it the final solution
        Collections.sort(population);
        Chromosome fitess = (Chromosome) population.lastElement();
       
       // done = fitess.fitness() >= MAX_POWER ? true : false;
        while(numRuns<100)
        {
//      ---- Running for the Old Fitness Function      
//            if(fitess.fitness1()>max1)
//            {
//                max1=fitess.fitness1();
//                best1=fitess;
//            }
//      ---- Running for the New Fitness Function            
            if(fitess.newFitness()>=max2)
            {
                max2=fitess.newFitness();
                best2=fitess;
            }              
            numRuns++;
//    System.out.println(">>>>> Chromosome with Max Old_Fitness in iteration ( "+numRuns+" ) : "+ fitess.fitness1());
    System.out.println(">>>>> Chromosome with Max New_Fitness in iteration ( "+numRuns+" ) : "+ fitess.newFitness());
           
            generateNewPopulation();
           
             Collections.sort(population);
            fitess = (Chromosome) population.lastElement();

        }
//         System.out.println("DONE, solution found: Its Old Fitness : "+max1);
//         best1.displayPages();
         
        System.out.println("*******************************************NEW Fitness Function ***********************");
         System.out.println("DONE, solution found: Its new Fitness : "+max2);
         best2.displayPages();
     
//        if (done) {
//            System.out.println("DONE, solution found: " + fitess);
//        } else {
//            numRuns++;
//            System.out.println("FITESS: " + fitess + " fitness: " + fitess.fitness1());
//            generateNewPopulation();
//            start();
//        }
    }

    private void generateNewPopulation() {

        // System.out.println("***Generating New Population");
        Vector temp = new Vector();

        for (int i = 0; i < population.size() / 2; ++i) {

            // GA Operators : Selection / Reproduction
            Chromosome p1 = selectParent();
            Chromosome p2 = selectParent();
            // GA Operators : Crossover .. This is not randomly mating
            temp.add(cross1(p1, p2));
            temp.add(cross2(p1, p2));
        }

        population.clear(); //empty the population
        population.addAll(temp);
   //Printing the contents of the chromosome .         
        for(int i=0;i<population.size();i++)
        {
         System.out.println("Chromosome ["+i+"]"+((Chromosome)population.elementAt(i)).getBitString());
        }
    }
// ****************** GA Operator : Reproduction *******************************

    private Chromosome selectParent() {
        int delta = population.size();
        delta = NUM_CHROMOSOMES - NUM_CHROMOSOMES / 2;

        int num = (int) (Math.random() * 10 + 1);
        int index;

        if (num >= 4) {
            index = (int) (Math.random() * delta + NUM_CHROMOSOMES / 2);
        } else {
            index = (int) (Math.random() * delta);
        }

        return (Chromosome) population.get(index);
    }
// ****************** GA Operator : CrossOver *******************************

    private Chromosome cross1(Chromosome parent1, Chromosome parent2) {
        String bitS1 = parent1.getBitString();
        String bitS2 = parent2.getBitString();
        int length = bitS1.length();
// Cutting point is length/2 (Middle) : swap the first part of P1 with the second part of P2.
        String newBitString = bitS1.substring(0, length / 2) + bitS2.substring(length / 2, length);
        Chromosome offspring = new Chromosome();
        offspring.setBitString(newBitString);

        if (shouldMutate()) {
            mutate(offspring);
        }

        return offspring;
    }

    private Chromosome cross2(Chromosome parent1, Chromosome parent2) {
        String bitS1 = parent1.getBitString();
        String bitS2 = parent2.getBitString();
        int length = bitS1.length();

        String newBitString = bitS2.substring(0, length / 2) + bitS1.substring(length / 2, length);
        Chromosome offspring = new Chromosome();
        offspring.setBitString(newBitString);

        if (shouldMutate()) {
            mutate(offspring);
        }

        return offspring;
    }

    private boolean shouldMutate() {
        double num = Math.random();
        int number = (int) (num * 100);
        num = (double) number / 100;
        return (num <= MUTATE);
    }
//************************ GA Operators : Mutation *****************************

    private void mutate(Chromosome offspring) {
        String s = offspring.getBitString();
        int num = s.length();
        int index = (int) (Math.random() * num);
        String newBit = flip(s.substring(index, index + 1));
        String newBitString = s.substring(0, index) + newBit + s.substring(index + 1, s.length());
        offspring.setBitString(newBitString);
    }

    private String flip(String s) {
        return s.equals("0") ? "1" : "0";
    }

// --- The main method : just for testing the Genetic Algorithm class independantly .
//    public static void main(String[] args) {
//        double average = 0;
//        int sum = 0;
//
//        for (int i = 0; i < 10; ++i) {
//            GeneticAlgorithm s = new GeneticAlgorithm();
//
////            s.generateInitialPopulation(clusters);
//
//            s.start();
//            sum = sum + s.numRuns;
//            average = (double) sum / (double) (i + 1);
////            System.out.println("Number of runs: " + s.numRuns);
//        }
////        System.out.println("average runs: " + average);
//    }
}
