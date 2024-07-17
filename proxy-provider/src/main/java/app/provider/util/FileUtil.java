package app.provider.util;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;


public class FileUtil
{
    public static void removeNotEmptyDir(String path)
    {
        try (Stream<Path> pathStream = Files.walk(Path.of(path)))
        {
            pathStream.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new RuntimeException(ex.getMessage());
        }
    }
}
