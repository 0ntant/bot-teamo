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
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

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
    PersonNotExistImgClient imgClient;

    public int getImgCurCount()
    {
        return FileUtil.countFile(dir);
    }

    public String getRandImgPath()
    {
        return getRandImg().getAbsolutePath();
    }

    public boolean imgExists(String name)
    {
        return FileUtil.fileExits(getFullImgPath(name));
    }

    public String sendToCSS(String gender, String imgName)
    {
        log.info("Send to content storage service gender {} {}", gender, getFullImgPath(imgName));

        contentStoragePub.sendMsq(new File(getFullImgPath(imgName)), gender);
        return "Success";
    }

    public File getRandImg()
    {
        return FileUtil.getFilesInDir(dir)
                .get(ThreadLocalRandom.current().nextInt(0, getImgCurCount()));
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


    public void createImg()
    {
        byte[] imgFromClient = imgClient.getImage();
        String imgName = String.format("%stpdne.jpeg" , StrUtil.genRandStr());
        File imgToSave = FileUtil.saveFile(
                getFullImgPath(imgName),
                imgFromClient
        );
        ImgUtil.cutImgBottom(imgToSave.getAbsolutePath(), 25);
        log.info("Save image {}", imgName);
    }

    private String getFullImgPath(String imgName)
    {
        return String.format("%s/%s", dir, imgName);
    }
}
