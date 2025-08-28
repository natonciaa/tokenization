package co.com.tokenization.tokenization_api.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private Long id;
    @NotBlank
    private String name;
    @Email(message = "Invalid email")
    private String email;
    @NotBlank
    private String phone;
    @NotBlank
    private String address;

}
