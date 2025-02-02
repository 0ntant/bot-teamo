package app.service.service;

import app.service.util.FileUploadValidator;
import app.service.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;

@Slf4j
@Service
public class FileValidatorService
{
    @Value("${file.temp.dir}")
    String tempDir;

    public boolean isImgValid(byte[] imgData)
    {
        try
        {
           Path imgTemp = Files.createTempFile(
                    Path.of(tempDir),
                   "img-",
                   ".jpeg"
           );
           FileUtil.write(String.valueOf(imgTemp.toAbsolutePath()), imgData);

           boolean isImgValid = FileUploadValidator.isValidImage(imgTemp);
           imgTemp.toFile().delete();
           return isImgValid;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            log.error(ex.getMessage());
        }
        return false;
    }
}
