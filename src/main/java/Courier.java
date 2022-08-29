import org.apache.commons.lang3.RandomStringUtils;

public class Courier {
    public String login;
    public String password;
    public String firstName;

    public Courier(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandom(){

        String login = RandomStringUtils.randomAlphabetic(5);
        String password = RandomStringUtils.randomNumeric(5);
        String firstName = RandomStringUtils.randomAlphabetic(5);

        return new Courier(login,password,firstName);
    }
    @Override
    public String toString() {
        return "Courier{" + "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName=" + firstName + '\'' + '}';
    }


}
