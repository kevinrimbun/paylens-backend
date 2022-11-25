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
    @Size(max = 6)
    private String pin;
}