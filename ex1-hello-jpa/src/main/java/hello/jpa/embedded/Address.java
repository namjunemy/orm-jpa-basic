package hello.jpa.embedded;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
