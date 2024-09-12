package img.gen.service;

import img.gen.integration.mq.ContentStoragePub;
import img.gen.integration.rest.PersonNotExistImgClient;
import img.gen.util.FileUtil;
import img.gen.util.ImgUtil;
import img.gen.util.StrUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@Service
@Slf4j
public class ImgService
{
    @Getter
    @Value("${images.count}")
    int imageCount;

    @Getter
    @Value("${images.dir}")
    String dir;

    @Autowired
    ContentStoragePub contentStoragePub;

    @Autowired
    PersonNotExistImgClient imgPENClient;

    @Autowired
    UnsplashService unsplashService;

    @Autowired
    PexelsService pexelsService;

    @Autowired
    PixabayService pixabayService;

    @Async
    public void createImg()
    {
        log.info("Create image");
        if(getImgCurCount() <= getImageCount())
        {
            createPENCFile();
            createUnsplashPhotos();
            createPexelsPhotos();
            createPixabayPhotos();
        }
    }

    @Async
    public void createPexelsPhotos()
    {
        File femaleImg = saveJpegImage(pexelsService.getFemaleImage());
        File maleImg = saveJpegImage(pexelsService.getMaleImage());

        ImgUtil.horizontalFlipImage(femaleImg.getAbsolutePath());
        ImgUtil.horizontalFlipImage(maleImg.getAbsolutePath());
    }

    @Async
    public void createUnsplashPhotos()
    {
        File femaleImg = saveJpegImage(unsplashService.getFemaleImage());
        File maleImg = saveJpegImage(unsplashService.getMaleImage());

        ImgUtil.horizontalFlipImage(femaleImg.getAbsolutePath());
        ImgUtil.horizontalFlipImage(maleImg.getAbsolutePath());
    }

    @Async
    public void createPENCFile()
    {
        File imgToSave = saveJpegImage(imgPENClient.getImage());
        ImgUtil.cutImgBottom(imgToSave.getAbsolutePath(), 25);
    }

    @Async
    public void createPixabayPhotos()
    {
        List<byte[]> photosData = Stream.concat(
                pixabayService.getFemaleImages().stream(),
                pixabayService.getMaleImages().stream()
        ).toList();

        for (byte[] photoData : photosData)
        {
            File file = saveJpegImage(photoData);
            ImgUtil.horizontalFlipImage(file.getAbsolutePath());
        }
    }

    public String getRandImgPath()
    {
        return getRandImg().getAbsolutePath();
    }

    public File getRandImg()
    {
        return FileUtil.getFilesInDir(dir)
                .get(ThreadLocalRandom.current().nextInt(0, getImgCurCount()));
    }

    public int getImgCurCount()
    {
        return FileUtil.countFile(dir);
    }

    public boolean imgExists(String name)
    {
        return FileUtil.fileExits(getFullImgPath(name));
    }

    public String sendToCSS(String gender, String imgName)
    {
        contentStoragePub.sendMsq(new File(getFullImgPath(imgName)), gender);
        deleteByName(imgName);
        return "Success";
    }

    public String deleteByName(String name)
    {
        String namePath = getFullImgPath(name);
        if(FileUtil.fileExits(namePath))
        {
            FileUtil.removeFile(namePath);
            log.info("Removed img {}", namePath);
            return "Success";
        }
        return "File not exists";
    }

    public Optional<File> getByName(String name)
    {
        return FileUtil.getFilesInDir(dir)
                .stream()
                .filter(file -> FileUtil.getName(file.getAbsolutePath()).equals(name))
                .findAny();
    }

    public List<String> getAllImgNames()
    {
        return FileUtil.getFilesInDir(dir)
                .stream()
                .map(file -> FileUtil.getName(file.getAbsolutePath()))
                .toList();
    }

    private File saveJpegImage(byte[] imgData)
    {
        File imgToSave  = null;
        String imgName = String.format("%stpdne.jpeg", StrUtil.genRandStr());
        try
        {
            imgToSave = FileUtil.saveFile(getFullImgPath(imgName), imgData);
            log.info("Save image {}", getFullImgPath(imgName));
        }
        catch (Exception ex)
        {
            log.error(ex.getMessage());
        }
        return imgToSave;
    }

    private String getFullImgPath(String imgName)
    {
        return String.format("%s/%s", dir, imgName);
    }
}
