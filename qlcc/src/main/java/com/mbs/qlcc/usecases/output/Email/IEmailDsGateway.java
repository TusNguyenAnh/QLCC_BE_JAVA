package com.mbs.qlcc.usecases.output.Email;

import com.mbs.qlcc.entities.Complex.Complex;

public interface IEmailDsGateway {
    void sendMail(String to, String subject, String complexName, String name, String username, String password);
}
