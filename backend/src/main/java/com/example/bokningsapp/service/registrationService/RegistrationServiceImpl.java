package com.example.bokningsapp.service.registrationService;

import com.example.bokningsapp.dto.RegistrationRequest;
import com.example.bokningsapp.enums.ERole;
import com.example.bokningsapp.model.User;
import com.example.bokningsapp.repository.UserRepository;
import com.example.bokningsapp.security.BcryptPasswordConfig;
import com.example.bokningsapp.service.emailService.EmailSender;
import com.example.bokningsapp.service.emailService.EmailService;
import com.example.bokningsapp.service.userService.UserService;
import com.example.bokningsapp.token.VerificationToken;
import com.example.bokningsapp.token.VerificationTokenRepo;
import com.example.bokningsapp.token.VerificationTokenService;
import com.example.bokningsapp.validator.RegistrationRequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final UserRepository userRepository;
    private final BcryptPasswordConfig bcryptPasswordConfig;
    private final EmailService emailService;
    private final VerificationTokenRepo verificationTokenRepo;
    private final RegistrationRequestValidator registrationRequestValidator;
    private final VerificationTokenService verificationTokenService;
    private final UserService userService;
    private final EmailSender emailSender;


    @Autowired
    public RegistrationServiceImpl(UserRepository userRepository, BcryptPasswordConfig bcryptPasswordConfig, EmailService emailService, VerificationTokenRepo
            verificationTokenRepo, RegistrationRequestValidator registrationRequestValidator,VerificationTokenService verificationTokenService, UserService userService,
            EmailSender emailSender) {
        this.userRepository = userRepository;
        this.bcryptPasswordConfig = bcryptPasswordConfig;
        this.emailService = emailService;
        this.verificationTokenRepo = verificationTokenRepo;
        this.registrationRequestValidator = registrationRequestValidator;
        this.verificationTokenService = verificationTokenService;
        this.userService = userService;
        this.emailSender = emailSender;
    }


    @Override
    public VerificationToken registerUser(RegistrationRequest registrationRequest) {

     /*   boolean isFirstNameValid = registrationRequestValidator.validateFirstName(registrationRequest.getFirstName());
        boolean isLastNameValid = registrationRequestValidator.validateLastName(registrationRequest.getLastName());
        boolean isEmailValid = registrationRequestValidator.validateEmail(registrationRequest.getEmail());
        boolean isPasswordValid = registrationRequestValidator.validatePassword(registrationRequest.getPassword());
        boolean isAddressValid = registrationRequestValidator.validateAddress(registrationRequest.getAddress());
        boolean isPhoneNumberValid = registrationRequestValidator.validatePhoneNumber(registrationRequest.getPhoneNumber());

        if (!isFirstNameValid) {
            throw new IllegalStateException("First name is not valid");
        }
        if (!isLastNameValid) {
            throw new IllegalStateException("Last name is not valid");
        }
        if (!isPasswordValid) {
            throw new IllegalStateException("Password is not valid");
        }
        if (!isEmailValid) {
            throw new IllegalStateException("Email is not valid");
        }
        if (!isAddressValid) {
            throw new IllegalStateException("Adress is not valid");
        }
        if (!isPhoneNumberValid) {
            throw new IllegalStateException("Phone number is not valid");
        }
*/
        User user = new User();
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(encryptPassword(registrationRequest.getPassword()));
        user.setBirthDate(registrationRequest.getBirthDate());
        user.setAddress(registrationRequest.getAddress());
        user.setPhoneNumber(registrationRequest.getPhoneNumber());
        user.setRole(ERole.ROLE_USER);

        User registeredUser = userRepository.save(user);
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setUser(registeredUser);
        verificationToken.setToken(UUID.randomUUID().toString());
        verificationToken.setExpiryDate(calculateExpiryDate(60 * 24)); // expires in 24 hours
        verificationTokenRepo.save(verificationToken);

        String link = "http://localhost:8080/confirmAccount?token=" + registeredUser.getVerificationToken();
        emailSender.sendVerificationEmail(
                registeredUser.getEmail(),
                buildEmail(registeredUser.getFirstName(), link));
        return verificationToken;
    }

    @Override
    public String confirmToken(String token) {
        VerificationToken verificationToken = verificationTokenService.getToken(token).orElseThrow(() -> new IllegalStateException("token not found"));
        if (verificationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }
        if (token == null) {
            throw new IllegalStateException("Invalid token");
        }

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new IllegalStateException("Token has expired");
        }
        verificationTokenService.setConfirmedAt(token);
        userService.enableUser(
                verificationToken.getUser().getEmail());
        return "confirmed";

    }

    @Override
    public Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        long longValue = System.currentTimeMillis();
        Date dateValue = new Date(longValue);
        cal.setTimeInMillis(dateValue.getTime());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTimeInMillis());
    }

    @Override
    public String encryptPassword(String password) {
        return bcryptPasswordConfig.bCryptPasswordEncoder1().encode(password);   }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}

