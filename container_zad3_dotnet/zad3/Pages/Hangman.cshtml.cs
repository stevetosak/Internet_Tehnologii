using System.Runtime.CompilerServices;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

public class HangmanModel : PageModel{

    public string? Word;
    public string? Title;
    public string? Description = null;

    static HangmanGameManager hangmanGameManager = new HangmanGameManager();

    private bool ValidateInput(string? param){
        if(string.IsNullOrEmpty(param)){
            Title = "HELP";
            Description = "INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 with a single alphabetical character as a value and will output if you guessed the char correctly (if an imagined string contains the guessed character and at which positions)";
            return false;
        } else if (param.Length != 1 || !char.IsLetter(param[0])){
             Title = "ERROR";
             Description = "INVALID OPERATION - the hangman service expects a GET or POST parameter operand1 with a single alphabetical character as a value and will output if you guessed the char correctly (if an imagined string contains the guessed character and at which positions)";
             return false;
        } else{
            Title = "zad3";
            return true;
        }
    }

    public IActionResult OnPost(){
        string? input = Request.Form["operand1"];
        HandleRequest(input);

        return Page();
    }
    public IActionResult OnGet(){
        string? input = Request.Query["operand1"];
        HandleRequest(input);
        return Page();
    }

    public void HandleRequest(string? input){
        if(ValidateInput(input)){
            string? cookieVal = Request.Cookies["word"];
            Word = hangmanGameManager.updatePage(cookieVal,char.ToUpper(input[0]),Response);
        }
    }



}