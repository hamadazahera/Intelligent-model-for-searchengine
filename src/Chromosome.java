
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

//    import java.lang.Comparable;

/*
 * 
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
/**
 * 
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class Chromosome implements Comparable {

    // set of Genes (zeros , ones ).
    protected String bitString;
    
    public Chromosome() {
    }
// Value : Decimal Value , Length

    public Chromosome(long value, int length) {
       
        bitString = convertIntToBitString(value, length);
    }

    public void setBitString(String s) {
        bitString = s;
    }

    public String getBitString() {
        return bitString;
    }

    public int compareTo(Object o) {
        Chromosome c = (Chromosome) o;
        int num = countOnes(this.bitString) - countOnes(c.getBitString());
        return num;
    }
// Fitness Function is calculated as count of ones : T1(C) , T2(C) , T3(C)
//	public int fitness(){
//		return countOnes(bitString);
//	}

// ----- Old Fitness Function -------    
    public double fitness1() {

        Page.weightedValues();
        
// Normalization Parameters
        double alpha = 1;
        double beta = 50;
        double gama = 1;

        double fitness = 0;

        int SumOfScores = 0;
        double SumofED = 0;
        double T1;
        double T2;
        double T3;

        int countOnes = 0;
 // Page Index : 12334455 in the Chromosome   
        String PagesIndex = "";
// Calcuate Term 1 : Sum of Pages scores in the chromosome.
        String s = this.getBitString();

        for (int i = 0; i < s.length(); i++) {

            if (s.charAt(i) == '1') {
                SumOfScores += Model.page[i].getScore();
                countOnes++;
                PagesIndex += i;
            }
        }
        T1 = SumOfScores;
// Calcuate Term 2 : Total Number of Pages in the population / (Difference of Dimensions+1)
//        int np = GeneticAlgorithm.NUM_CHROMOSOMES * GeneticAlgorithm.MAX_POWER;
        int np=1000;
        int dem = Math.abs(countOnes - GeneticAlgorithm.MAX_POWER) + 1;
        T2 = np / dem;
// Calcuate Term 3 : Sum of Euclidan Distance of pages
        int index1;
        int index2;

        for (int i = 0; i < PagesIndex.length(); i++) {
            index1 = Integer.parseInt(PagesIndex.charAt(i) + "");
            for (int j = i + 1; j < PagesIndex.length(); j++) {
                index2 = Integer.parseInt(PagesIndex.charAt(j) + "");
                SumofED += KMeanCluster.euclid(Page.weightValues[index1],Page.weightValues[index2]);//SemanticModel.Page[index1].kWordsFreq, SemanticModel.Page[index2].kWordsFreq);
            }
        }
        T3 = SumofED;

// ********** Calculate The Fitness Function

        fitness = alpha * T1 + beta * T2 + gama * T3;

        NumberFormat formmatter = new DecimalFormat("#.###");
        fitness = Double.parseDouble(formmatter.format(fitness));

        return fitness;
    }
    
 // -- ** **  New Fitness Function FF(C)= A*T1(C)+B*T2(C)+C*T3(C)+D*T4(C). ** **
    
    public double newFitness() 
    {
        
   Page.weightedValues();
   
   long fitness = 0;
   int countOnes = 0;
   int SumOfScores=0;
   
   double  sumJacc=0;
   
   double T1=0;
   double T2=0;
   double T3=0;
   double T4=0;
   
   int totalKeywords=0;

// Normalization Parameters
        double alpha = 1;
        double beta = 50;
        double gama = 1;
        double delta=1;
// Page Index : 12334455 in the Chromosome   
        String PagesIndex = "";
// Calcuate Term 1 : Sum of Pages scores in the chromosome.
        String s = this.getBitString();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                SumOfScores += Model.page[i].getScore();
                totalKeywords+=Model.page[i].countKeyWords();
                countOnes++;
                PagesIndex += i;
            }
        }
        T1 = SumOfScores;
// Calcuate Term 2 : Total Number of Pages in the population / (Difference of Dimensions+1)
        int np = GeneticAlgorithm.NUM_CHROMOSOMES * GeneticAlgorithm.MAX_POWER;
        int dem = Math.abs(countOnes - GeneticAlgorithm.MAX_POWER) + 1;
        T2 = np / dem;       
// Compute Term 3: Jaccard Coefficient. // using Term Wieght Frequency       
        int index1;
        int index2;
        for (int i = 0; i < PagesIndex.length(); i++) {
            index1 = Integer.parseInt(PagesIndex.charAt(i) + "");
            for (int j = i + 1; j < PagesIndex.length(); j++) {
                index2 = Integer.parseInt(PagesIndex.charAt(j) + "");
            // computing the Jaccard co-efficient with Term Weight Frequency for better similarity Measure    
                sumJacc += KMeanCluster.euclid(Page.weightValues[index1],Page.weightValues[index2]);//SemanticModel.Page[index1].kWordsFreq, SemanticModel.Page[index2].kWordsFreq);
            }
        }
        T3 = sumJacc;       
   //Compute Term 4:  counting the number of keywords in the chromosomes   
        T4=totalKeywords;           
    fitness=(long) (alpha*T1+beta*T2+gama*T3+delta*T4);  
    return fitness;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Chromosome) {
            Chromosome c = (Chromosome) o;
            return c.getBitString().equals(bitString);
        }
        return false;
    }
    @Override
    public int hashCode() {
        return bitString.hashCode();
    }
    @Override
    public String toString() {
        return "Chromosome: " + bitString;
    }
// Calculate the   Fitness Function (number of ones )
    public static int countOnes(String bits) {
        int sum = 0;
        for (int i = 0; i < bits.length(); ++i) {
//			String test = bits.substring(i, i+1);
//			if(test.equals("1")){
//				sum = sum + 1;
//			}

            if (bits.charAt(i) == '1') {
                sum += 1;
            }
        }
        return sum;
    }
// Convert Decimal to binary value

    public static String convertIntToBitString(long val, int length) {
        long reval = val;

        StringBuilder bitString = new StringBuilder(length);
        for (int i = length - 1; i >= 0; --i) {
            if (reval - (Math.pow(2, i)) >= 0) {
                bitString.append("1");
                reval = (int) (reval - Math.pow(2, i));
            } else {
                bitString.append("0");
            }
        }
        return bitString.toString();
    }
// This Function to set bit value ... This function need to be test !!!

    public void setBitValue(int index, long value) {

        String s = this.getBitString();
        long[] c = new long[s.length()];

   // convert from String to Bits of ones and zeros
        for (int i = 0; i < c.length; i++) {
            c[i] = Integer.parseInt(s.charAt(i) + "");
        }

        c[index] = value;

        String s2 = "";

        for (int i = 0; i < c.length; i++) {
            s2 += c[i];
        }

        this.setBitString(s2);
    }
// This funtion to display the URL of pages inside the Chromosome

    public void displayPages() {      
        Vector scores=new Vector();
        String s = this.getBitString();
        for (int i = 0; i < Model.numberOfPages; i++) {
            if (s.charAt(i) == '1') {

                scores.add(Model.page[i]);
            }
        }
System.out.print("\nPageID \t URL \t\t\t Score \t\t Relevance Ratio");
        for(int i=0;i<scores.size();i++)
        {
            KMeanCluster.ClusterSorting(scores);
            System.out.print("\n" +((Page)scores.elementAt(i)).PageID+" \t  "+((Page)scores.elementAt(i)).URL+" \t \t "+((Page)scores.elementAt(i)).score+"\t \t"+((Page)scores.elementAt(i)).ratio+" %");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        //System.out.println(convertIntToBitString(2046, 10));
        Chromosome c = new Chromosome(1234, 10);
        c.setBitValue(3, 0);

        System.out.println(c.newFitness());
    }
}
