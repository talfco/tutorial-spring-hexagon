package net.cloudburo.hexagon.demo.port.in.maintenance.adapter.http.json.presentation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cloudburo.hexagon.demo.domain.EmailData;
import net.cloudburo.hexagon.demo.kernel.usecase.MaintenanceUseCaseRepository;

@RestController
public class UserPresentationController {

    final MaintenanceUseCaseRepository maintenanceUseCaseRepository;

    public UserPresentationController(final MaintenanceUseCaseRepository repository) {
        maintenanceUseCaseRepository = repository;
    }

    @GetMapping("/presentation/maintain/user/{userId}")
    public HttpEntity<UserPresentationModel> readUser(@PathVariable String userId) {
        // The Avro Client Object which is our Domain Model
        try {
            EmailData data = maintenanceUseCaseRepository.readUser(userId);
            String country = "Switzerland";
            UserPresentationModel user = new UserPresentationModel(
                    data.getUsername(),
                    data.getEmail(),
                    data.getSubscribed(),
                    country);
            user.id = userId;
            user.add(linkTo(methodOn(UserPresentationController.class).readUser(userId)).withSelfRel());
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(path = "/presentation/maintain/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserPresentationModel> createUser(@RequestBody UserPresentationModel user) {
        // We transform the request to our domain model
        String country = "CH";
        EmailData emailData = EmailData.newBuilder()
                                .setEmail(user.email)
                                .setUsername(user.userName)
                                .setSubscribed(user.subscribed)
                                .setCountry(country)
                                .build();
        try {
            emailData = maintenanceUseCaseRepository.createUser(emailData);
            user.id = emailData.getUid();
            user.add(linkTo(methodOn(UserPresentationController.class).createUser(user)).withSelfRel());
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping("/presentation/usermaintain")
    public HttpEntity<UserPresentationModel> user(
            @RequestParam(value = "user", defaultValue = "peter") String user,
            @RequestParam(value = "email", defaultValue = "peter@gmail.com") String email,
            @RequestParam(value = "subscribed", defaultValue = "true" ) boolean subscribed,
            @RequestParam(value = "country", defaultValue = "true" ) String country)
    {
        UserPresentationModel account = new UserPresentationModel(user,email,subscribed,country);
        account.add(linkTo(methodOn(UserPresentationController.class).user(user, email, subscribed, country)).withSelfRel());
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
