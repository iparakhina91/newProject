package models;

import lombok.Data;

@Data
public class RegisterUserBody {
    String email, password;
}
