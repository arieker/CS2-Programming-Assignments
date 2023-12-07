import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class GreedyChildren {
    private int[] greedyFactors;
    private int[] sweetnessFactors;
    private int happyChildren;
    private int angryChildren;

    public GreedyChildren(int numCandies, int numChildren, String greedyFile, String sweetnessFile) {
        this.greedyFactors = new int[numChildren];
        this.sweetnessFactors = new int[numCandies];
        this.happyChildren = 0;
        this.angryChildren = 0;
        read(greedyFile, sweetnessFile);
    }

    public void read(String greedyFile, String sweetnessFile) {
        try {
            Scanner greedyScanner = new Scanner(new File(greedyFile));
            Scanner sweetnessScanner = new Scanner(new File(sweetnessFile));

            int greedyLength = greedyFactors.length;
            for (int i = 0; i < greedyLength; i++) {
                greedyFactors[i] = greedyScanner.nextInt();
            }

            int sweetLength = sweetnessFactors.length;
            for (int i = 0; i < sweetLength; i++) {
                sweetnessFactors[i] = sweetnessScanner.nextInt();
            }

            greedyScanner.close();
            sweetnessScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public void greedyCandy() {
        Arrays.sort(greedyFactors);
        Arrays.sort(sweetnessFactors);

        int i = 0;
        int j = 0;

        int greedyLength = greedyFactors.length;
        int sweetLength = sweetnessFactors.length;
        while (i < greedyLength && j < sweetLength) {
            if (greedyFactors[i] <= sweetnessFactors[j]) {
                happyChildren++;
                i++;
                j++;
            } else {
                j++;
            }
        }

        angryChildren = greedyLength - happyChildren;

    }

    public void display() {
        System.out.println("There are " + happyChildren + " happy children.");
        System.out.println("There are " + angryChildren + " angry children.");
    }
}