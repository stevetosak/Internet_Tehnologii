package gen;

import java.util.Random;

public class WordGenerator {
    private static String[] words = {
            "RESEN","KICEVO","STRUMICA",
            "SKOPJE","KATLANOVO","OHRID"
    };

    public static String generate(){
        return words[new Random().nextInt(words.length)];
    }

    public static void main(String[] args) {
        int numCities = Integer.parseInt(args[0]);
        System.out.println(new Random().nextInt(numCities) + 1);
    }
}
