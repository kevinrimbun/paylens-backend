package net.backend.paylens.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import net.backend.paylens.model.dto.request.MailDto;
import net.backend.paylens.model.dto.response.ResponseData;
import net.backend.paylens.model.entity.User;
import net.backend.paylens.repository.UserRepository;
import net.backend.paylens.validator.UserValidator;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserValidator userValidator;
    private User user;
    private ResponseData<Object> responseData;

    // @Override
    // public void sendMail(MailDto request) throws Exception {
    //     // TODO Auto-generated method stub
        
    //     Optional<User> userOpt = userRepository.findByEmail(request.getRecipient());
    //     userValidator.validateUserNotFound(userOpt);
    //     // if (userOpt.isPresent()) {
    //         user = userOpt.get();
    //     SimpleMailMessage message = new SimpleMailMessage();

    //     message.setTo(request.getRecipient());
    //     message.setSubject("forgot");
    //     message.setText("link yang mengarah ke halaman forgot password");

    //     javaMailSender.send(message);
    //     // }else{
            
    //     // }
    // }

    @Override
    public ResponseData<Object> sendMail(MailDto data) throws Exception {
        // TODO Auto-generated method stub
        Optional<User> userOpt = userRepository.findByEmail(data.getRecipient());
        userValidator.validateUserNotFound(userOpt);
        // if (userOpt.isPresent()) {
            user = userOpt.get();
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(user.getEmail());
        message.setSubject("forgot");
        String pesan = "https://paylens.vercel.app/new-password/" + user.getId();
        message.setText(pesan);
        javaMailSender.send(message);
        responseData = new ResponseData<Object>(HttpStatus.OK.value(), "Terkirim", user.getPassword());
        return responseData;
    }
}