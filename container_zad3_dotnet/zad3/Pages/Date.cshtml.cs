using System.Linq.Expressions;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.RazorPages;

namespace zad3.Pages;
public class DateModel : PageModel{

    public string? CurrentDate { get; set; }
    public string GetDate(bool time){
        DateTime utcNow = DateTime.UtcNow;
        TimeZoneInfo utcPlusTwoZone = TimeZoneInfo.FindSystemTimeZoneById("E. Europe Standard Time");
        DateTime utcPlusTwoTime = TimeZoneInfo.ConvertTimeFromUtc(utcNow, utcPlusTwoZone);

        if (!time){
            return utcPlusTwoTime.ToString("yyyy-MM-dd");
        } else {
            return utcPlusTwoTime.ToString("yyyy-MM-dd HH:mm:ss");
        }
    }

    public IActionResult OnGet(){
        string param = ParseQueryString(Request.QueryString.ToString());
        if(param.Equals("time")){
            CurrentDate = GetDate(true);
            ViewData["Title"] = "Current Date and Time";
        } else if (param.Equals("")){
            CurrentDate = GetDate(false);
            ViewData["Title"] = "Current Date";
        } else {
            return RedirectToPage("/Error");
        }

        return Page();
    }

    public string ParseQueryString(string query){
        string [] paramval = query.Split("?");
        if(paramval.Length > 1){
            return paramval[1].Split("=")[0];
        } else return "";
        
    }

}