package app.service.util;

import java.util.Base64;

public class Base64Util
{
    public static byte[] getBase64(byte[] bytes)
    {
        return Base64.getEncoder().encode(bytes);
    }

    public static byte[] getBytesFromBase64(byte[] bytes)
    {
        return Base64.getDecoder().decode(bytes);
    }
}
