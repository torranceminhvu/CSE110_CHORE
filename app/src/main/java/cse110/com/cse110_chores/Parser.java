package cse110.com.cse110_chores;

/**
 * Parse strings of frequency to ints
 */
public class Parser {
    //vars
    Chores toParse;
    int parsed;

    // empty constructor
    public Parser(){
    }

    // parse the frequency of a chore from string to int
    public int parse(Chores chore){
        String current;

        this.toParse = chore;
        current = toParse.getFrequency();

        /* check which case, and return its int form */
        if (current.equals("Weekly")) {
            parsed = 7;
        }
        else if (current.equals("Bi-weekly")){
            parsed = 14;
        }
        else if (current.equals("Monthly")){
            parsed = 30;
        }
        else if (current.equals("Daily")){
            parsed = 1;
        }

        return parsed;
    }
}
