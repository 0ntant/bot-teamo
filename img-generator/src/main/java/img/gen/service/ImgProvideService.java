package img.gen.service;

public interface ImgProvideService
{
    String getSource();
    byte[] getMaleImage();
    byte[] getFemaleImage();
    boolean isRateLimited();
}
