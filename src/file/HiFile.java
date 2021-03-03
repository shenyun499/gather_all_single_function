package file;

public class HiFile {
	private String fileName;
	private StringBuffer fileBody;
	private String saveKey;
	private String saveFilePath;
	private String srcFilePath;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public StringBuffer getFileBody() {
		return fileBody;
	}
	public void setFileBody(StringBuffer fileBody) {
		this.fileBody = fileBody;
	}
	public String getSaveKey() {
		return saveKey;
	}
	public void setSaveKey(String saveKey) {
		this.saveKey = saveKey;
	}
	public String getSaveFilePath() {
		return saveFilePath;
	}
	public void setSaveFilePath(String saveFilePath) {
		this.saveFilePath = saveFilePath;
	}
	
	public String getSrcFilePath() {
		return srcFilePath;
	}
	
	public void setSrcFilePath(String srcFilePath) {
		this.srcFilePath = srcFilePath;
	}
	

}
