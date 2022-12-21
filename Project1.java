//Fernando Vargas
//Project 1: Stable Matching
//9/30/2022

import java.util.*;
import java.io.*;

public class Project1 {

    //set 2D Arrays for men and women
    int[][] men;
    int[][] women;
    int[][] couples;

    int[][] freeM;

    int[][] freeW;

    //global scanner
    Scanner input;

    //set object n and menSize and womenSize using Integer to later parseInt to get int
    Integer n;
    Integer menSize, womenSize;


    //setup try and catch for new File input.txt
    public Project1(){
        try {
            input = new Scanner(new File("input.txt"));
        }   catch (FileNotFoundException e){
            System.out.println("input.txt NOT FOUND.\n\n");
        }

        //return Integer to int using parseInt
        n = Integer.parseInt(input.next());
        //set parameters for menSize to n+1 and women to n+1
        menSize = n+1;
        womenSize = n+1;
    }



    //method to createMen and set algorithm to show us the current man and there preference list
    public void createMen(){
        //set men to 2d array of row# menSize and column# womenSize
        men = new int[menSize][womenSize];
        //print out initial menSize and womenSize
        System.out.println("menSize: " + menSize + "\nwomenSize: " + womenSize);
        //set current man and there preference
        int currMan = 1;
        int currPreff = 1;
        //while loop for 1 < menSize
        while(currMan < menSize){
            //nested while loop for preference < womenSize
            while(currPreff < womenSize){
                //return men 2D array and increment preference by 1 to loop again and find next int
                men[currMan][currPreff++] = input.nextInt();
                //print out currMan and currPreff(men)
                System.out.println("\ncurrMan: " + currMan + "\ncurrPreff: " + currPreff);
            }
            //after nested while loop exits increment currMan + 1 and set currPreff to 1 to restart
            currMan++;
            currPreff = 1;
        }



    }

    //method to print out the men data and shows there current preference
    public void printMen(){
        //set current man and there preference
        int currMan = 1;
        int currPreff = 1;

        while(currMan < menSize){
            //print out man 1,2,3
            System.out.println("Man #" + currMan + ": ");
            //nested while loop for printing out man's prefereance
            while(currPreff < womenSize){
                System.out.println(men[currMan][currPreff++] + " PREF ");
            }

            System.out.println("\n");
            //after nested while loop exits increment currMan + 1 and set currPreff to 1 to restart
            currMan++;
            currPreff = 1;
        }


    }




    //method to createWomen and set algorithm to show us the current man and there preference list
    public void createWomen(){
        women = new int[womenSize][menSize];
        System.out.print("womenSize: " + womenSize + "\nmenSize: " + menSize);
        int currWomen = 1;
        int currWPreff = 1;
        while(currWomen < womenSize) {
            while(currWPreff < menSize) {
                women[currWomen][currWPreff++] = input.nextInt();
                System.out.println("\ncurrWomen: " + currWomen + "\ncurrWPreff: " + currWPreff);

            }
            currWomen++;
            currWPreff = 1;

        }

    }


    //method to print out the women data and shows there current preference
    public void printWomen(){
        int currWomen = 1;
        int currWPreff = 1;
        while(currWomen < womenSize) {
            System.out.println("Woman # "+ currWomen + ": ");
            while(currWPreff < menSize) {
                System.out.println(women[currWomen][currWPreff++] + " PREF ");
            }
            System.out.println("\n");
            currWomen++;
            currWPreff = 1;
        }


    }

    private int getNextUnengagedMan(){
        int menIndex = -1;
        for(int i = 1; i < menSize; i++){
            for(int j = 1; j < womenSize; j++){
                if(couples[i][j] == 1){
                    break;
                }
                else if(j == womenSize-1){
                    return i;
                }

            }
        }
        return menIndex;
    }

    public void stableMarriage (){
        couples = new int[menSize][womenSize];
        int currMan, loop = 1, currWoman, currManPref, currWomenPref;

        while(true){
            //while there exists an unengaged man.
            System.out.println("Entering Stable Marriage loop #" + loop++ + "\n");
            /////////////////////////////////////////////////////////////////////////////////
            //exit strategy
            currMan = getNextUnengagedMan();
            if(currMan == -1) {
                System.out.println("\nAll Marriages have been Stabilized. Exiting Algorithm.\n");
                break;
            }
            // entering stable matching part since there is an unengaged man.\
            int cwIndex = 1;
            while(true) {
                currWoman = men[currMan][cwIndex];
                //we look for woman
                if(currWomanIsEngaged(currWoman)) {
                    if(newManIsPreferred(currMan, currWoman)){
                        //changeEngagement();
                        int oldMan = getCurrMan(currWoman);
                        int newMan = currMan;
                        couples[oldMan][currWoman] = 0;
                        couples[newMan][currWoman] = 1;
                        break;
                    }
                    else{
                        cwIndex++;
                    }
                }
                else{
                    couples[currMan][currWoman] = 1;
                    break;
                }
            }
            //end while
        }
    }

    private int getCurrMan(int cw){
        for(int i = 1; i < menSize; i++){
            if(couples[i][cw] == 1)
                return i;
        }
        return -1;
    }
    private boolean newManIsPreferred(int nm, int cw){
        int nmIndex = -1, currManIndex = -1;
        int herCurrMan = getCurrMan(cw);
        for(int i = 1; i < menSize; i++){
            if(women[cw][i] == nm){
                nmIndex = i;
            }
            else if(women[cw][i] == herCurrMan){
                currManIndex = i;
            }
        }
        return (nmIndex < currManIndex);
    }
    private boolean currWomanIsEngaged(int cw){
        for(int i = 1; i < menSize; i++){
            if(couples[i][cw] == 1)
                return true;
        }
        return false;
    }
    public void printResults(){
        System.out.println("\n\n\tResults:\n");
        for(int i = 1; i < menSize; i++){
            for(int j = 1; j < womenSize; j++){
                if(couples[i][j] == 1)
                    System.out.println("Man(" + i + ") --> Woman(" + j + ").");
            }
        }
    }
    public static void main(String[]args){
        Project1 proj1 = new Project1();
        proj1.createMen();
        proj1.printMen();
        proj1.createWomen();
        proj1.printWomen();
        proj1.stableMarriage();
        proj1.printResults();
    }
}