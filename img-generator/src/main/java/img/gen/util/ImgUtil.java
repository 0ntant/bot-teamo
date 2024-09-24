package img.gen.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.File;

public class ImgUtil
{
    public static void cutImgBottom(String filePath, int size)
    {
        validateImgExt(filePath);
        try
        {
            File imgToCut = new File(filePath);
            BufferedImage bufferedImage = ImageIO.read(imgToCut);
            if (size >= bufferedImage.getHeight())
            {
                throw new RuntimeException("Invalid cut size");
            }
            BufferedImage subImage = bufferedImage.getSubimage(
                    0,
                    0,
                    bufferedImage.getWidth(),
                    bufferedImage.getHeight() - size
            );
            File outputfile = new File(filePath);
            String fileExt = FileUtil.getExt(filePath);
            ImageIO.write(subImage, fileExt, outputfile);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void cutImgUpper(String filePath, int size)
    {
        validateImgExt(filePath);
        try
        {
            File imgToCut = new File(filePath);
            BufferedImage bufferedImage = ImageIO.read(imgToCut);
            if (size >= bufferedImage.getHeight())
            {
                throw new RuntimeException("Invalid cut size");
            }
            BufferedImage subImage = bufferedImage.getSubimage(
                    0,
                    size,
                    bufferedImage.getWidth(),
                    bufferedImage.getHeight() - size
            );
            File outputfile = new File(filePath);
            String fileExt = FileUtil.getExt(filePath);
            ImageIO.write(subImage, fileExt, outputfile);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void cutImgRight(String filePath, int size)
    {
        validateImgExt(filePath);
        try
        {
            File imgToCut = new File(filePath);
            BufferedImage bufferedImage = ImageIO.read(imgToCut);
            if (size >= bufferedImage.getHeight())
            {
                throw new RuntimeException("Invalid cut size");
            }
            BufferedImage subImage = bufferedImage.getSubimage(
                    0,
                    0,
                    bufferedImage.getWidth() - size,
                    bufferedImage.getHeight()
            );
            File outputfile = new File(filePath);
            String fileExt = FileUtil.getExt(filePath);
            ImageIO.write(subImage, fileExt, outputfile);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void cutImgLeft(String filePath, int size)
    {
        validateImgExt(filePath);
        try
        {
            File imgToCut = new File(filePath);
            BufferedImage bufferedImage = ImageIO.read(imgToCut);
            if (size >= bufferedImage.getHeight())
            {
                throw new RuntimeException("Invalid cut size");
            }
            BufferedImage subImage = bufferedImage.getSubimage(
                    size,
                    0,
                    bufferedImage.getWidth() - size,
                    bufferedImage.getHeight()
            );
            File outputfile = new File(filePath);
            String fileExt = FileUtil.getExt(filePath);
            ImageIO.write(subImage, fileExt, outputfile);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void horizontalFlipImage(String filePath)
    {
        validateImgExt(filePath);
        try
        {
            File imageToFlip = new File(filePath);
            BufferedImage image = ImageIO.read(imageToFlip);

            BufferedImage flippedImage = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    image.getType()
            );

            AffineTransform transform = AffineTransform.getScaleInstance(-1, 1);
            transform.translate(-image.getWidth(), 0);

            Graphics2D g = flippedImage.createGraphics();
            g.drawImage(image, transform, null);
            g.dispose();

            File outputfile = new File(filePath);
            String fileExt = FileUtil.getExt(filePath);
            ImageIO.write(flippedImage, fileExt, outputfile);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private static void validateImgExt(String filePath)
    {
        String imgEx = FileUtil.getExt(filePath);
        switch (imgEx)
        {
            case "jpeg","png","jpg": break;
            default: throw new RuntimeException("Invalid image ext");
        }
    }
}
