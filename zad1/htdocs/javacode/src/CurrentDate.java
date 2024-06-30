import gen.HtmlGen;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;

public class CurrentDate{
    static String errorMsg(){
        String errorMsg = "INVALID OPERATION - the service only functions " +
                "either without any parameters or with " +
                "the parameter \"time\", used with or without a value";
        return HtmlGen.generateSimpleHtml("Current Date and Time",new String[]{errorMsg});
    }
    public static void main(String[] args){
        String queryString = System.getenv("QUERY_STRING");
        if(queryString == null || queryString.isEmpty()){
            String currentTime = String.valueOf(LocalDate.now(ZoneId.of("UTC+2")));
            String document = HtmlGen.generateSimpleHtml("Current Date",new String[]{currentTime});
            System.out.println(document);
        }
        else {

            String[] params = queryString.split("&");
            if(params.length > 1){
                System.out.println(errorMsg());
                return;
            }
            String[] keyval = params[0].split("=");
            if(!keyval[0].equals("time")){
                System.out.println(errorMsg());
                return;
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            String currentTime = LocalDateTime.now(ZoneId.of("UTC+2")).format(formatter);
            System.out.println(HtmlGen.generateSimpleHtml("Current Date and Time",
                    new String[]{currentTime}));
        }
    }
}