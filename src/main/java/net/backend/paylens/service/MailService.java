package net.backend.paylens.service;

import net.backend.paylens.model.dto.request.MailDto;
import net.backend.paylens.model.dto.response.ResponseData;

public interface MailService {
    // void sendMail(MailDto request) throws Exception;
    ResponseData<Object> sendMail(MailDto data) throws Exception;
}
