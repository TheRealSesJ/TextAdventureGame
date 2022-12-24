package com.sesj.Interfaces;

import java.util.Scanner;

public interface ArtObject {

    public String getArt();

    default String appendArt(String string, String art){
        //set up variables
        int maxLength = maxLength(art);
        int offset = offset(string);
        Scanner scanStats = new Scanner(string).useDelimiter("\n");
        Scanner scanArt = new Scanner(art).useDelimiter("\n");
        StringBuffer statsBuffer = new StringBuffer();
        while(scanStats.hasNext()){
            String appendedString = scanStats.next();
            statsBuffer.append(appendedString);
            if(scanArt.hasNext()){
                statsBuffer
                        .append(" ".repeat(offset- appendedString.length()))
                        .append(scanArt.next())
                        .append("\n");
            } else {
                statsBuffer.append("\n");
            }
        }
        //if art not finished add rest
        while(scanArt.hasNext()){
            statsBuffer
                    .append(" ".repeat(offset))
                    .append(scanArt.next())
                    .append("\n");
        }
        return statsBuffer.toString();
    }


    default String packageArt(String art){
        //set up variables
        int maxLength = maxLength(art);
        boolean artEnded = false;
        Scanner scanArt = new Scanner(art).useDelimiter("\n");
        StringBuffer artBuffer = new StringBuffer();
        //append top border
        artBuffer
                .append("_".repeat(maxLength+4))
                .append("\n")
                .append("| ")
                .append(" ".repeat(maxLength))
                .append(" |")
                .append("\n");
        //middle
        while(scanArt.hasNext()){
            String appendedArt = scanArt.next();
            artBuffer
                    .append("| ")
                    .append(appendedArt)
                    .append(" ".repeat(maxLength - appendedArt.length()))
                    .append(" |")
                    .append("\n");
        }
        //end
        artBuffer
                .append("| ")
                .append(" ".repeat(maxLength))
                .append(" |")
                .append("\n")
                .append("-".repeat(maxLength+4))
                .append("\n");
        return artBuffer.toString();
    }

    private int maxLength(String art){
        Scanner scanArt = new Scanner(art).useDelimiter("\n");
        int maxLength =0;
        while(scanArt.hasNext()){
            String str = scanArt.next();
            if(str.length()>maxLength) maxLength = str.length();
        }
        return maxLength;
    }

    private int offset(String str){
        Scanner scanString = new Scanner(str).useDelimiter("\n");
        int max = 0;
        while(scanString.hasNext()){
            String current = scanString.next();
            if(current.length()>max) max = current.length();
        }
        return max;
    }
}
