package com.dollar.all.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User {

    private Long id;
    private String phoneNumber;
    private String name;
    private String firstName;
    private String email;
    private String password;
    private String profile;
}
