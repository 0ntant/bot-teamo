package app.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleJokeRecognizer
{
    public boolean isJoke(String joke)
    {
        return isCharsEnough(joke)
                &&(
                        isSpacesEnough(joke)
                        && isEndWithSign(joke)
                        && isNotEndThreeDots(joke)
                        && isStartsWithUpperCase(joke)
                );
    }

    private boolean isSpacesEnough(String joke)
    {
        int gapsLimit = 5;
        return spacesCount(joke) >= gapsLimit;
    }

    private int spacesCount(String str)
    {
        int count = 0;
        for (char chr : str.toCharArray())
        {
            if (chr == ' ')
                count++;
        }
        return count;
    }

    private boolean isStartsWithUpperCase(String joke)
    {
        return  Character.isUpperCase(joke.codePointAt(0))
                ||  Character.isUpperCase(joke.codePointAt(1))
                ||  Character.isUpperCase(joke.codePointAt(2));
    }

    private boolean isEndWithSign(String joke)
    {
        Character lastChar = joke.charAt(joke.length() - 1);
        List<Character> validSign = List.of('.', '!', '?');

        return validSign.stream().anyMatch(character -> character == lastChar);
    }

    private boolean isNotEndThreeDots(String joke)
    {
        return !joke
                .substring(joke.length()- 4)
                .contains("...") && !joke.contains("…");
    }

    private boolean isCharsEnough(String srt)
    {
        return srt.length() > 30;
    }

    private   boolean isRussianText(String text) {
        return text.chars().allMatch(ch ->
                (ch >= '\u0400' && ch <= '\u04FF') || Character.isWhitespace(ch) || isPunctuation(ch)
        );
    }

    private  boolean isPunctuation(int ch)
    {
        return "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~".indexOf(ch) >= 0;
    }
}
