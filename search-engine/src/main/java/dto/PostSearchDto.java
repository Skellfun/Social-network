package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Tag;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)

public class PostSearchDto implements Serializable {

    private long id;

    private boolean isDeleted;

    private SearchIds ids;

    private SearchBlockedByIds blockedByIds;

    private String author;

    private boolean withFriends;

    private List<String> tags;

    private LocalDateTime dateFrom;

    private LocalDateTime dateTo;


}