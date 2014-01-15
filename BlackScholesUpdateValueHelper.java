package com.example.stockprice;

import android.util.Log;

public class BlackScholesUpdateValueHelper {
	
	private int containerValueId;
	private String workOnValue;
	private String valueBeforeDecimal;
	private String valueAfterDecimal;
	private boolean decimalFlag;
	
	public BlackScholesUpdateValueHelper(int containerValueId){
		
		this.valueBeforeDecimal = "";
		this.valueAfterDecimal = "";
		
		workOnValue = valueBeforeDecimal;
		
		switch(containerValueId){
		case R.id.underlyingPriceContainer:
			this.containerValueId = R.id.underlyingPriceValue;
			break;
		case R.id.exercisePriceContainer:
			this.containerValueId = R.id.exercisePriceValue;
			break;
		case R.id.daysUntilExpirationContainer:
			this.containerValueId = R.id.daysUntilExpirationValue;
			break;
		case R.id.historicalVolatilityContainer:
			this.containerValueId = R.id.historicalVolatilityValue;
			break;
		case R.id.riskFreeRateContainer:
			this.containerValueId = R.id.riskFreeRateValue;
			break;
		case R.id.dividendYieldContainer:
			this.containerValueId = R.id.dividendYieldValue;
			break;
			
		}
	}

	public void numPadClicked(int id) {
		
		String numPadInput = "";
		int limit = 4;
		
		if(decimalFlag == true){
			workOnValue = valueAfterDecimal;
			limit = 2;
		}
		
		//This limit 7 digit in total [including '.']
		if(this.workOnValue.length() < limit){

			switch(id){
			case R.id.numPad1:
				numPadInput = "1";
				break;
			case R.id.numPad2:
				numPadInput = "2";
				break;
			case R.id.numPad3:
				numPadInput = "3";
				break;
			case R.id.numPad4:
				numPadInput = "4";
				break;
			case R.id.numPad5:
				numPadInput = "5";
				break;
			case R.id.numPad6:
				numPadInput = "6";
				break;
			case R.id.numPad7:
				numPadInput = "7";
				break;
			case R.id.numPad8:
				numPadInput = "8";
				break;
			case R.id.numPad9:
				numPadInput = "9";
				break;
			case R.id.numPad0:
				Log.i("logcat", workOnValue);
				numPadInput = "0";
				break;
			}
			
			workOnValue += numPadInput;
			
			if(decimalFlag == true){ 
				valueAfterDecimal = workOnValue;
			} else {
				valueBeforeDecimal = workOnValue ;
			}
			
			Log.i("logcat", "work: " + workOnValue);
			
		}
		
		//pressing numPadDot while 'decimal place' is already present does nothing
		if(id == R.id.numPadDot){
			if(decimalFlag == true){
			//do nothing
			} else {
				decimalFlag = true;
			}
		}
		//pressing numPadDel reset value to '0'
		else if (id == R.id.numPadDel){
			this.valueBeforeDecimal = "";
			this.valueAfterDecimal = "";
			workOnValue = valueBeforeDecimal;
			decimalFlag = false;
		}
	} 
	
	public int getContainerValueId(){
		return this.containerValueId;
	}
	
	//'Default' String returned is '0.', [i.e. numPadDel is pressed, and no further input is given]
	
	public String getValue(){
		//If valueBeforeDecimal is "", this may return ".xx", which is Parse-able
		//however, if valueAfterDecimal is also "", this will "." which is NOT Parse-able
		//Thus, a work-around is needed to avoid Non-parse-able-Error
		if(valueBeforeDecimal == ""){
			valueBeforeDecimal = "0";
		}
		Log.i("logcat", "getValue: " + valueBeforeDecimal + " " + valueAfterDecimal);
		return String.format("%s.%s", valueBeforeDecimal, valueAfterDecimal);
	}

}
