package internettehologii.individualnizadaci.zad2.services;

public class RequestManager implements InputManager{

    private final String errorMessage;
    public RequestManager(String errorMessage){
        this.errorMessage = errorMessage;
    }
    @Override
    public String[] parseQueryString(String query){
        if(query != null){
            String[] params = query.split("&");
            if(params.length != 0){
                return params[0].split("=");
            }
        }
        return null;
    }

    @Override
    public String validateInput(String[] paramNvalue){
        if (paramNvalue == null ||paramNvalue.length < 2) return "HELP";
        if(!paramNvalue[0].equals("operand1") || paramNvalue[1].length() != 1) return "ERROR";
        return null;
        //Vrakja naslov na stranicata
    }

    @Override
    public String[] parsePostReq(String body) {
        String[] parts = body.split("&");
        return parts[0].split("=");
    }
    @Override
    public String getErrorMessage(){
        return errorMessage;
    }
}
