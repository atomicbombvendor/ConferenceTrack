import com.practice.model.Conference;
import com.practice.service.ConferenceTackService;
import com.practice.service.impl.ConferenceTaskServiceImpl;
import com.practice.util.FileUtil;

import java.util.List;

public class App {

    private ConferenceTackService service;

    public App() {
        this.service = new ConferenceTaskServiceImpl();
    }

    private void scheduleConference() {
        try {
            String inputFile = App.class.getResource("/") + "Conferences.txt";
            inputFile = inputFile.replace("file:/", "");
            List<String> inputLines = FileUtil.readInputDataFile(inputFile);
            List<Conference> sequentialConferences = service.getSequentialConferences(inputLines);
            List<List<Conference>> sessions = service.getSessionDays(sequentialConferences);
            service.printSessionDay(sessions);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.scheduleConference();
    }
}
