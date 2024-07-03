package gen;

public class HttpGen {
    public static void clearCookie(String cookieName) {
        System.out.println("Content-Type: text/html; charset=UTF-8");
        System.out.println("Set-Cookie: " + cookieName + "=; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT; Path=/hangman.cgi");
        System.out.println();
    }

    public static String[] getCookie(String cookieName,String[] cookies) {
        for (String ck : cookies) {
            String[] nv = ck.split("=");
            if (nv[0].equals(cookieName)) {
                return nv;
            }
        }
        return null;
    }

    public static void setCookie(String name,String data) {
        System.out.println("Content-type: text/html; charset=UTF-8");
        System.out.println("Set-Cookie: " + name + "=" + data + "; Path=/hangman.cgi");
        System.out.println();
    }

    public static void printHTTPHeader() {
        System.out.println("Content-type: text/html; charset=UTF-8");
        System.out.println();
    }
}
