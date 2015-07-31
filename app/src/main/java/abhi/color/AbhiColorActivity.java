package abhi.color;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Scanner;

import org.json.JSONArray;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class AbhiColorActivity extends Activity implements OnClickListener{

	ListView listView;
	MyCustomSpannable customSpannable, customSpannable1;
	ImageView imageView;
	TextView textView;
	AdView adView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);   

        new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				for (int i = 0; i < 20; i++) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Settings.System.putString(getContentResolver(), Settings.System.NEXT_ALARM_FORMATTED, "Hello "+i);	
				}
			}
		}).start();
        
        EnableWifiManually();
        getDisplayHeightWidth();
        useScannerMultipleDelimiter();
        usingScanner();
        //storingArrayInSharedPreferences();
        //InstallAPKFromSDCard();
        //CallApplicationByIntent();
        
        imageView = (ImageView) findViewById(R.id.check);
        textView = (TextView) findViewById(R.id.myTextview);
        //adView = new AdView(this, AdSize.BANNER, "a9876sf98dfg");
        adView = (AdView) findViewById(R.id.myAds);
        //AddingadMob();
        
        QuickContactBadge badgeSmall = (QuickContactBadge) findViewById(R.id.badge_small);
        badgeSmall.assignContactFromPhone("9725600451", true);
        badgeSmall.setMode(ContactsContract.QuickContact.MODE_LARGE);

        setImageUsingURI(imageView);
        imageView.setOnClickListener(this);
        
        String text = "Start Activity and Start WebView";
        settingSpannable(textView, text);
        enumUsage();
        
        for (int i = 1; i <= 2; i++) {
        	int id = getResources().getIdentifier("first_"+i, "raw", getPackageName());
        	imageView.setImageResource(id);
		}
    }
	
	private void EnableWifiManually() {
		WifiManager wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE); 
		wifiManager.setWifiEnabled(true);
	}
	
	/*@Override
	protected void onUserLeaveHint() {
		super.onUserLeaveHint();
		Log.e("Home Pressed", "Yes");
		ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
	    List<RunningTaskInfo> recentTasks = activityManager.getRunningTasks(Integer.MAX_VALUE);
	    for (int i=0; i<recentTasks.size(); i++) {
	        if (i == 1 && recentTasks.get(i).baseActivity.toShortString().indexOf(getPackageName()) > -1) {
	        	Log.e("Home Pressed", "inside loop "+recentTasks.get(i).baseActivity.toShortString());
	        	runningProcess();
		    // home button pressed
	            break;
	        }
	    }
	}

	*//**
	 * 
	 * Detect running applications.
	 * 
	 **//*
	private void runningProcess() {
		ActivityManager actvityManager = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> procInfos = actvityManager.getRunningAppProcesses();
		
		boolean appFound = false;
		for (int i = 0; i < procInfos.size(); i++) {
			System.out.println(procInfos.get(i).processName);
			if (procInfos.get(i).processName.equalsIgnoreCase("abhi.color")){
				appFound = true;
				
				actvityManager.killBackgroundProcesses(procInfos.get(i).processName);
				Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.app.contentcardapp.de");
				startActivity(LaunchIntent);
				
			}
		}

		if (appFound)
			Toast.makeText(getApplicationContext(),
					"Camera App is running!!!!", Toast.LENGTH_LONG).show();
		else
			Toast.makeText(getApplicationContext(),
					"Camera App is not running!!!!", Toast.LENGTH_LONG).show();
	}
	
	@Override
	public void onUserInteraction() {
		super.onUserInteraction();
		Log.e("user touched or pressed key or trackball event handled", "Yes");
	}*/

	private void enumUsage() {
		Log.d("value of FIRST ENUM", MyEnum.Numbers.valueOf("FIRST") + "");
		Log.d("value of FIRST ENUM", MyEnum.Numbers.FIRST + "");
		for (MyEnum.Numbers numbers : MyEnum.Numbers.values()) {
			Log.d("numbers", numbers + " " + numbers.ordinal());
		}

		MyEnum.Numbers numbers = MyEnum.Numbers.FIRST;
		switch (numbers) {
		case FIRST:
			Log.d("switch FIRST", numbers + "");
			break;
		case SECOND:
			Log.d("switch SECOND", numbers + "");
			break;
		case THIRD:
			Log.d("switch THIRD", numbers + "");
			break;
		}
	}
	
	private void getDisplayHeightWidth() {
		System.out.println(getResources().getResourceEntryName(R.drawable.ic_launcher));
        Display display = getWindowManager().getDefaultDisplay();
        Log.e("PixelFormat", display.getHeight()+" "+display.getWidth()+" "+display.getPixelFormat());
	}
	
	private void useScannerMultipleDelimiter() {
		HashMap<String, String> map = new HashMap<String, String>();
		String str = "Item1:100 Item2:200";
		Scanner scanner = new Scanner(str);
		
		scanner.useDelimiter("\\s*:\\s*|\\s* \\s*");  // ":" and " " are delimiters here
		while (scanner.hasNext()) {
			map.put(scanner.next(), scanner.next());
		}
		System.out.println(map);
	}
	
	private void usingScanner() {
		Scanner scanner;
		try {
			scanner = new Scanner(new File(Environment.getExternalStorageDirectory() + File.separator+ "myfile.txt"));
			scanner.useDelimiter("\n");
			while (scanner.hasNext()) {
				System.out.println(scanner.next());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void AddingadMob() {
	    AdRequest request = new AdRequest();
	    request.addTestDevice("87201F78C034507D78A2488BC7FACE07");  // for testing purpose.
	    adView.loadAd(request);	    
	}
	
	private void ApplyLinkToTextView(TextView textView) {
		String text = "<a href=\"http://www.google.co.in/\">Google</a>";
        textView.setText(Html.fromHtml(text));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	// Works 
	private void InstallAPKFromSDCard() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File("/mnt/sdcard/testproject.apk")),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}
	
	// Works
	private void CallApplicationByIntent() {
		
		// If you know the Main Activity
		Intent intent = new Intent(Intent.ACTION_MAIN);
		// package_name, package_name.Activity
		intent.setComponent(new ComponentName("com.example.parcelabledemo","com.example.parcelabledemo.MainActivity"));
		startActivity(intent);
		
		// If you don't know the Main Activity
		Intent LaunchIntent = getPackageManager().getLaunchIntentForPackage("com.example.parcelabledemo");
		startActivity(LaunchIntent);
	}
	
	// Works
	private void setImageUsingURI(ImageView imageView) {
		
		try {
			InputStreamToFile(getAssets().open("desert.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + File.separator+ "desert.jpg"));
		imageView.setImageURI(uri);
	}
	
	// Works
	private void InputStreamToFile(InputStream inputStream) {
		// write the InputStream to a FileOutputStream
		OutputStream out;
		try {
			out = new FileOutputStream(new File(Environment.getExternalStorageDirectory()+File.separator+"desert.jpg"));
			int read = 0;
			byte[] bytes = new byte[1024];
		 
			while ((read = inputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			inputStream.close();
			out.flush();
			out.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Works
	private void deleteFilesFromFilesFolder() {
		File[] files = getFilesDir().listFiles();
		for (File file : files) {
			System.out.println(file.getName());
			if (file.getName().equals("MyFile.txt"))
				file.delete();
		}
	}
	
	// Works
	private void ReadWriteFileInApplicationStorage() {
        /*************** WRITE FILE & Image to Application Storage Folder (data/data/package_name/files/...) *****************/
		
		// Text
        try {
        	BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File(getFilesDir()+File.separator+"MyFile.txt")));
        	bufferedWriter.write("lalit poptani");
        	bufferedWriter.close();
        	
        	/*************** Read FILE from Application Storage Folder (data/data/package_name/files/...) *****************/
        	BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(getFilesDir()+File.separator+"MyFile.txt")));
        	String read;
        	StringBuilder builder = new StringBuilder("");
        	while((read = bufferedReader.readLine()) != null){
        		builder.append(read);
        	}
        	Log.d("Output", builder.toString());
        	bufferedReader.close();
        	
        	// Image
        	FileOutputStream outputStream = new FileOutputStream(new File(getFilesDir()+File.separator+"MyImg.png"));
			Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.act_add_ons);
			bitmap.compress(CompressFormat.PNG, 100, outputStream);
//			outputStream.write("lalit poptani".getBytes());
			outputStream.close();
			
			Bitmap bitmapRead = BitmapFactory.decodeFile(getFilesDir()+File.separator+"MyImg.png");
			imageView.setImageBitmap(bitmapRead);
        	
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	// Works
	private void showDirection() {
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
				Uri.parse("http://maps.google.com/maps?saddr=" + 23.0094408
						+ "," + 72.5988541 + "&daddr=" + 22.99948365856307
						+ "," + 72.60040283203125));
		startActivity(intent);
	}
	
	// Works
	private void showPlaceWithAnOverlay() {
		Uri uri = Uri
				.parse("geo:0,0?q=22.99948365856307,72.60040283203125 (Maninagar)");
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}
	
	// Works
	private void storingArrayInSharedPreferences() {
		// Write
		
		SharedPreferences prefs = PreferenceManager
               .getDefaultSharedPreferences(this);
		JSONArray jsonArray = new JSONArray();
		jsonArray.put(1);
		jsonArray.put(2);
		Editor editor = prefs.edit();
		editor.putString("key", jsonArray.toString());
		Log.d("Write Array To Shared Preferences", jsonArray.toString());
		editor.commit();
		
		// Read 
		try {
			Log.d("Read Array From Shared Preferences", prefs.getString("key", "[]"));
	        JSONArray jsonArrayRead = new JSONArray(prefs.getString("key", "[]"));
	        for (int i = 0; i < jsonArrayRead.length(); i++) {
	        	 Log.d("your JSON Array value at position - "+i, jsonArrayRead.getInt(i)+"");
			}
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	// Works
	private void getIdentifierfForImageView(ImageView imageView) {
		imageView.setImageResource(getResources().getIdentifier("ic_launcher","drawable", getPackageName()));
	}
	
	// Works
	private void createCustomDirectory() {
		
		// creates Directory in data/data/package/
		
		File file = getDir("custom", MODE_PRIVATE);
        String path = file.getAbsolutePath();
        File create_dir = new File(path+"/dir_name");
        if(!create_dir.exists()){
        	create_dir.mkdir();
        }
	}
	
	// Works
	private void MapWithMultipleKeys() {
		 MyHashMap hashMap = new MyHashMap();
	        hashMap.put("dog", "dachshund");
	        hashMap.put("dog", "beagle");
	        hashMap.put("dog", "corgi");
	        Log.d("output", String.valueOf(hashMap));
	}
	
	// Works
	private void callLocationListener() {
		MyLocationListener myLocationListener = new MyLocationListener();
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
		System.out.println(MyLocationListener.getLatitude()+" "+MyLocationListener.getLongitude());
	}
	
	private void settingSpannable(TextView textView, String text) {
		SpannableStringBuilder stringBuilder = new SpannableStringBuilder(text);
        customSpannable = new MyCustomSpannable(""){
			@Override
			public void onClick(View widget) {
				Intent intent = new Intent(AbhiColorActivity.this, ListViewMultipleChoice.class);
				startActivity(intent);
			}
        };
        stringBuilder.setSpan(customSpannable, 0, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        
        customSpannable1 = new MyCustomSpannable("http://www.google.co.in/"){

			@Override
			public void onClick(View widget) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(customSpannable1.getUrl()));
				startActivity(intent);
			}
        };
        stringBuilder.setSpan(customSpannable1, 19, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        
        textView.setText( stringBuilder, BufferType.SPANNABLE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
	}
	
	
	private void readLogCat() throws IOException{
		StringBuilder builder = new StringBuilder();
		Process process = Runtime.getRuntime().exec("logcat -d");
		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()), 2*1024);
		String line = "";
		
		while((line = reader.readLine()) != null){
			builder.append(line);
		}
		System.out.println(builder.toString());
	}
	
	@Override
	public void onClick(View paramView) {

		Intent intent = new Intent(AbhiColorActivity.this, ListViewMultipleChoice.class);
		startActivity(intent);
	}
}