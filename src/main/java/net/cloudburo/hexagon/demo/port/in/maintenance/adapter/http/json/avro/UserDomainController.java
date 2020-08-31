package net.cloudburo.hexagon.demo.port.in.maintenance.adapter.http.json.avro;

import net.cloudburo.hexagon.demo.domain.User;
import net.cloudburo.hexagon.demo.kernel.usecase.MaintenanceUseCaseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserDomainController {

    final MaintenanceUseCaseRepository maintenanceUseCaseRepository;

    public UserDomainController(final MaintenanceUseCaseRepository repository) {
        maintenanceUseCaseRepository = repository;
    }

    @PostMapping(path = "/domain/maintain/user", consumes = "application/avro-json", produces = "application/avro-json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // We got our domain model request
        try {
            user = maintenanceUseCaseRepository.createUser(user);
            // This below will not work, because our RequestBody model class (i.e. the avro client bindings)
            // do not extend from org.springframework.hateoas.RepresentationModel
            //user.add(linkTo(methodOn(UserDomainController.class).createUser(user)).withSelfRel());
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>( HttpStatus.EXPECTATION_FAILED);
        }
    }
}
