package fr.cerbere.screenapp;

public class Utils {

    public static boolean detectChange(int[] oldCodes, int[] newCodes){
        if(oldCodes != null){
            if(oldCodes[0] != newCodes[0]){
                return true;
            } else if(oldCodes[1] != newCodes[1]){
                return true;
            } else if(oldCodes[2] != newCodes[2]){
                return true;
            }
        } else {
            return true;
        }
        return false;
    }
}
