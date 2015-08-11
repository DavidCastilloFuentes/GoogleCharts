package com.ma.blast.motion.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an utility class for parsing CVS files
 * among other utility methods for converting from
 * strings to numeric values
 */
public final class Parser {
    private Parser(InputStream inputStream) {
    }

    public static float getFloat(String in){
        float res =0;

        try {
            res = Float.valueOf(in);
        } catch(NumberFormatException e) {
            res = 0;
        }

        return res;
    }

    public static int getInteger(String in){
        int res =0;

        try{
            res = Integer.valueOf(in);
        }catch(NumberFormatException e){
            res = 0;
        }

        return res;
    }

    public static List<String[]> read(InputStream is) {
        List<String[]> result = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                result.add(row);
            }
        } catch (IOException ex) {
        } finally {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
        return result;
    }
}
