package gen;

public class HttpGen {
    public static void clearCookie(String cookieName) {
        System.out.println("Content-Type: text/html");
        System.out.println("Set-Cookie: " + cookieName + "=; Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT");
        System.out.println();
    }

    public static String getCookie(String cookieName) {
        String[] cookies = System.getenv("HTTP_COOKIE").split(";");

        for (String ck : cookies) {
            String[] nv = ck.split("=");
            if (nv[0].equals(cookieName)) {
                return nv[1];
            }
        }
        return "NOT_FOUND";
    }

    public static void setCookie(String name,String data) {
        System.out.println("Content-Type: text/html");
        System.out.println("Set-Cookie: " + name + "=" + data);
        System.out.println();
    }

    public static void printHTTPHeader() {
        System.out.println("Content-Type: text/html");
        System.out.println();
    }
}
