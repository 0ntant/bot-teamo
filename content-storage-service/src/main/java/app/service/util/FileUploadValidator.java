package app.service.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

@Slf4j
public class FileUploadValidator
{
    private static final Set<String> ALLOWED_MIME_TYPES = Set.of(
            "image/png",
            "image/jpeg"
    );

    public static boolean isValidImage(Path filePath)
    {
        try
        {
            String mimeType = Files.probeContentType(filePath);
            if (mimeType == null || !ALLOWED_MIME_TYPES.contains(mimeType))
            {
                log.error("INVALID_MIME_TYPE={}", mimeType);
                return false;
            }

            return isImageContentValid(filePath);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
            return false;
        }
    }

    private static boolean isImageContentValid(Path filePath)
    {
        try
        {
            return ImageIO.read(filePath.toFile()) != null;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
            return false;
        }
    }
}
