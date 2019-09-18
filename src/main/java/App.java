import com.practice.service.ConferenceService;
import com.practice.service.impl.ConferenceServiceImpl;

public class App {

    private ConferenceService service;

    public App() {
        this.service = new ConferenceServiceImpl();
    }

    public static void main(String[] args) {
        App app = new App();
        app.scheduleConference();
    }

    private void scheduleConference() {
        try {
            String fileName = "Conferences.txt";
            service.TrackSession(fileName);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
