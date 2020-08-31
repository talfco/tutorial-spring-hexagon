package net.cloudburo.hexagon.demo.port.in.maintenance.adapter.http.json.presentation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import net.cloudburo.hexagon.demo.domain.Header;
import net.cloudburo.hexagon.demo.port.out.persistence.adapter.sandbox.SandboxPersistency;
import org.apache.avro.SchemaNormalization;
import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import net.cloudburo.hexagon.demo.domain.User;
import net.cloudburo.hexagon.demo.domain.Basic;
import net.cloudburo.hexagon.demo.kernel.usecase.MaintenanceUseCaseRepository;


@RestController
public class UserPresentationController {

    private static Logger logger = Logger.getLogger(UserPresentationController.class);
    final MaintenanceUseCaseRepository maintenanceUseCaseRepository;

    public UserPresentationController(final MaintenanceUseCaseRepository repository) {
        maintenanceUseCaseRepository = repository;
    }

    @GetMapping("/presentation/maintain/user/{userId}")
    public HttpEntity<UserPresentationModel> readUser(@PathVariable String userId) {
        // The Avro Client Object which is our Domain Model
        try {
            User data = maintenanceUseCaseRepository.readUser(userId);
            String country = "Switzerland";
            UserPresentationModel user = new UserPresentationModel(
                    data.getBasic().getUsername(),
                    data.getBasic().getEmail(),
                    data.getBasic().getSubscribed(),
                    country);
            user.id = userId;
            user.add(linkTo(methodOn(UserPresentationController.class).readUser(userId)).withSelfRel());
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Get failed", ex);
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PostMapping(path = "/presentation/maintain/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserPresentationModel> createUser(@RequestBody UserPresentationModel user) {

        // We transform the request to our domain model
        String country = "CH";

        long fingerprint = SchemaNormalization.parsingFingerprint64(User.getClassSchema());

        // Creating the Header Record
        Header headerRecord = Header.newBuilder()
                                            .setAvroFingerprint(fingerprint)
                                            .setLastUpdateLoginId("FKU")
                                            .build();

        // Creating the Basic Record
        Basic basicRecord = Basic.newBuilder()
                                        .setUsername(user.userName)
                                        .setCountry(country)
                                        .setEmail(user.email)
                                        .setSubscribed(user.subscribed)
                                        .build();
        // Creating the User Record
        User userRecord = User.newBuilder()
                                .setBasic(basicRecord)
                                .setHeader(headerRecord)
                                .build();
        try {
            userRecord = maintenanceUseCaseRepository.createUser(userRecord);
            user.id = userRecord.getIds().getUid();
            user.add(linkTo(methodOn(UserPresentationController.class).createUser(user)).withSelfRel());
            return new ResponseEntity<>(user,HttpStatus.OK);
        } catch (Exception ex) {
            logger.error("Put failed", ex);
            return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
        }
    }

    @RequestMapping("/presentation/usermaintain")
    public HttpEntity<UserPresentationModel> user(
            @RequestParam(value = "user", defaultValue = "peter") String user,
            @RequestParam(value = "email", defaultValue = "peter@gmail.com") String email,
            @RequestParam(value = "subscribed", defaultValue = "true" ) boolean subscribed,
            @RequestParam(value = "country", defaultValue = "CH" ) String country)
    {
        UserPresentationModel account = new UserPresentationModel(user,email,subscribed,country);
        account.add(linkTo(methodOn(UserPresentationController.class).user(user, email, subscribed, country)).withSelfRel());
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
