package com.printfactura.core.domain.appusers;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AppUser {

    private String IdUser;
    private String Password;
}
