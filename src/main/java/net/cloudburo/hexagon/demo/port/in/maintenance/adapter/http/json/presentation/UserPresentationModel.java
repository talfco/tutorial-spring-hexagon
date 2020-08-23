package net.cloudburo.hexagon.demo.port.in.maintenance.adapter.http.json.presentation;

import org.springframework.hateoas.RepresentationModel;

/**
 * The UserPresentationModel resource extends from the RepresentationModel class (HAEOTAS) to inherit the add() method.
 * So once we create a link, we can easily set that value to the resource representation without adding any new
 * fields to it.
 */
public class UserPresentationModel extends RepresentationModel<UserPresentationModel> {

    public String id;
    public String userName;
    public String email;
    public boolean subscribed;
    public String country;  // Localized Value

    public UserPresentationModel(String userName, String email, boolean subscribed, String country) {
       this.userName = userName;
       this.email = email;
       this.subscribed = subscribed;
       this.country = country;
    }

}

