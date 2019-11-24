package com.ninhhk.aoremote;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class InputStreamUtils {
    public static List<String> getString(InputStream is) {
        LineNumberReader reader = new LineNumberReader(new InputStreamReader(is));
        List<String> strings = new ArrayList<>();


        while (true) {
            try {
                String line = reader.readLine();
                if (line == null)
                    break;

                strings.add(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return strings;
    }
}
