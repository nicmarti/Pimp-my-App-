package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="agg_r6")
public class BigTable_R6 extends Model {
    public String localisation;
    public Double part_var;
    public Double pro_n1;
    public String month;
    public Date lastUpdated;
    public Date date;
}
