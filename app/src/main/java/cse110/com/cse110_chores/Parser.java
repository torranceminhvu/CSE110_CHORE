package cse110.com.cse110_chores;

/**
 * Created by Matthew on 11/22/2015.
 */
public class Parser {
    Chores toParse;
    int parsed;

    public Parser(){
    }

    public int parse(Chores chore){
        String current;

        this.toParse = chore;
        current = toParse.getFrequency();

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
