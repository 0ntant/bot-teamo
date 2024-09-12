package img.gen.scheduler;

import img.gen.service.ImgService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ImgScheduler
{
    @Autowired
    ImgService imgService;

    @Async
    @Scheduled(initialDelay = 1000, fixedDelay = 1000 * 60 * 60)
    public void checkImages()
    {
        imgService.createImg();
    }
}
