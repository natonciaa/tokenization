package co.com.tokenization.tokenization_api.domain.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private Long id;
    @NotNull(message = "Name cant be null" )
    private String name;
    @Email(message = "Invalid email")
    private String email;
    @NotNull(message = "Phone cant be null" )
    private String phone;
    @NotNull(message = "Address cant be null" )
    private String address;

}
