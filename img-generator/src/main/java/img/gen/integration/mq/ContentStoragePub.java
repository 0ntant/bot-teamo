package img.gen.integration.mq;

import img.gen.config.RabbitConfig;
import img.gen.util.Base64Util;
import img.gen.util.FileUtil;
import integration.dto.ImgAvaDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Slf4j
@Component
public class ContentStoragePub
{
    @Autowired
    RabbitTemplate rabbitTemplate;

    public void sendMsq(File imgFile, String gender)
    {
        try
        {
            rabbitTemplate.convertAndSend(
                    RabbitConfig.cssImgAvaExc,
                    RabbitConfig.cssImgAvaRoute,
                  new ImgAvaDto(
                          FileUtil.getName(imgFile.getAbsolutePath()),
                          gender,
                          new String(Base64Util.getBase64(FileUtil.getFileBytes(imgFile.getAbsolutePath()))))
            );
            log.info("[PRODUCER]Send to exchanger {}", RabbitConfig.cssImgAvaExc);
            FileUtil.removeFile(imgFile.getAbsolutePath());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
