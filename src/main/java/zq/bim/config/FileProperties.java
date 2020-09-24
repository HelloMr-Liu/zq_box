package zq.bim.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileProperties {
	
	private String zyPath;
	
	private String graphicsPath;

	public String getZyPath() {
		return zyPath;
	}

	public void setZyPath(String zyPath) {
		this.zyPath = zyPath;
	}

	public String getGraphicsPath() {
		return graphicsPath;
	}

	public void setGraphicsPath(String graphicsPath) {
		this.graphicsPath = graphicsPath;
	}
}
