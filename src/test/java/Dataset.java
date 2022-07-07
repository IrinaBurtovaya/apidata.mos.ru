import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

//создаем модель датасета
@Data //подключаем lombok с помощью аннотации @Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dataset {

    @JsonProperty("Id")
    Long id;

    @JsonProperty("Caption")
    String caption;

    @JsonProperty("FullDescription")
    String fullDescription;
}
