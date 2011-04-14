package models;

import play.db.jpa.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import java.util.Date;

@Entity
@Table(name="agg_r6")
public class BigTable extends Model {
    public String localisation;

    @Column(name = "part_var")
    public Double variable01;

    @Column(name = "pro_n1")
    public Double variable02;

    @Temporal(TemporalType.TIMESTAMP)
    public Date date;
}
