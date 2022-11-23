package net.backend.paylens.service;

import net.backend.paylens.model.dto.request.LoginDto;
import net.backend.paylens.model.dto.request.RegisterDto;
import net.backend.paylens.model.dto.response.ResponseData;

public interface UserService {
    ResponseData<Object> register(RegisterDto request) throws Exception;
    ResponseData<Object> login(LoginDto request) throws Exception;
    ResponseData<Object> updateDetailUser(RegisterDto request) throws Exception;
}
