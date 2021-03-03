package file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class FileUtil {
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static void coypFile(String srcPathStr, String desPathStr,String fileName) throws Exception {

		srcPathStr=srcPathStr+File.separator + fileName;    //    源文件地址
		System.out.println("源文件:" + fileName);
		desPathStr = desPathStr + File.separator + fileName; //   目标文件地址
		System.out.println("目标文件地址:" + desPathStr);
		try {
			FileInputStream fis = new FileInputStream(srcPathStr);// 创建输入流对象
			FileOutputStream fos = new FileOutputStream(desPathStr); // 创建输出流对象
			byte datas[] = new byte[1024 * 8];// 创建搬运工具
			int len = 0;// 创建长度
			while ((len = fis.read(datas)) != -1)// 循环读取数据
			{
				fos.write(datas, 0, len);
			}
			fis.close();  // 释放资源
			fos.close();   // 释放资源
		} catch (Exception e) {
			logger.error( " 复制文件，发生了异常。", e);
			throw e;
		};

	}
	
	public  static HiFile readFile(String bakFilePath, String fileName) throws IOException {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader bReader = null;
		HiFile returnFile = null;
		String filePathName = bakFilePath + File.separatorChar + fileName;
		try {
			String line;
			StringBuffer stringBuffer = new StringBuffer();
			fis = new FileInputStream(filePathName);// 定义输入文件
			isr = new InputStreamReader(fis);// 读取输入文件
			bReader = new BufferedReader(isr);// 读取缓冲区
			while ((line = bReader.readLine()) != null) { // 按行读取数据
				stringBuffer.append(line);
			}
			returnFile = new HiFile();
			returnFile.setFileBody(stringBuffer);
			returnFile.setFileName(fileName);
		} catch (IOException e) {
			logger.error("读入文件异常");
			throw e;
		} finally {
			try {
				bReader.close();// 关闭读取缓冲区
				isr.close();// 关闭读取文件内容
				fis.close();// 关闭读取文件
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return returnFile;
	}
	
	public  static HiFile readBakFile(String bakFilePath, String fileName) throws IOException {
		HiFile returnFile = null;
		String filePathName = bakFilePath + File.separatorChar + fileName;
		try {
			StringBuffer stringBuffer = new StringBuffer();

			File f = new File(filePathName);
			String se = org.apache.commons.io.FileUtils.readFileToString(f);
			stringBuffer.append(se);

			returnFile = new HiFile();
			returnFile.setFileBody(stringBuffer);
			returnFile.setFileName(fileName);

		} catch (IOException e) {
			String strInfo=" 读入文件异常:"+"文件路径："+bakFilePath+";fileName"+fileName;
			logger.info(strInfo, e);	

			throw e;
		}
		return returnFile;
	}
}
