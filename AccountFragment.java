package com.example.stockprice;

import java.util.ArrayList;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

import android.R.color;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AccountFragment extends Fragment {
	
	AccountFragment thisFragment = this;
	
	String callingActivity;
	String accountEmail;
	
	View rootView;
	
	//Child layout
	FrameLayout accountActivityContainer;
	
	
	LinearLayout mContainer;
	
	LinearLayout containerFirstName;
	LinearLayout containerLastName;
	LinearLayout containerEmail;
	LinearLayout containerPhone;
	LinearLayout containerPassword;
	
	LinearLayout buttonContainerEditDelete;
	
	LinearLayout deleteDialog;
	LinearLayout editDialog;
	
	ImageView accountPicture;
	
	TextView textViewFirstname;
	TextView textViewLastname;
	TextView textViewEmail;
	TextView textViewPhone;
	
	EditText editTextFirstName;
	EditText editTextLastName;
	EditText editTextEmail;
	EditText editTextPhone;
	EditText editTextPassword;
	
	Button buttonEdit;
	Button buttonDelete;
	
	Button buttonConfirmDelete;
	Button buttonCancelDelete;
	Button buttonConfirmEdit;
	Button buttonCancelEdit;
	

	public AccountFragment() {
		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	
		rootView = inflater.inflate(R.layout.account_activity_fragment, container, false);
		
		accountActivityContainer = (FrameLayout) rootView.findViewById(R.id.accountActivityContainer);
		
		mContainer = (LinearLayout) rootView.findViewById(R.id.accountViewContainer);
		
		accountPicture = (ImageView) rootView.findViewById(R.id.account_picutre);
		
		containerFirstName = (LinearLayout) rootView.findViewById(R.id.firstNameContainer);
		containerLastName = (LinearLayout) rootView.findViewById(R.id.lastNameContainer);
		containerEmail = (LinearLayout) rootView.findViewById(R.id.emailContainer);
		containerPhone = (LinearLayout) rootView.findViewById(R.id.phoneContainer);
		containerPassword = (LinearLayout) rootView.findViewById(R.id.passwordContainer);
		
		textViewFirstname = (TextView) rootView.findViewById(R.id.firstname);
		textViewLastname = (TextView) rootView.findViewById(R.id.lastname);
		textViewPhone = (TextView) rootView.findViewById(R.id.phone);
		textViewEmail = (TextView) rootView.findViewById(R.id.email);
		
		editTextFirstName = (EditText) rootView.findViewById(R.id.editTextFirstname);
		editTextLastName = (EditText) rootView.findViewById(R.id.editTextLastname);
		editTextPhone = (EditText) rootView.findViewById(R.id.editTextPhone);
		editTextEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
		editTextPassword = (EditText) rootView.findViewById(R.id.editTextRetypePassword);
		
		containerFirstName.removeView(editTextFirstName);
		containerLastName.removeView(editTextLastName);
		containerEmail.removeView(editTextEmail);
		containerPhone.removeView(editTextPhone);
		
		buttonContainerEditDelete = (LinearLayout) rootView.findViewById(R.id.buttonContainerEditDelete);
		
		deleteDialog = (LinearLayout) rootView.findViewById(R.id.deleteDialog);
		accountActivityContainer.removeView(deleteDialog);
		
		editDialog = (LinearLayout) rootView.findViewById(R.id.editDialog);
		mContainer.removeView(editDialog);
		
		mContainer.removeView(containerPassword);
		
		buttonDelete = (Button) rootView.findViewById(R.id.buttonDelete);
		buttonEdit = (Button) rootView.findViewById(R.id.buttonEdit);
		
		accountEmail = getArguments().getString("email");
		personalizePage(accountEmail);
		
		accountPicture.setImageResource(getImageResource());
		
		OnClickListener accountFragmentOnClickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {

				
				switch(v.getId()){
				case R.id.buttonDelete:
					InitializeDeleteDialog();
					break;
				case R.id.buttonEdit:
					InitializeEditDialog();
				}
				
			}
			
		};
		
		buttonDelete.setOnClickListener(accountFragmentOnClickListener);
		buttonEdit.setOnClickListener(accountFragmentOnClickListener);
		
		return rootView;
	}

	protected void InitializeEditDialog() {
		//Remove TextView and Show EditText
		
		LinearLayout[] containers = {containerFirstName, containerLastName, 
				 containerPhone};
		
		TextView[] textViews = {textViewFirstname, textViewLastname,
				 textViewPhone};
		
		EditText[] editTexts = {editTextFirstName, editTextLastName,
				 editTextPhone};
		
		for (int i = 0; i < 3; i++){
			
			String text = (String) textViews[i].getText();
			
			containers[i].removeView(textViews[i]);
			containers[i].addView(editTexts[i]);
			
			editTexts[i].setText(text);
			
		}
		

		//Remove Edit/Delete Buttons
		mContainer.removeView(buttonContainerEditDelete);
		
		//show'Dialog'
		mContainer.addView(containerPassword, mContainer.getChildCount() - 1);
		mContainer.addView(editDialog, mContainer.getChildCount()-1);
		
		buttonConfirmEdit = (Button) rootView.findViewById(R.id.buttonConfrimEdit);
		buttonCancelEdit = (Button) rootView.findViewById(R.id.buttonCancelEdit);
		
		OnClickListener editDialogOnClickListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				
				LinearLayout[] containers = {containerFirstName, containerLastName, 
						 containerPhone};
				
				TextView[] textViews = {textViewFirstname, textViewLastname,
						 textViewPhone};
				
				EditText[] editTexts = {editTextFirstName, editTextLastName,
						 editTextPhone};
				
				switch(v.getId()){
				case R.id.buttonConfrimEdit:
					
					
					commitEdit();
					
					personalizePage(accountEmail);

					Toast toast = Toast.makeText(getActivity(), "Account information changed.", Toast.LENGTH_LONG);
			    	toast.show();
			    	
					
				case R.id.buttonCancelEdit:
					mContainer.removeView(editDialog);
					mContainer.removeView(containerPassword);
					mContainer.addView(buttonContainerEditDelete,mContainer.getChildCount()-1);
					
					for (int i = 0; i < 3; i++){
						containers[i].removeView(editTexts[i]);
						containers[i].addView(textViews[i]);
					}
					
					break;
				}
				
			}
			
		};
		
		buttonConfirmEdit.setOnClickListener(editDialogOnClickListener);
		buttonCancelEdit.setOnClickListener(editDialogOnClickListener);
		
		
	}

	protected void InitializeDeleteDialog() {
		//show delete dialog
		accountActivityContainer.addView(deleteDialog);
		
		buttonConfirmDelete = (Button) rootView.findViewById(R.id.buttonConfrimDelete);
		buttonCancelDelete = (Button) rootView.findViewById(R.id.buttonCancelDelete);
		
		OnClickListener deleteDialogOnClickListener = new OnClickListener(){

			@Override
			public void onClick(View v) {
				switch(v.getId()){
				case R.id.buttonConfrimDelete:
					//TODO
					Toast toast = Toast.makeText(getActivity(), "Account deleted.", Toast.LENGTH_LONG);
			    	toast.show();
			    	
			    	accountActivityContainer.removeView(deleteDialog);
					getActivity().onBackPressed();
					
					AccountDatabase db = new AccountDatabase(getActivity());
			    	db.deleteRow(accountEmail);
					
			    	break;
					
				case R.id.buttonCancelDelete:
					accountActivityContainer.removeView(deleteDialog);
					break;
				}
				
			}
			
		};
		
		buttonConfirmDelete.setOnClickListener(deleteDialogOnClickListener);
		buttonCancelDelete.setOnClickListener(deleteDialogOnClickListener);
		
	}
	
    private int getImageResource(){
    	int imageResource = 0;
    	
    	if (accountEmail.equals("chris.bobby@email.com")){
    		imageResource = R.drawable.man1;
    	} else if(accountEmail.equals("carlo.putra@email.com")){
    		imageResource = R.drawable.man2;
    	} else if(accountEmail.equals("erlangga@email.com")){
    		imageResource = R.drawable.man3;
    	} else if(accountEmail.equals("kai.nathan@email.com")){
    		imageResource = R.drawable.man4;
    	} else {
    		imageResource = R.drawable.man0;
    	}
    	
    	Log.i("logcat", "imageResource");
    	
    	return imageResource;
    	
    }

	public boolean onBackPressed() {
		return true;
	}
	
	public void setCallingActivity(String callingActivity){
		this.callingActivity = callingActivity;
	}
	
	private void commitEdit(){
		
		String email = textViewEmail.getText().toString();
		
		String fname = editTextFirstName.getText().toString();
		String lname = editTextLastName.getText().toString();
		String password= editTextPassword.getText().toString();
		String phone = editTextPhone.getText().toString();
		
		if (password.length() == 0){
			password = null;
		}
		
		AccountDatabase db = new AccountDatabase(getActivity());
		db.updateRow(fname, lname, password, email, phone);

	}
	
	 private void personalizePage(String email) {
		AccountDatabase db = new AccountDatabase(getActivity());
		String[] accountData =  db.getAccountDataFromEmail(email);

		textViewFirstname.setText(accountData[0]);
		textViewLastname.setText(accountData[1]);
		textViewEmail.setText(accountData[3]);
		textViewPhone.setText(accountData[4]);
		
		if (callingActivity == "MainActivity"){
			((MainActivity)getActivity()).personalizePage(accountEmail);
		}
		
	}
	
}
