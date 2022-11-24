package net.backend.paylens.model.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {

    @NotBlank(message = "Username is required!")
    private String username;

    @Email(message = "Must be email format!")
    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Size(min = 8, max = 12, message = "Password must between 8 - 12 characters!")
    private String password;

    // @NotBlank(message = "FirstName is required!")
    // private String firstName;
    // private String lastName;

    // @Size(min = 10, max = 15, message = "Phone number must between 10 - 15 characters!")
    // private String phoneNumber;
}