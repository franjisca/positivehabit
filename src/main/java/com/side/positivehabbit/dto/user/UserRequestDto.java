package com.side.positivehabbit.dto.user;

import com.side.positivehabbit.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {

    public String email;

    public String password;

    public String nickname;



}
