package abhi.color;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class UploadFile {

	public static void uploadFileUsingMultipart(String file_path) {
		HttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(
				"http://192.168.6.230/mytesting/sttl/fileupload.php");
		MultipartEntity entity = new MultipartEntity();
		try {
			entity.addPart("file", new StringBody("file"));
			entity.addPart("filename", new StringBody("test.txt"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		entity.addPart("file", new FileBody(new File(file_path)));
		httpost.setEntity(entity);

		HttpResponse response;

		try {
			response = client.execute(httpost);
			System.out.println("Response - "+response);
			System.out.println("Actual Response - "+EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
