package models;

import play.data.validation.MaxSize;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="agg_r3")
public class BigTableR extends Model {
    public Long product;
    @MaxSize(100)
    public String localisation;
    public String segment;
    @MaxSize(10)
    public String period;
    @MaxSize(20)
    public String indicator;

    public Double evolution;
    public Double monthly_N;
    public Double difference;

}
