package net.backend.paylens.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TransactionDto {
  private Long amount;
  private String notes;
  private Long userId;
}
