package gen;

import java.util.Objects;

public class HtmlGen {
    public static String generateSimpleHtml(String Title, String[] bodyContent){
        StringBuilder doc = new StringBuilder("""
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>""");
        doc.append(Title);
        doc.append("</title>" + "</head>\n" + "<body>\n" );
        if(bodyContent.length != 0){
            for(String text : bodyContent){
                if(!Objects.equals(text, ""))
                    doc.append("<p id=\"data\">").append(text).append("<p>").append("\n");
            }
        }
        doc.append("</body>\n" + "</html>");

        return doc.toString();
    }
}
