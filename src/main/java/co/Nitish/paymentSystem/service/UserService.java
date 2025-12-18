package co.Nitish.paymentSystem.service;

import co.Nitish.paymentSystem.dto.LoginRequestDto;
import co.Nitish.paymentSystem.dto.RegisterRequestDto;
import co.Nitish.paymentSystem.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    public Map<String,String> login(LoginRequestDto loginRequestDto);
    public User register(RegisterRequestDto registerRequestDto);
}
