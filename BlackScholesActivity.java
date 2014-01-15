package com.example.stockprice;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

//DONE Change ICON
//DONE Change NavBar color - RED or BLACK
//DONE Create NumPad 
//DONE	numPad color - bg: 'black', txt: white; bgOnClick: Red
//DONE???animation
//DONE Set onClick to 'Items'
//DONE	change background & font colors
//DONE	float numPad (create new method)
//DONE Be able to edit 'values'
//DONE	gives correct output
//DONE Back button "CALCULATE" if numPad is shown
//DONE	else, works like normal 'back'

//TODO

//OPTIONAL - CHANGE ANIMATION TO scroll up and down


public class BlackScholesFragment extends Fragment{
	
	
	BlackScholesFragment thisFragment = this;
	
	ActionBar actionBar;
	
	BlackScholesModel blackScholesModel;
	BlackScholesUpdateValueHelper updateValueHelper;
	
	private int CUSTOM_RED = Color.parseColor("#9e2d37");
	private int CUSTOM_BLACK = Color.parseColor("#222222");
	private int CUSTOM_GREY = Color.parseColor("#666666");
	private int CUSTOM_WHITE = Color.parseColor("#FFFFFF");
	
	private int NUM_PAD_SHOW = 1;
	private int NUM_PAD_HIDE = 0;
	
	boolean numPadVisible = false;
	
	
	//Declaring 'View' instances 
	
	//Parent layout
	View rootView;
	ViewGroup blackScholesFragmentContainer;
	
	//Child layout
	View rowOutput;
	View numPadContainer;
	View underlyingPriceContainer;
	View exercisePriceContainer;
	View daysUntilExpirationContainer;
	View historicalVolatilityContainer;
	View riskFreeRateContainer;
	View dividendYieldContainer;
	
	//Child-Child layout
	TextView callOptionValue;
	TextView putOptionValue;
	
	TextView underlyingPriceValue;
	TextView exercisePriceValue;
	TextView daysUntilExpirationValue;
	TextView historicalVolatilityValue;
	TextView riskFreeRateValue;
	TextView dividendYieldValue;
	
	Button buttonCalculate;
	
	Button numPad1;
	Button numPad2;
	Button numPad3;
	Button numPad4;
	Button numPad5;
	Button numPad6;
	Button numPad7;
	Button numPad8;
	Button numPad9;
	Button numPad0;
	Button numPadDot;
	Button numPadDel;
	
