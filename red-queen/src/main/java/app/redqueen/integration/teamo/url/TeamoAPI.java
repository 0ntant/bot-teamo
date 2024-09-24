package app.redqueen.integration.teamo.url;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeamoAPI
{
    private String specialUsers;
    private String searchPersons;
    private String income;
    private String mutual;
    private String statistic;
    private String searchPersonsOthers;
    private String guests;
    private String signup;
    private String signin;

    @Autowired
    public TeamoAPI()
    {
        this.specialUsers = "https://api.teamo.ru/teamo.specialUsers";
        this.searchPersons = "https://api.teamo.ru/teamo.searchPersons";
        this.income = "https://api.teamo.ru/teamo.getIncome";
        this.mutual = "https://api.teamo.ru/teamo.getMutual";
        this.statistic = "https://api.teamo.ru/teamo.getStatistic";
        this.searchPersonsOthers = "https://api.teamo.ru/teamo.searchPersonsOthers";
        this.guests = "https://api.teamo.ru/teamo.getGuests";
        this.signup = "https://teamo.ru/signup";
        this.signin = "https://teamo.ru/signin";
    }

    public String getSpecialUsers() {
        return specialUsers;
    }

    public String getSearchPersons() {
        return searchPersons;
    }

    public String getIncome() {
        return income;
    }

    public String getMutual() {
        return mutual;
    }

    public String getStatistic() {
        return statistic;
    }

    public String getSearchPersonsOthers() {
        return searchPersonsOthers;
    }

    public String getGuests() {
        return guests;
    }

    public String getSignup() {
        return signup;
    }

    public String getSignin() {
        return signin;
    }
}
