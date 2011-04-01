package controllers;

import models.BigTable_R6;
import org.joda.time.DateMidnight;
import play.mvc.Controller;

import java.util.List;

/**
 * A long-polling controller to demonstrate Comet capabilities.
 *
 * @author Nicolas Martignole
 * @since 16/03/11 17:35
 */
public class AsyncBlotter extends Controller {
    public static void index(){
        List<BigTable_R6> listOfData = BigTable_R6.find("id < ?",15L).fetch(10);
         for(BigTable_R6 m:listOfData){
            m.part_var=Math.random()*10;
            m.pro_n1=Math.random()*10;
            m.save();
        }
        render(listOfData);
    }

    public static void getMessages(){
       List<BigTable_R6> messages = BigTable_R6.find("from BigTable_R6 r where r.id<15 and (r.pro_n1<5 or r.part_var > 5)").fetch();

        if (messages.isEmpty()) {
            suspend("5s");
        }
        for(BigTable_R6 m:messages){
            m.part_var=Math.random()*10;
            m.pro_n1=Math.random()*10;
            m.save();
        }
        renderJSON(messages);
    }

}
