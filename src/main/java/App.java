import com.practice.model.Conference;
import com.practice.service.InputService;
import com.practice.service.PrintService;
import com.practice.service.TackService;
import com.practice.service.impl.InputServiceImpl;
import com.practice.service.impl.PrintServiceImpl;
import com.practice.service.impl.TrackServiceImpl;
import com.practice.util.FileUtil;

import java.util.List;

public class App {

    private TackService trackService;

    private InputService inputService;

    private PrintService printService;

    public App() {
        this.trackService = new TrackServiceImpl();
        this.inputService = new InputServiceImpl();
        this.printService = new PrintServiceImpl();
    }


    public static void main(String[] args) {
        App app = new App();
        app.scheduleConference();
    }

    private void scheduleConference() {
        try {
            String inputFile = App.class.getResource("/") + "Conferences.txt";
            inputFile = inputFile.replace("file:/", "");
            List<String> inputLines = FileUtil.readInputDataFile(inputFile);

            List<Conference> sequentialConferences = inputService.getSequentialConferences(inputLines);
            List<List<Conference>> sessionDays = trackService.getSessionDays(sequentialConferences);
            printService.printSessionDay(sessionDays);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
