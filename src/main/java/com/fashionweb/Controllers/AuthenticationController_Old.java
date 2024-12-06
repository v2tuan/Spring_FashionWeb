package com.fashionweb.Controllers;

import com.fashionweb.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController_Old {
    AuthenticationService authenticationService;

//    @PostMapping("/token")
//    ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequestDTO request){
//        AuthenticationResponse result = authenticationService.authenticate(request);
//        return new ResponseEntity<AuthenticationResponse>(result, HttpStatus.OK);
//    }
//
//    @PostMapping("/introspect")
//    ResponseEntity<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
//            throws ParseException, JOSEException {
//        IntrospectResponse result = authenticationService.introspect(request);
//
//        return new ResponseEntity<IntrospectResponse>(result, HttpStatus.OK);
//    }


}
