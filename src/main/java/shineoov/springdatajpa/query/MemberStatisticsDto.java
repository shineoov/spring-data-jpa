package shineoov.springdatajpa.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberStatisticsDto {

    private Long count;
    private Long sum;
    private Double avg;
    private Integer max;
    private Integer min;

    public MemberStatisticsDto(Long count, Long sum, Double avg, int max, int min) {
        this.count = count;
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
    }
}
