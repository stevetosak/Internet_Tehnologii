package internettehologii.individualnizadaci.zad2.services;

import java.util.Random;

public class CityGen{
    private final String [] cities = new String[]{"RESEN","STRUMICA","KICEVO","OHRID","SKOPJE","VALANDOVO","MAVROVO"};
    private final Random random = new Random();
    public String getRandomCity(){
        return cities[random.nextInt(cities.length)];
    }
}
