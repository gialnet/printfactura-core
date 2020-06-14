package com.printfactura.core.domain.appusers;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AppUser {

    private String IdUser; // email address
    private String UserUUID; // it is assigned when is create a new user
    private String Password;
    private Date SignDate;
    @Builder.Default
    private String Status = "active";
}
