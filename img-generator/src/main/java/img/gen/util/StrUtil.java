package img.gen.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StrUtil
{
    public static String genRandStr()
    {
        String pattern = "MMddyyyyHHmmss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }
}
