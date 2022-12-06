package net.backend.paylens.service;

import net.backend.paylens.model.dto.request.ChangePasswordDto;
import net.backend.paylens.model.dto.request.LoginDto;
import net.backend.paylens.model.dto.request.MailDto;
import net.backend.paylens.model.dto.request.PhoneNumberDto;
import net.backend.paylens.model.dto.request.PinDto;
import net.backend.paylens.model.dto.request.RegisterDto;
import net.backend.paylens.model.dto.response.ResponseData;

public interface UserService {
    ResponseData<Object> register(RegisterDto request) throws Exception;

    // Update user method
    ResponseData<Object> updateDetailUser(RegisterDto request) throws Exception;

    ResponseData<Object> createPin(long id, PinDto request) throws Exception;
    ResponseData<Object> login(LoginDto request) throws Exception;
    ResponseData<Object> changePassword(long id, ChangePasswordDto request) throws Exception;
    ResponseData<Object> phoneNumber(long id, PhoneNumberDto request) throws Exception;
    ResponseData<Object> deletePhoneNumber(long id) throws Exception;
ResponseData<Object> getById(long id);
}