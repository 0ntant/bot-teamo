package img.gen.service;

import img.gen.dto.ImgDataDto;
import img.gen.model.ImageModel;
import img.gen.util.StrUtil;
import integration.dto.ImgAvaDto.ImgAvaDto;
import integration.dto.ImgAvaDto.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ImgMemService
{
    private final List<ImgProvideService> imgProvideServices;
    private final ImgProvideService defaultProvider;
    private final ContentStorageService contentStorageService;

    private LinkedList<ImageModel> images;

    @Autowired
    public ImgMemService(
            @Qualifier("Pexels") ImgProvideService pexProvide,
            @Qualifier("pixelbay") ImgProvideService pixProvide,
            @Qualifier("unsplash") ImgProvideService unsProvide,
            @Qualifier("PEN") ImgProvideService penService,
            ContentStorageService contentStorageService
    )
    {
        this.defaultProvider = penService;
        this.contentStorageService = contentStorageService;

        images = new LinkedList<>();
        imgProvideServices = new ArrayList<>();

        imgProvideServices.add(pexProvide);
        imgProvideServices.add(pixProvide);
        imgProvideServices.add(unsProvide);
    }

    public void setImages()
    {
        if (images.size() < 4) {
            addRandImg();
            setImages();
        }

    }

    @Async
    public void addRandImg()
    {
        ImgProvideService randomImgProvide
                = imgProvideServices.get(
                        new Random().nextInt(
                                imgProvideServices.size()
                        )
                );

        if (randomImgProvide.isRateLimited())
        {
            try
            {
                addImgs(randomImgProvide);
            }
            catch (Exception ex)
            {
                log.warn("Error during getting pictures {}", ex.getMessage());
                addImgs(defaultProvider);
            }
        }
        else
        {
            for (ImgProvideService imgProvideService : imgProvideServices)
            {
                if (imgProvideService.isRateLimited())
                {
                    addImgs(imgProvideService);
                    return;
                }
            }
            addImgs(defaultProvider);
        }
    }

    private void addImgs(ImgProvideService imgProcessingService)
    {
        addImg(imgProcessingService, "female");
        addImg(imgProcessingService, "male");
    }

    private void addImg(ImgProvideService imgProvideService, String gender )
    {
        ImageModel image = ImageModel.builder()
                .name(StrUtil.genRandImgJpegName())
                .gender(gender)
                .build();
        if (gender.equals("female"))
        {
            image.setData(imgProvideService.getFemaleImage());
        }
        else
        {
            image.setData(imgProvideService.getMaleImage());
        }

        image.setSource(imgProvideService.getSource());

        if (!contentStorageService.isReg(image.getData()))
        {
            images.add(image);
        }
        else
        {
            log.info("Img is REGISTERED");
        }
    }

    public ImgDataDto getRandImg()
    {
        if (images.isEmpty())
        {
            throw new NoSuchElementException("Images not found");
        }
        return ImgDataDto.map(
                images.remove(
                        new Random()
                                .nextInt(0,
                                        images.size()
                                )
                )
        );
    }

    public void saveImg(ImgDataDto imgDataDto)
    {
        ImageModel imageModel = ImgDataDto.map(imgDataDto);
        imageModel.setName(StrUtil.genRandImgJpegName());
        contentStorageService.sendImg(saveRequest(imageModel));
    }

    public void deleteImg(ImgDataDto imgDataDto)
    {
        ImageModel imageModel = ImgDataDto.map(imgDataDto);
        imageModel.setName(StrUtil.genRandImgJpegName());
        contentStorageService.sendImg(regRequest(imageModel));
    }

    private ImgAvaDto saveRequest(ImageModel imageToSend)
    {
        return map(
                imageToSend,
                Operation.SAVE
        );
    }

    private ImgAvaDto regRequest(ImageModel imageToSend)
    {
        return map(
                imageToSend,
                Operation.REG
        );
    }

    private ImgAvaDto map(ImageModel imageModel,
                          Operation operation
    )
    {
        return new ImgAvaDto(
                operation,
                imageModel.getName(),
                imageModel.getGender(),
                imageModel.getData(),
                imageModel.getSource()
        );
    }
}
