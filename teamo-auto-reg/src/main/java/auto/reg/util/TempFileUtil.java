package auto.reg.util;

import java.nio.file.Files;
import java.nio.file.Path;

public class TempFileUtil
{
    public static String save(String filePath, byte[] bytes)
    {
        try
        {
            Files.write(Path.of(filePath), bytes);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return filePath;
    }

    public static void remove(String filePath)
    {
        try
        {
            Files.delete(Path.of(filePath));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
