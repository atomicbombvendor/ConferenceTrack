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
            printTracks(sessions);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void printTracks(List<List<Conference>> sessions){

        int trackCount = 1;
        for (List<Conference> ss : sessions){
            System.out.println("Track " + trackCount + ":");
            for (Conference s : ss){
                System.out.println(s.printTrack());
            }
            System.out.println();
            trackCount++;
        }
    }

    public static void main(String[] args) {
        App app = new App();
        app.scheduleConference();
    }
}
