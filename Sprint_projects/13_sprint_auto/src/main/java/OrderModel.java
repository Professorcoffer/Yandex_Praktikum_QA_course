import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OrderModel {
    @NonNull private String firstName;
    @NonNull private String lastName;
    @NonNull private String address;
    @NonNull private String metroStation;
    @NonNull private String phone;
    @NonNull private String rentTime;
    @NonNull private String deliveryDate;
    @NonNull private String comment;
    private String[] color;
    private String track;
    private String id;
}
