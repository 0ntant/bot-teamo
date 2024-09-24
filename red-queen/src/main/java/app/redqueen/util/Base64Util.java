package app.redqueen.util;

import java.util.Base64;

public class Base64Util
{
    public static byte[] encode(byte[] bytes)
    {
        return Base64.getEncoder().encode(bytes);
    }

    public static byte[] decode(byte[] bytes)
    {
        return Base64.getDecoder().decode(bytes);
    }
}