	public BlackScholesFragment() {
		//Empty constructor required for fragment subclasses
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.black_scholes_activity_fragment, container, false);
		OnKeyListener onKeyListener = new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
		        if( keyCode == KeyEvent.KEYCODE_BACK )
		        {
		            
		        }
				return false;
			}
			
		};
		rootView.setOnKeyListener(onKeyListener);
		
		//Initialize 'View' instances
		blackScholesFragmentContainer = (ViewGroup)rootView.findViewById(R.id.black_scholes_container);

		rowOutput = rootView.findViewById(R.id.rowOutput);
		numPadContainer = rootView.findViewById(R.id.numPadContainer);
		
		underlyingPriceContainer = rootView.findViewById(R.id.underlyingPriceContainer);
		exercisePriceContainer = rootView.findViewById(R.id.exercisePriceContainer);
		daysUntilExpirationContainer = rootView.findViewById(R.id.daysUntilExpirationContainer);
		historicalVolatilityContainer = rootView.findViewById(R.id.historicalVolatilityContainer);
		riskFreeRateContainer = rootView.findViewById(R.id.riskFreeRateContainer);
		dividendYieldContainer = rootView.findViewById(R.id.dividendYieldContainer);
		
		callOptionValue = (TextView)rootView.findViewById(R.id.callOptionValue);
		putOptionValue = (TextView)rootView.findViewById(R.id.putOptionValue);
		
		underlyingPriceValue = (TextView)rootView.findViewById(R.id.underlyingPriceValue);
		exercisePriceValue = (TextView)rootView.findViewById(R.id.exercisePriceValue);
		daysUntilExpirationValue = (TextView)rootView.findViewById(R.id.daysUntilExpirationValue);
		historicalVolatilityValue = (TextView)rootView.findViewById(R.id.historicalVolatilityValue);
		riskFreeRateValue = (TextView)rootView.findViewById(R.id.riskFreeRateValue);
		dividendYieldValue = (TextView)rootView.findViewById(R.id.dividendYieldValue);
		
		buttonCalculate = (Button)rootView.findViewById(R.id.buttonCalculate);
	
		numPad1 = (Button) rootView.findViewById(R.id.numPad1);
		numPad2 = (Button) rootView.findViewById(R.id.numPad2);
		numPad3 = (Button) rootView.findViewById(R.id.numPad3);
		numPad4 = (Button) rootView.findViewById(R.id.numPad4);
		numPad5 = (Button) rootView.findViewById(R.id.numPad5);
		numPad6 = (Button) rootView.findViewById(R.id.numPad6);
		numPad7 = (Button) rootView.findViewById(R.id.numPad7);
		numPad8 = (Button) rootView.findViewById(R.id.numPad8);
		numPad9 = (Button) rootView.findViewById(R.id.numPad9);
		numPad0 = (Button) rootView.findViewById(R.id.numPad0);
		numPadDot = (Button) rootView.findViewById(R.id.numPadDot);
		numPadDel = (Button) rootView.findViewById(R.id.numPadDel);
		
		
		blackScholesFragmentContainer.removeView(numPadContainer);
		
		
		/**
		RangeSeekBar<Integer> seekBar = new RangeSeekBar<Integer>(20, 75, getActivity());
		seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Integer>() {
		        @Override
		        public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
		                // handle changed range values
		                Log.i("TAG", "User selected new range values: MIN=" + minValue + ", MAX=" + maxValue);
		        }
		});

		// add RangeSeekBar to pre-defined layout
		blackScholesActivityContainer.addView(seekBar, 1);
		**/
		
		
		blackScholesModel = new BlackScholesModel();
		
		
		//OnClickListener for 'Button' and 'TextView' in BlackScholes.java
		OnClickListener blackScholesActivityOnClickListener = new View.OnClickListener() {

			
			@Override
			public void onClick(View v) {

				switch(v.getId()){
				case R.id.underlyingPriceContainer:
				case R.id.exercisePriceContainer:
				case R.id.daysUntilExpirationContainer:
				case R.id.historicalVolatilityContainer:
				case R.id.riskFreeRateContainer:
				case R.id.dividendYieldContainer:
					
					updateValueHelper = new BlackScholesUpdateValueHelper(v.getId());
					((BlackScholesFragment) thisFragment).containerClicked(v.getId());
					
					break;
				
				case R.id.buttonCalculate:
					resetAllContainerColor();
					toggleNumPad(NUM_PAD_HIDE);
					
					double S = Double.parseDouble(underlyingPriceValue.getText().toString());
					double X = Double.parseDouble(exercisePriceValue.getText().toString());
					double T = Double.parseDouble(daysUntilExpirationValue.getText().toString())/365;
					double sig = Double.parseDouble(historicalVolatilityValue.getText().toString().replace(" %", ""))/100;
					double r1 = Double.parseDouble(riskFreeRateValue.getText().toString().replace(" %", ""))/100;
					double r2 = Double.parseDouble(dividendYieldValue.getText().toString().replace(" %", ""))/100;
					
					blackScholesModel.calculate(S, X, T, sig, r1, r2);
					
					//Update callOptionValue and putOptionValue
					callOptionValue.setText(String.format("%.3f", blackScholesModel.getCallOption()));
					putOptionValue.setText(String.format("%.3f", blackScholesModel.getPutOption()));
					
					break;
					
				case R.id.numPad1:
				case R.id.numPad2:
				case R.id.numPad3:
				case R.id.numPad4:
				case R.id.numPad5:
				case R.id.numPad6:
				case R.id.numPad7:
				case R.id.numPad8:
				case R.id.numPad9:
				case R.id.numPad0:
				case R.id.numPadDot:
				case R.id.numPadDel:
					
					updateValueHelper.numPadClicked(v.getId());
					
					((BlackScholesFragment)thisFragment).updateContainerValue(updateValueHelper.getContainerValueId(), 
							updateValueHelper.getValue());
					
					break;	
				}				
			}
		};
		
		//Registering onClickListener to 'View' instances
		underlyingPriceContainer.setOnClickListener(blackScholesActivityOnClickListener);
		exercisePriceContainer.setOnClickListener(blackScholesActivityOnClickListener);
		daysUntilExpirationContainer.setOnClickListener(blackScholesActivityOnClickListener);
		historicalVolatilityContainer.setOnClickListener(blackScholesActivityOnClickListener);
		riskFreeRateContainer.setOnClickListener(blackScholesActivityOnClickListener);
		dividendYieldContainer.setOnClickListener(blackScholesActivityOnClickListener);
		
		buttonCalculate.setOnClickListener(blackScholesActivityOnClickListener);
		
		numPad1.setOnClickListener(blackScholesActivityOnClickListener);
		numPad2.setOnClickListener(blackScholesActivityOnClickListener);
		numPad3.setOnClickListener(blackScholesActivityOnClickListener);
		numPad4.setOnClickListener(blackScholesActivityOnClickListener);
		numPad5.setOnClickListener(blackScholesActivityOnClickListener);
		numPad6.setOnClickListener(blackScholesActivityOnClickListener);
		numPad7.setOnClickListener(blackScholesActivityOnClickListener);
		numPad8.setOnClickListener(blackScholesActivityOnClickListener);
		numPad9.setOnClickListener(blackScholesActivityOnClickListener);
		numPad0.setOnClickListener(blackScholesActivityOnClickListener);
		numPadDot.setOnClickListener(blackScholesActivityOnClickListener);
		numPadDel.setOnClickListener(blackScholesActivityOnClickListener);		
		
		return rootView;
	}

	private void containerClicked(int id) {
		
		int container = 0;
		int title = 0;
		int value = 0;
		
		//Reset numPadDot visibility to VISIBLE
		numPadDot.setVisibility(View.VISIBLE);
		
		switch(id){
		case R.id.underlyingPriceContainer:
			container = R.id.underlyingPriceContainer;
			title = R.id.underlyingPriceTitle;
			value = R.id.underlyingPriceValue;
			break;
		case R.id.exercisePriceContainer:
			container = R.id.exercisePriceContainer;
			title = R.id.exercisePriceTitle;
			value = R.id.exercisePriceValue;
			break;
		case R.id.daysUntilExpirationContainer:
			
			//Set numPadDot visibility to INVISIBLE because daysUntilExpiration cannot have a decimal point
			numPadDot.setVisibility(View.INVISIBLE);
			container = R.id.daysUntilExpirationContainer;
			title = R.id.daysUntilExpirationTitle;
			value = R.id.daysUntilExpirationValue;
			break;
		
		case R.id.historicalVolatilityContainer:
			container = R.id.historicalVolatilityContainer;
			title = R.id.historicalVolatilityTitle;
			value = R.id.historicalVolatilityValue;
			break;
		case R.id.riskFreeRateContainer:
			container = R.id.riskFreeRateContainer;
			title = R.id.riskFreeRateTitle;
			value = R.id.riskFreeRateValue;
			break;
		case R.id.dividendYieldContainer:
			container = R.id.dividendYieldContainer;
			title = R.id.dividendYieldTitle;
			value = R.id.dividendYieldValue;
			break;
		}
		
		setContainerColor(container, title, value);
		toggleNumPad(NUM_PAD_SHOW);
		
	}

	private void setContainerColor(int container, int title, int value) {
		resetAllContainerColor();
		
		View v = rootView.findViewById(container);
		v.setBackgroundResource(R.drawable.color_semi_transparent_red);
		
		TextView tv = (TextView)rootView.findViewById(title);
		tv.setTextColor(CUSTOM_WHITE);
		
		tv = (TextView)rootView.findViewById(value);
		tv.setTextColor(CUSTOM_WHITE);
		
	}

	private void resetAllContainerColor() {
		int[] containerArray = {R.id.underlyingPriceContainer, R.id.exercisePriceContainer,
								R.id.daysUntilExpirationContainer, R.id.historicalVolatilityContainer,
								R.id.riskFreeRateContainer, R.id.dividendYieldContainer};
		
		int[] titleArray = {R.id.underlyingPriceTitle, R.id.exercisePriceTitle,
				R.id.daysUntilExpirationTitle, R.id.historicalVolatilityTitle,
				R.id.riskFreeRateTitle, R.id.dividendYieldTitle};
		
		int[] valueArray = {R.id.underlyingPriceValue, R.id.exercisePriceValue,
				R.id.daysUntilExpirationValue, R.id.historicalVolatilityValue,
				R.id.riskFreeRateValue, R.id.dividendYieldValue};
		
		for (int container: containerArray){
			View v = rootView.findViewById(container);
			v.setBackgroundResource(R.drawable.color_semi_transparent_white);
		}
		
		for (int title : titleArray){
			TextView tv = (TextView)rootView.findViewById(title);
			tv.setTextColor(CUSTOM_BLACK);
		}
		
		for (int value : valueArray){
			TextView tv = (TextView)rootView.findViewById(value);
			tv.setTextColor(CUSTOM_GREY);
		}
		
		}
	
	private void toggleNumPad(int command) {
	
		if(command == 1){
			if(numPadVisible == true){
				//do nothing
				
			}else{
				
				//show numPAd
				blackScholesFragmentContainer.removeView(rowOutput);
				blackScholesFragmentContainer.addView(numPadContainer);
				numPadVisible = true;
			}
			
		} else if (command == 0){
			if(numPadVisible == false){
				//do nothing
			} else {
				//hide numPad
				blackScholesFragmentContainer.removeView(numPadContainer);
				blackScholesFragmentContainer.addView(rowOutput, 0);
				numPadVisible = false;
				
			}	
		}
	}
	
	private void updateContainerValue(int containerValueId, String value) {
		
		//Because 'daysUntilExpirationDate' does not contain any decimal place, it requires a slightly different handler
		TextView tv = (TextView)rootView.findViewById(containerValueId);
		
		if(containerValueId == R.id.daysUntilExpirationValue){
		
			
			//'Default' String returned by updateValueHelper.getValue() is '0.' [see .class for more detail]
			//'0.' cannot be parseInt(), therefore must be .replace()
			//numPadDot is disabled for daysUntilExpirationContainer, so no integer will be present after decimal place '.' anyway
			tv.setText(String.format("%d", Integer.parseInt(value.replace(".", ""))));
		} else {
			
			switch(containerValueId){
			case R.id.underlyingPriceValue:
			case R.id.exercisePriceValue:

				tv.setText(String.format("%.2f", Double.parseDouble(value)));
				break;				
				
			//Because these 3 has '%', they require a slightly different handler
			case R.id.historicalVolatilityValue:
			case R.id.riskFreeRateValue:
			case R.id.dividendYieldValue:
				
				tv.setText(String.format("%.2f %%", Double.parseDouble(value)));
				break;
			}
			
		}
		
	}

	public boolean onBackPressed() {
		if(numPadVisible){
			buttonCalculate.callOnClick();
			return false;
		} else {
			return true; //return true will call super.onBackPressed() at MainActivity
		}
		
	}

}
