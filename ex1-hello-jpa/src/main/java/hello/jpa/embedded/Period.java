package hello.jpa.embedded;

import java.time.LocalDateTime;
import javax.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
public class Period {

    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
