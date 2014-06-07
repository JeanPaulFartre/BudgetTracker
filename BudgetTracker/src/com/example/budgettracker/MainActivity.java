package com.example.budgettracker;

import java.util.ArrayList;

import models.AccountModel;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.utilities.BudgetTrackerContract.AccountEntry;
import com.example.utilities.DatabaseUtility;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		
		DatabaseUtility dbu = new DatabaseUtility(getBaseContext());
		SQLiteDatabase db = dbu.getReadableDatabase();
		

		Cursor c = db.query(AccountEntry.TABLE_NAME, null , null, null, null, null, AccountEntry.COLUMN_ACCOUNT_NAME + " ASC" );
		int anyRows = c.getColumnCount();
		if (anyRows >0 ){
			populateAccountList( c );
		}
		
		String prompt = getString(R.string.new_account_prompt);
		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setText(prompt);

		setContentView(R.layout.fragment_main); 
		
	}

	public void onCreateNewAccount(View view){
		Intent intent = new Intent(this, CreateNewAccountActivity.class );
		
		startActivity(intent);
	}
	
	public void populateAccountList(Cursor table){
		String accountName;
		double balance;
		ArrayList<AccountModel> accounts = new ArrayList<AccountModel>();
		int nameIndex, balanceIndex;
		ListView listView = new ListView(this);
		
		
		table.moveToFirst();
		for (int i = 0; i < table.getCount(); i++){
			nameIndex = table.getColumnIndex(AccountEntry.COLUMN_ACCOUNT_NAME);
			balanceIndex = table.getColumnIndex(AccountEntry.COLUMN_BALANCE);
			
			accountName = table.getString(nameIndex);
			balance = table.getDouble(balanceIndex);
			
			AccountModel account = new AccountModel(balance, accountName);
			
		}
		
		
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
