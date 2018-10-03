import java.util.ArrayList;

public class LostConsonants
{
    public static void main(String args[])
    {
        
        switch(args.length)
        {
            case 0:
                System.out.println("Expected 2 command line arguments and received 0. \nPlease provide a dictionary as the first argument and a sentence as the second argument");
                break;

            case 1:
                System.out.println("Expected 2 command line arguments and received 1. \nPlease provide a sentence as the second argument");
                break;

            case 2:
                boolean flag = false;
                boolean flag2 = false;
                ArrayList<String> lines = FileUtil.readLines(args[0]);//dictionary is unique 
                String in = args[1].toLowerCase();
                if(in.matches(".*[b-df-hj-np-tv-z].*+"))//input string has something in it and has consonants
                {
                    String words[] = in.split(" ");//create string array of the words without spaces
                    boolean inDict[] = new boolean[words.length];//create parallel array for flagging words as in dictionary before taking consonants out
                    
                    for(int x = 0; x < words.length; x++)//set parallel array as true if in dict
                    {
                        inDict[x] = false;
                        if(lines.contains(words[x]))
                        {
                            inDict[x] = true;
                        }
                    }
                    int counter = 0;
                    int counter2 = 0;
                    for(int u = 0; u < inDict.length; u++)
                    {
                        if(!inDict[u])
                        {
                            counter++;
                        }   
                    }
                    if(counter == inDict.length)//no words are in the dictionary prior
                    {
                        flag = true;
                    }
                    for(int i = 0; i < words.length; i++)//iterate through array of words
                    {

                        for(int j = 0; j < words[i].length(); j++)//check chars in word
                        {
                            StringBuilder inPhrase = new StringBuilder(words[i]);
                            String temp = String.valueOf(words[i].charAt(j));//create temp char and set to string because I want to use regex
                            if(temp.matches(".*[b-df-hj-np-tv-z].*+"))//if char is a consonant, delete and search dict
                            {
                                inPhrase.deleteCharAt(j);
                                if(lines.contains(inPhrase.toString()))//if in dictionary
                                {
                                    counter2++;
                                    for(int b = 0; b < words.length; b++)//iterate through the words printing all in dictionary
                                    {
                                       if(b != i && inDict[b])
                                       {
                                           System.out.print(words[b] + " ");
                                       }
                                       else
                                       {
                                           System.out.print(inPhrase + " ");
                                       }

                                    }
                                    System.out.println("");
                                    
                                }

                            }
                        }
                    }
                    if(counter2 == 0)
                    {
                        flag2 = true;
                    }
                    if(flag2)
                    {
                        System.out.println("Could not find any alternatives");
                    }
                    if(flag && flag2)
                    {
                        System.out.println("Could not find any alternatives for your made-up word(s)");
                    }
                    System.out.println("Found " + counter2 + " alternatives");
                }
                else//only vowels check
                {
                    System.out.println("Your input is only vowels. Let's see if it's in the dictionary.");
                    if(lines.contains(in))
                    {
                        System.out.println("Congrats! Here is your input: " + in);
                        
                    }
                }
                break;
            case 3:
                System.out.println("Expected 2 arguments. You entered 3.\nPlease provide a dictionary as the first argument and a sentence as the second argument");
                break;
            
            default:
                System.out.println("You are throwing in a zany number of arguments. Please only use 2. The first is the dictionary, the second is the sentence to analyze");
                break;
        }
        

    }
}