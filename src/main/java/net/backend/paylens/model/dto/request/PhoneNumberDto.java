package net.backend.paylens.model.dto.request;

import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumberDto {
    @NotBlank(message = "Phone Number is Required")
    // @Pattern(regexp = "/^+?[1-9][0-9]{7,14}$")
    @Size(min = 10, max = 15)
    private String phoneNumber;
    
}
