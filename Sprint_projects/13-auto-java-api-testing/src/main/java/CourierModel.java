import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourierModel {
    private String login;
    private String password;
    private String firstName;
    private String id;
}
