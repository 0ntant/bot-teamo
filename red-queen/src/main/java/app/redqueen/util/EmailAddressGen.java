package app.redqueen.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EmailAddressGen
{
    public static String mailRu()
    {
        return String.format("%s@%s", genRandStr(), "mail.ru");
    }

    public static String yandexRu()
    {
        return String.format("%s@%s", genRandStr(), "yandexmail.ru");
    }

    public static String gmailCom()
    {
        return String.format("%s@%s", genRandStr(), "gmail.com");
    }

    private static String genRandStr()
    {
        String pattern = "MMddyyyyHHmmss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        return df.format(today);
    }
}
