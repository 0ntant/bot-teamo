package integration.dto.reg;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BirthDto
{
    Integer day;
    Integer monthNumber;
    Integer year;
}
