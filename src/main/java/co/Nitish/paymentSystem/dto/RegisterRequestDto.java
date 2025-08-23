package co.Nitish.paymentSystem.dto;


import lombok.*;

@Data@Getter@Setter@AllArgsConstructor@NoArgsConstructor
public class RegisterRequestDto {
    private String username;
    private String password;
    private String email;
}
