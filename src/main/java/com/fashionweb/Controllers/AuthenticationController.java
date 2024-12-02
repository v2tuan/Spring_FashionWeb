package com.fashionweb.Controllers;

import com.fashionweb.dto.request.AuthenticationRequestDTO;
import com.fashionweb.dto.request.IntrospectRequest;
import com.fashionweb.dto.response.AuthenticationResponse;
import com.fashionweb.dto.response.IntrospectResponse;
import com.fashionweb.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequestDTO request){
        AuthenticationResponse result = authenticationService.authenticate(request);
        return new ResponseEntity<AuthenticationResponse>(result, HttpStatus.OK);
    }

    @PostMapping("/introspect")
    ResponseEntity<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        IntrospectResponse result = authenticationService.introspect(request);

        return new ResponseEntity<IntrospectResponse>(result, HttpStatus.OK);
    }
}
