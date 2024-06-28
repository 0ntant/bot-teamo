package auto.reg.util;

import integration.dto.ImgAvaDto;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class TempPhotoFileUtil
{
    private static String tempImgDir = new File(".").getAbsolutePath() + "/src/main/resources/temp/photos";

    public static String saveFromImgAvaDto(ImgAvaDto imgAvaDto)
    {
        return save(imgAvaDto.getName(), Base64Util.decode(imgAvaDto.getBase64().getBytes()));
    }

    public static String save(String name, byte[] bytes)
    {
        String pathToSave = getFilePathByName(name);
        try
        {
            Files.write(Path.of(pathToSave), bytes);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return name;
    }

    public static void removeByName(String name)
    {
        try
        {
            Files.delete(Path.of(getFilePathByName(name)));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String getFilePathByName(String name)
    {
        return  String.format("%s/%s", tempImgDir, name);
    }
}
