package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * TODO  SELECT month,Part_N1,Part_N,Part_var,Pro_N1,Pro_N,Pro_var,Bdd_N1,Bdd_N,Bdd_var FROM agg_r6  WHERE  LOCALISATION = 'Ile De France' AND Year = '2009' AND PRODUCT = 13010001
 */
@Entity
@Table(name="agg_r6")
public class BigTable_R6 extends Model {
    public String localisation;
}
