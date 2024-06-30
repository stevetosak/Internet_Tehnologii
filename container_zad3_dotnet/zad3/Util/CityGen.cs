public class CityGen{
    private  string [] cities = new string[]{"RESEN","STRUMICA","KICEVO","OHRID","SKOPJE","VALANDOVO","MAVROVO"};
    private readonly Random random = new Random();
    public String getRandomCity(){
        return cities[random.Next(0, cities.Length)];
    }
}