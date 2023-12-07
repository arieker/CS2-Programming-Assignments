public class SequenceAlignment {
    private String sequence1;
    private String sequence2;

    public SequenceAlignment(String sequence1, String sequence2) {
        this.sequence1 = sequence1;
        this.sequence2 = sequence2;
    }

    public void computeAlignment(int baseCost) {
        int str1Length = sequence1.length();
        int str2Length = sequence2.length();
        int[][] arr = new int[str2Length + 1][str1Length + 1];

        for (int i = 0; i <= str1Length; i++) {
            arr[0][i] = i * baseCost;
        }

        for (int i = 0; i <= str2Length; i++) {
            arr[i][0] = i * baseCost;
        }

        for (int i = 1; i <= str2Length; i++) {
            for (int j = 1; j <= str1Length; j++) {
                int substitutionCost = mismatchCost(sequence1.charAt(j - 1), sequence2.charAt(i - 1));
                arr[i][j] = Math.min(Math.min(arr[i - 1][j - 1] + substitutionCost, arr[i - 1][j] + baseCost),
                        arr[i][j - 1] + baseCost);
            }
        }

        // Now retrace for the solution
        int i = str2Length;
        int j = str1Length;
        StringBuilder alignedSeq1 = new StringBuilder();
        StringBuilder alignedSeq2 = new StringBuilder();

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && arr[i][j] == arr[i - 1][j - 1] + mismatchCost(sequence1.charAt(j - 1), sequence2.charAt(i - 1))) {
                // Diagonal move
                alignedSeq1.insert(0, sequence1.charAt(j - 1));
                alignedSeq2.insert(0, sequence2.charAt(i - 1));
                i--;
                j--;
            } else if (i > 0 && arr[i][j] == arr[i - 1][j] + baseCost) {
                // Move up
                alignedSeq1.insert(0, '-');
                alignedSeq2.insert(0, sequence2.charAt(i - 1));
                i--;
            } else {
                // Move left
                alignedSeq1.insert(0, sequence1.charAt(j - 1));
                alignedSeq2.insert(0, '-');
                j--;
            }
        }

        sequence1 = alignedSeq1.toString();
        sequence2 = alignedSeq2.toString();
    }

    private int mismatchCost(char x, char y) {
        if (x == y) {
            return 0;
        } else if ((isVowel(x) && isVowel(y)) || (isConsonant(x) && isConsonant(y))) {
            return 1;
        } else {
            return 3;
        }
    }

    private static boolean isVowel(char x) {
        return "aeiou".indexOf(x) != -1;
    }

    private static boolean isConsonant(char x) {
        return Character.isLetter(x) && !isVowel(x);
    }

    public String getAlignment() {
        return sequence1 + " " + sequence2;
    }
}