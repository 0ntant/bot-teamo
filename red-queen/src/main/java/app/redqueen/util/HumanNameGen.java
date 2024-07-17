package app.redqueen.util;

import java.util.Random;

public class HumanNameGen
{
    static String[] maleNames = {"Ваня", "Виталя", "Рома", "Гена", "Игорь", "Семен"};
    static String[] femaleNames = {"Оля", "Юля", "Вика", "Эля", "Диана"};

    static public String getNameByGender(String gender)
    {
        Random random = new Random();
        if(gender.equals("male"))
        {
            return maleNames[random.nextInt(0,maleNames.length - 1)];
        }
        return femaleNames[random.nextInt(0,femaleNames.length - 1)];
    }
}
