package img.gen.service;

import img.gen.util.FileUtil;
import img.gen.util.ImgUtil;
import img.gen.util.StrUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class ImgProcessingService
{
    @Getter
    String tempDir;

    public ImgProcessingService(@Value("${images.temp-dir}") String tempDir)
    {
        this.tempDir = tempDir;
    }

    public byte[] horizontalFlip(byte[] imgData)
    {
        File imgTemp = saveJpegImage(imgData);
        ImgUtil.horizontalFlipImage(imgTemp.getAbsolutePath());

        return extractImgBytes(imgTemp);
    }

    public byte[] cutBottom(byte[] imgData, int size)
    {
        File imgTemp = saveJpegImage(imgData);
        ImgUtil.cutImgBottom(imgTemp.getAbsolutePath(), size);

        return extractImgBytes(imgTemp);
    }

    public byte[] cutUpper(byte[] imgData, int size)
    {
        File imgTemp = saveJpegImage(imgData);
        ImgUtil.cutImgUpper(imgTemp.getAbsolutePath(), size);

        return extractImgBytes(imgTemp);
    }

    public byte[] cutRight(byte[] imgData, int size)
    {
        File imgTemp = saveJpegImage(imgData);
        ImgUtil.cutImgRight(imgTemp.getAbsolutePath(), size);

        return extractImgBytes(imgTemp);
    }

    public byte[] cutLeft(byte[] imgData, int size)
    {
        File imgTemp = saveJpegImage(imgData);
        ImgUtil.cutImgLeft(imgTemp.getAbsolutePath(), size);

        return extractImgBytes(imgTemp);
    }

    private byte[] extractImgBytes(File imgTemp)
    {
        byte[] extractData = FileUtil.getFileBytes(imgTemp.getAbsolutePath());
        FileUtil.removeFile(imgTemp.getAbsolutePath());
        return extractData;
    }

    private File saveJpegImage(byte[] imgData)
    {
        String imgName = String.format("%stpdne.jpeg", StrUtil.genRandStr());
        try
        {
            return FileUtil.saveFile(getFullTempPath(imgName), imgData);
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    private String getFullTempPath(String imgName)
    {
        return String.format("%s/%s", getTempDir(), imgName);
    }
}
