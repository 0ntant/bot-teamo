package img.gen.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;

@Service("pixelbay")
public class PixabayServiceAdapter implements ImgProvideService
{
    @Autowired
    PixabayService pixabayService;

    @Getter
    private final String source = "pixabay.com";

    private Queue<byte[]> malePhotos = new LinkedList<>();
    private Queue<byte[]> femalePhotos = new LinkedList<>();

    @Override
    public byte[] getMaleImage() {
        if (malePhotos.isEmpty())
        {
            malePhotos.addAll(pixabayService.getMaleImages());
        }
        return malePhotos.remove();
    }

    @Override
    public byte[] getFemaleImage() {
        if (femalePhotos.isEmpty())
        {
            femalePhotos.addAll(pixabayService.getFemaleImages());
        }
        return femalePhotos.remove();
    }

    @Override
    public boolean isRateLimited() {
        return pixabayService.isRateLimited();
    }
}
