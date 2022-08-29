
public class CourierCredentials {
    public String login;
    public String password;

    public CourierCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials from(Courier courier) {
        return new CourierCredentials(courier.login, courier.password);
    }

    public CourierCredentials() {

    }

   public CourierCredentials setLogin(String login) {
       this.login = login;
       return this;
   }

   public static CourierCredentials getCourierNotfield(Courier courier) {
       return new CourierCredentials().setLogin(courier.login);
   }

    @Override
    public String toString() {
        return "CourierCredentials{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }


}
