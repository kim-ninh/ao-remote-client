package com.ninhhk.aoremote.Utils;

import androidx.annotation.NonNull;

public class IRUtils {

    // patern from IRDB.tk: xxxx xxxx xxxx ...
    // x = {0:9, A:F}

    public static final byte[] prontoHexToBytes(@NonNull String prontonHex) {
        prontonHex = clearAllSpace(prontonHex);
        byte[] bytes = new byte[prontonHex.length() / 2];

        for (int i = 0; i < prontonHex.length(); i += 2) {
            bytes[i / 2] = (byte) ((Character.digit(prontonHex.charAt(i), 16) << 4)
                    + Character.digit(prontonHex.charAt(i + 1), 16));
        }

        return bytes;
    }

    // Test
    public static void main(String[] args) {
//        int value = Character.digit('f', 16);
//        System.out.println(value);

        String str2 = "0000 12FF 55FE";
        System.out.println(clearAllSpace(str2));

        byte[] bytes = prontoHexToBytes(str2);
        for (byte b : bytes) {
            System.out.print(b);
            System.out.print(" ");
        }

    }

    // use only with above pattern
    // 3 * 4 + (3 - 1) = 14
    // totalPair * digitEachPair + (totalPair - 1) = str_len
    // => totalPair = (str_len + 1 )/(digitEachPair + 1)

    private static String clearAllSpace(@NonNull String src) {

        int totalPair = (src.length() + 1) / (4 + 1);
        char[] chars = new char[totalPair * 4];

        int i, j;
        for (i = 0, j = 0; i < src.length(); i++) {
            if (src.charAt(i) != ' ') {
                chars[j] = src.charAt(i);
                j++;
            }
        }

        return new String(chars);
    }
}
