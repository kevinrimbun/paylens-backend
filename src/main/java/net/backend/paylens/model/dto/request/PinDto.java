package net.backend.paylens.model.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PinDto {
    @NotBlank(message = "PIN is required!")
    @Size(min = 6, max = 6, message = "PIN must be 6 number")
    private String pin;
}