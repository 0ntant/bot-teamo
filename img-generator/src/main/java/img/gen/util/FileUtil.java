package img.gen.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtil
{
    public static int countFile(String dir)
    {
        try
        {
            File checkingDir = new File(dir);
            if (!checkingDir.exists())
            {
                return 0;
            }
            return checkingDir.listFiles().length;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return 0;
    }

    public static List<File> getFilesInDir(String dirPath)
    {
        try
        {
            File dir = new File(dirPath);
            List<File> filesIdDir = Arrays.asList(dir.listFiles());
            return filesIdDir;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }

    public static String getExt(String path)
    {
        return path.substring(path.lastIndexOf(".") + 1);
    }

    public static String getName(String path)
    {
        return path.substring(path.lastIndexOf("/") + 1);
    }

    public static byte[] getFileBytes(String path)
    {
        try
        {
            return Files.readAllBytes(Path.of(path));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    public static void removeFile(String path)
    {
        try
        {
            Files.delete(Path.of(path));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static boolean fileExits(String path)
    {
        try
        {
            return Files.exists(Path.of(path));
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

        return false;
    }

    public static File saveFile(String path, byte[] bytes)
    {
        try
        {
            Files.write(Path.of(path), bytes);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return new File(path);
    }
}
