package dh.projetointegradorctd.backend.request;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignUpForm {
    private String name;
    private String lastname;
    private String email;
    private String password;
}
