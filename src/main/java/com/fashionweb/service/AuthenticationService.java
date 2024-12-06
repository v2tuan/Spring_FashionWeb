package com.fashionweb.service;

import com.fashionweb.Entity.Account;
import com.fashionweb.Enum.Role;
import com.fashionweb.dto.request.AuthenticationRequestDTO;
import com.fashionweb.dto.request.IntrospectRequest;
import com.fashionweb.dto.request.VerifyAccountDTO;
import com.fashionweb.dto.request.accounts.RegisterAccountDTO;
import com.fashionweb.dto.response.AuthenticationResponse;
import com.fashionweb.dto.response.IntrospectResponse;
import com.fashionweb.mapper.IAccountMapper;
import com.fashionweb.repository.IAccountRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    IAccountRepository accountRepository;

    @Autowired
    private IAccountMapper accountMapper;

    @Autowired
    private final EmailService emailService;

//    @NonFinal
////    @Value("${jwt.signerKey}")
//    protected String SIGNER_KEY;
//
////     Dang nhap xac thuc va tao token
//    public AuthenticationResponse authenticate(AuthenticationRequestDTO request){
//        Account account = accountRepository.findAccountByEmail(request.getEmail())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
//        boolean authenticated = passwordEncoder.matches(request.getPassword(), account.getPassword());
//        if (!authenticated)
//            throw new RuntimeException("Tài khoan mat khau khong dung");
//        var token = generateToken(account);
//
//        return AuthenticationResponse.builder()
//                .token(token)
//                .authenticated(true)
//                .build();
//    }
//
//    private String generateToken(Account account) {
//        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
//
//        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .subject(account.getEmail())
//                .issuer("devteria.com")
//                .issueTime(new Date())
//                .expirationTime(new Date(
//                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
//                ))
//                .claim("accId", account.getAccId()) // Thêm account ID
//                .build();
//
//        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
//
//        JWSObject jwsObject = new JWSObject(header, payload);
//
//        try {
//            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
//            return jwsObject.serialize();
//        } catch (JOSEException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // xac thuc token
//    public IntrospectResponse introspect(IntrospectRequest request)
//            throws JOSEException, ParseException {
//        var token = request.getToken();
//
//        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
//
//        SignedJWT signedJWT = SignedJWT.parse(token);
//
//        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
//
//        var verified = signedJWT.verify(verifier);
//
//        return IntrospectResponse.builder()
//                .valid(verified && expiryTime.after(new Date()))
//                .build();
//    }


    public Account signup(RegisterAccountDTO input) {
        // Kiểm tra xem email của tài khoản đã tồn tại trong hệ thống chưa
        if(accountRepository.existsByEmail(input.getEmail())) {
            throw new RuntimeException("Email đã tồn tại trên một tài khoản khác.");
        }
        Account account = accountMapper.toAccount(input);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        account.setVerificationCode(generateVerificationCode());
        account.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
        account.setEnabled(false);
        account.setRole(Role.USER);
        account.setCreateDate(LocalDate.now());
        sendVerificationEmail(account);
        return accountRepository.save(account);
    }

    public Account sendcodeforgotpassword(String email) {
        Optional<Account> optionalAccount = accountRepository.findAccountByEmail(email);
        if (optionalAccount.isPresent()) {
            // Tài khoản tồn tại
            Account account = optionalAccount.get();
            account.setVerificationCode(generateVerificationCode());
            account.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(15));
            sendVerificationEmail(account);
            return accountRepository.save(account);
        } else {
            // Tài khoản không tồn tại
            throw new RuntimeException("Email không tồn tại.");
        }
    }

    public Account resetPassword(RegisterAccountDTO input) {
        Optional<Account> optionalAccount = accountRepository.findAccountByEmail(input.getEmail());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            // Tài khoản tồn tại
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            account.setPassword(passwordEncoder.encode(input.getPassword()));
            return accountRepository.save(account);
        } else {
            // Tài khoản không tồn tại
            throw new RuntimeException("Email không tồn tại.");
        }
    }

    public Account verifyUser(VerifyAccountDTO input) {
        Optional<Account> optionalAccount = accountRepository.findAccountByEmail(input.getEmail());
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                throw new RuntimeException("Verification code has expired");
            }
            if (account.getVerificationCode().equals(input.getVerificationCode())) {
                account.setEnabled(true);
                account.setVerificationCode(null);
                account.setVerificationCodeExpiresAt(null);
                return accountRepository.save(account);
            } else {
                throw new RuntimeException("Invalid verification code");
            }
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void resendVerificationCode(String email) {
        Optional<Account> optionalAccount = accountRepository.findAccountByEmail(email);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.isEnabled()) {
                throw new RuntimeException("Account is already verified");
            }
            account.setVerificationCode(generateVerificationCode());
            account.setVerificationCodeExpiresAt(LocalDateTime.now().plusHours(1));
            sendVerificationEmail(account);
            accountRepository.save(account);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    private void sendVerificationEmail(Account account) { //TODO: Update with company logo
        String subject = "Account Verification";
        String verificationCode = "VERIFICATION CODE " + account.getVerificationCode();
        String htmlMessage = "<html>"
                + "<body style=\"font-family: Arial, sans-serif;\">"
                + "<div style=\"background-color: #f5f5f5; padding: 20px;\">"
                + "<h2 style=\"color: #333;\">Welcome to our app!</h2>"
                + "<p style=\"font-size: 16px;\">Please enter the verification code below to continue:</p>"
                + "<div style=\"background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0,0,0,0.1);\">"
                + "<h3 style=\"color: #333;\">Verification Code:</h3>"
                + "<p style=\"font-size: 18px; font-weight: bold; color: #007bff;\">" + verificationCode + "</p>"
                + "</div>"
                + "</div>"
                + "</body>"
                + "</html>";

        try {
            emailService.sendVerificationEmail(account.getEmail(), subject, htmlMessage);
        } catch (MessagingException e) {
            // Handle email sending exception
            e.printStackTrace();
        }
    }
    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }
}
