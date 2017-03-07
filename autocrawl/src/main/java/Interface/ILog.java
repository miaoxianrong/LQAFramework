package Interface;

import java.util.ArrayList;

public interface ILog {
	void WriteToMainLog(ArrayList<String> LogContext);
	void WriteToElementsLog(ArrayList<String> LogContext);
	void WriteToTaglessLog(ArrayList<String> LogContext);
	void setLogFilePath(String path);
	void createLogFilePath();
	void WriteToLog();
}
