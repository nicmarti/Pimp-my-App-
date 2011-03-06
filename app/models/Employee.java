package models;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Employee extends Model {
    public String firstName;
    public String lastName;

    @Required
    @Email
    public String email;

    @Temporal(TemporalType.DATE)
    public Date hiredAt;

    public String location;
    public String techno;
}
