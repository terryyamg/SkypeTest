package tw.android;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private EditText edt_skypeusername;
	private Button openskype, skypemsg, skypecall, skypevideocall;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		edt_skypeusername = (EditText) findViewById(R.id.edt_skypeusername);
		openskype = (Button) findViewById(R.id.openskype);
		skypemsg = (Button) findViewById(R.id.skypemsg);
		skypecall = (Button) findViewById(R.id.skypecall);
		skypevideocall = (Button) findViewById(R.id.skypevideocall);

		// 開啟skype

		openskype.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String mySkypeUri = "skype:"; // 傳入要啟動的動作
				SkypeUri(MainActivity.this, mySkypeUri);
			}
		});

		// 傳送訊息
		skypemsg.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String skypeName = edt_skypeusername.getText().toString()
						.trim();
				if (skypeName.length() <= 0) {
					Toast.makeText(getApplicationContext(), "請於上方輸入對方ID...",
							Toast.LENGTH_SHORT).show();
				} else {
					String mySkypeUri = "skype:" + skypeName + "?chat";// 傳入要啟動的動作
					SkypeUri(MainActivity.this, mySkypeUri);
				}
			}
		});

		// Skype Audio call button click event code here
		skypecall.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String skypeName = edt_skypeusername.getText().toString()
						.trim();
				if (skypeName.length() <= 0) {
					Toast.makeText(getApplicationContext(), "請於上方輸入對方ID...",
							Toast.LENGTH_SHORT).show();
				} else {
					String mySkypeUri = "skype:" + skypeName + "?call";// 傳入要啟動的動作
					SkypeUri(MainActivity.this, mySkypeUri);
				}
			}
		});

		// Skype Video call button click event code here
		skypevideocall.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String skypeName = edt_skypeusername.getText().toString()
						.trim();
				if (skypeName.length() <= 0) {
					Toast.makeText(getApplicationContext(), "請於上方輸入對方ID...",
							Toast.LENGTH_SHORT).show();
				} else {
					String mySkypeUri = "skype:" + skypeName
							+ "?call&video=true";// 傳入要啟動的動作
					SkypeUri(MainActivity.this, mySkypeUri);
				}
			}
		});
	}

	public void SkypeUri(Context myContext, String mySkypeUri) {

		if (!isSkypeClientInstalled(myContext)) { // 判斷是否有安裝skype
			goToMarket(myContext); // 沒有安裝前往商店安裝
			return;
		}
		Uri skypeUri = Uri.parse(mySkypeUri);
		Intent myIntent = new Intent(Intent.ACTION_VIEW, skypeUri);
		myIntent.setComponent(new ComponentName("com.skype.raider",
				"com.skype.raider.Main"));
		myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		myContext.startActivity(myIntent);

		return;
	}

	// 判斷是否有安裝skype
	public boolean isSkypeClientInstalled(Context myContext) {
		PackageManager myPackageMgr = myContext.getPackageManager();
		try {
			myPackageMgr.getPackageInfo("com.skype.raider",
					PackageManager.GET_ACTIVITIES);
		} catch (PackageManager.NameNotFoundException e) {
			return (false);
		}
		return (true);
	}

	// 前往商店安裝

	public void goToMarket(Context myContext) {
		Uri marketUri = Uri.parse("market://details?id=com.skype.raider");
		Intent myIntent = new Intent(Intent.ACTION_VIEW, marketUri);
		myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		myContext.startActivity(myIntent);
		return;
	}
}