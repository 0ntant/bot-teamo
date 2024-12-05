package img.gen.service;

import img.gen.integration.mq.ContentStoragePub;
import img.gen.integration.rest.CSSClient;
import img.gen.integration.rest.PersonNotExistImgClient;
import img.gen.util.FileUtil;
import img.gen.util.StrUtil;
import integration.dto.ImgAvaDto.ImgAvaDto;
import integration.dto.ImgAvaDto.Operation;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
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

    @Autowired
    CSSClient cssClient;

    @Autowired
    ImgProcessingService imgProcessingServ;

    @Async
    public void createImg()
    {
        if(getImgCurCount() < getImageCount())
        {
            log.info("Create image");
            createPENCFile();
            createUnsplashPhotos();
            createPexelsPhotos();
            createPixabayPhotos();
        }
    }

    @Async
    public void createPexelsPhotos()
    {
        if (!pexelsService.isRateLimited())
        {
            log.warn("Pexels api request limit is over");
            return;
        }

        byte[] maleImgData = imgProcessingServ.horizontalFlip(
                pexelsService.getMaleImage());
        byte[] femaleImgData = imgProcessingServ.horizontalFlip(
                pexelsService.getFemaleImage()
        );

        if (isReg(maleImgData) || isReg(femaleImgData) )
        {
            log.warn("One of Pexels image already register");
            return;
        }

        saveJpegImage(femaleImgData);
        saveJpegImage(maleImgData);

//        ImgUtil.horizontalFlipImage(femaleImg.getAbsolutePath());
//        ImgUtil.horizontalFlipImage(maleImg.getAbsolutePath());
    }

    @Async
    public void createUnsplashPhotos()
    {
        if(!unsplashService.isRateLimited())
        {
            log.warn("Unsplash api request limit is over");
            return;
        }

        byte[] maleImgData = imgProcessingServ.horizontalFlip(
                unsplashService.getMaleImage());
        byte[] femaleImgData = imgProcessingServ.horizontalFlip(
                unsplashService.getFemaleImage()
        );

        if (isReg(maleImgData) || isReg(femaleImgData) )
        {
            log.warn("One of Unsplash image already register");
            return;
        }

        saveJpegImage(femaleImgData);
        saveJpegImage(maleImgData);
       // ImgUtil.horizontalFlipImage(femaleImg.getAbsolutePath());
       // ImgUtil.horizontalFlipImage(maleImg.getAbsolutePath());
    }

    @Async
    public void createPixabayPhotos()
    {
        if(!pixabayService.isRateLimited())
        {
            log.warn("Pixabay api request limit is over");
            return;
        }
        List<byte[]> photosData = Stream.concat(
                pixabayService.getFemaleImages()
                        .stream()
                        .map(img -> imgProcessingServ.horizontalFlip(img)),
                pixabayService.getMaleImages()
                        .stream()
                        .map(img -> imgProcessingServ.horizontalFlip(img))
        ).toList();

        for (byte[] photoData: photosData)
        {
            if (isReg(photoData))
            {
                log.warn("Image Pixabay already register");
                return;
            }
        }

        for (byte[] photoData : photosData)
        {
            File file = saveJpegImage(photoData);
//            ImgUtil.horizontalFlipImage(file.getAbsolutePath());
        }
    }

    @Async
    public void createPENCFile()
    {
        byte[] imgData = imgProcessingServ.cutBottom(
                imgPENClient.getImage(),
                25
        );

        if (isReg(imgData))
        {
            log.warn("Image PENCFile already register");
            return;
        }
        File imgToSave = saveJpegImage(imgData);
//        ImgUtil.cutImgBottom(imgToSave.getAbsolutePath(), 25);
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

    public String saveToCSS(String gender, String imgName)
    {
        contentStoragePub.sendMsq(createSaveRequest(gender, imgName));
        deleteByName(imgName);
        return "Success";
    }

    public String regToCSS(String imgName)
    {
        contentStoragePub.sendMsq(createRegRequest("male", imgName));
        deleteByName(imgName);
        return "Success";
    }

    public void regToCSS(byte[] imgData, String imgName)
    {
        contentStoragePub.sendMsq(new ImgAvaDto(
            Operation.REG,
            imgName,
            "male",
            imgData
        ));
    }

    private ImgAvaDto createRegRequest(String gender, String imgName)
    {
        ImgAvaDto regResponse = createGenRequest(gender, imgName);
        regResponse.setOperation(Operation.REG);
        return regResponse;
    }

    private ImgAvaDto createSaveRequest(String gender, String imgName)
    {
        ImgAvaDto regResponse = createGenRequest(gender, imgName);
        regResponse.setOperation(Operation.SAVE);
        return regResponse;
    }

    private ImgAvaDto createGenRequest(String gender, String imgName)
    {
        File imgFile = new File(getFullImgPath(imgName));
        ImgAvaDto genResponse = new ImgAvaDto();
        genResponse.setName(imgName);
//        genResponse.setImgData(
//                new String(
//                        Base64Util.encode(
//                                FileUtil.getFileBytes(
//                                        imgFile.getAbsolutePath()
//                                )
//                        )
//                )
//        );
        genResponse.setImgData(
                FileUtil.getFileBytes(
                        imgFile.getAbsolutePath()
                )
        );
        genResponse.setGender(gender);
        return genResponse;
    }

    public void deleteByName(String name)
    {
        String namePath = getFullImgPath(name);
        if(FileUtil.fileExits(namePath))
        {
            FileUtil.removeFile(namePath);
            log.info("Removed img {}", namePath);
        }
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

    public boolean isReg(byte[] data)
    {
        return cssClient.checkObjectExists(data);
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
