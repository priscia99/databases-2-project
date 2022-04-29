package it.polimi.db2_project.TELCOEJB.utils;
import java.util.Random;
public class Utils {
    public static Boolean pay(){
        Random rand = new Random();
        return rand.nextInt(2) == 1;

    }
}
