package jpql.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {

    private String name;
    private int age;

    @Builder
    public MemberDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
