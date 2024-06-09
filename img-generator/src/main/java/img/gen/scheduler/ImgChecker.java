package img.gen.scheduler;

import img.gen.service.ImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImgChecker
{
    @Autowired
    ImgService imgService;

    @Scheduled(fixedDelay = 1000 * 5)
    public void checkImages()
    {
       if (imgService.getImgCurCount() <= imgService.getImageCount())
       {
           log.info("Create image");
           imgService.createImg();
       }
    }
}
