import models.Employee;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

/**
 * Loads a sample into the database
 *
 * @author Nicolas Martignole
 * @since 06/03/11 14:55
 */
@OnApplicationStart
public class Bootstrap extends Job {

    public void doJob() {
        if (Play.mode == Play.Mode.DEV) {
            if (Employee.count() == 0) {
                Fixtures.load("test-datas.yml");
            }
        }
    }

}