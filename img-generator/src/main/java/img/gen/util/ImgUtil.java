package img.gen.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImgUtil
{
    public static String cutImgBottom(String fileName, int size)
    {
        try
        {
            File imgToCut = new File(fileName);
            BufferedImage bufferedImage = ImageIO.read(imgToCut);
            BufferedImage subImage = bufferedImage.getSubimage(
                    0,
                    0,
                    bufferedImage.getWidth(),
                    bufferedImage.getHeight() - size
            );
            File outputfile = new File(fileName);
            String fileExt = FileUtil.getExt(fileName);
            ImageIO.write(subImage, fileExt, outputfile);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return fileName;
    }
}
