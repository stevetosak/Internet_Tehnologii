using System.Net;
using Microsoft.Net.Http.Headers;

public class HangmanGameManager {
    private string word;
    private string defaultString;
    private Dictionary<char,List<int>> ChToPosMap = new Dictionary<char,List<int>>();


    public HangmanGameManager(){
        reset();
    }

    public string updatePage(string wordStateCookie, char ch, HttpResponse response){
        char[] currentWordChArray;
        if (string.IsNullOrEmpty(wordStateCookie)) currentWordChArray = defaultString.ToCharArray();
        else currentWordChArray = wordStateCookie.ToCharArray();

        if (ChToPosMap.ContainsKey(ch)) {
            foreach (int pos in ChToPosMap[ch]) {
                currentWordChArray[pos] = ch;
            }

        }

        string currentWordString = new string(currentWordChArray);
        CookieOptions options = new CookieOptions(){
            Path = "/hangman"
        };
        if(gameFinished(currentWordString)){
            response.Cookies.Delete("word",options);
            reset();
            return "You guessed correctly - " + currentWordString;
            
        } else {
            response.Cookies.Append("word",currentWordString,options);
            return currentWordString;
        }
    }



    private void reset() {
        ChToPosMap = new Dictionary<char, List<int>>();
        word = "СТРУМИЦА";
        defaultString = new string('_',word.Length);
        for (int i = 0; i < word.Length; i++) {
            if (!ChToPosMap.ContainsKey(word[i])) {
                ChToPosMap.TryAdd(word[i], []);
            }
            ChToPosMap[word[i]].Add(i);
        }
    }

    private bool gameFinished(string currentWordState) {
        int j = 0;
        for (int i = 0; i < currentWordState.Length; i++) {
            if (currentWordState[i] != word[j++]) {
                return false;
            }
        }
        return true;
    }
}
