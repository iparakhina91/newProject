package models;

import lombok.Data;

@Data
public class RegisterUserResponse {
    String error;
    String token;
    Integer id;
}
