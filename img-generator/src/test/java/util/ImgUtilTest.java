package util;

import img.gen.util.ImgUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class ImgUtilTest
{
    @Test
    void isFileHasImgExt_true()
    {
        //given
        File imgFile = new File("src/test/resources/imgInstance.jpg");
        //then

        //expected
        assertTrue(ImgUtil.isFileImgExt(imgFile.getName()));
    }
}
