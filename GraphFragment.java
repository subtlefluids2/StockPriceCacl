package com.example.stockprice;

import java.util.ArrayList;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewDataInterface;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;
import com.jjoe64.graphview.LineGraphView;

import android.R.color;
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
import android.widget.TextView;

public class GraphFragment extends Fragment {
	
	GraphFragment thisFragment = this;

	private int CUSTOM_BLACK = Color.parseColor("#222222");
	private int CUSTOM_GREY = Color.parseColor("#666666");
	private int CUSTOM_WHITE = Color.parseColor("#FFFFFF");
	
	private int NUM_PAD_SHOW = 1;
	private int NUM_PAD_HIDE = 0;
	
	boolean numPadVisible = false;
	
	GraphViewSeries callOptionSeries;
	GraphViewSeries putOptionSeries;
	GraphViewSeries shadow;
	ArrayList<GraphViewData> graphViewData;
	GraphViewSeries graphMinValue;
	GraphViewSeries graphMaxValue;
	GraphView graphView;
	
	BlackScholesModel blackScholesModel;
	BlackScholesUpdateValueHelper updateValueHelper;
	
	View rootView;
	ViewGroup graphFragmentContainer;
	
	//Child layout
	ViewGroup graphContainer;
	ViewGroup graph;
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
	
	Button buttonDraw;
	
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
	

	
	public GraphFragment() {
		//Empty constructor required for fragment subclasses
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
	
		rootView = inflater.inflate(R.layout.graph_activity_fragment, container, false);
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
		
		//Create View related instances
		graphContainer = (ViewGroup) rootView.findViewById(R.id.graphContainer);
		graph = (ViewGroup) rootView.findViewById(R.id.graph);
		
		createGraphView(100.0, 100.0, (30.0/365.0), (25.0/100.0), (5.0/100.0), (1.0/100.0)); //default parameters
		((ViewGroup) graph).addView(graphView);

		//Initialize 'View' instances
		graphFragmentContainer = (ViewGroup)rootView.findViewById(R.id.graph_fragment_container);

		
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
				
		buttonDraw = (Button)rootView.findViewById(R.id.buttonDraw);
			
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
				
				
		graphFragmentContainer.removeView(numPadContainer);
				

				
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
					((GraphFragment) thisFragment).containerClicked(v.getId());
							
					break;
						
				case R.id.buttonDraw:
					resetAllContainerColor();
					toggleNumPad(NUM_PAD_HIDE);
							
					double S = Double.parseDouble(underlyingPriceValue.getText().toString());
					double X = Double.parseDouble(exercisePriceValue.getText().toString());
					double T = Double.parseDouble(daysUntilExpirationValue.getText().toString())/365;
					double sig = Double.parseDouble(historicalVolatilityValue.getText().toString().replace(" %", ""))/100;
					double r1 = Double.parseDouble(riskFreeRateValue.getText().toString().replace(" %", ""))/100;
					double r2 = Double.parseDouble(dividendYieldValue.getText().toString().replace(" %", ""))/100;
									
					//Update graph
					createGraphView(S, X, T, sig, r1, r2);
					((ViewGroup) graph).removeAllViews();
					((ViewGroup) graph).addView(graphView);
					
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
							
					((GraphFragment)thisFragment).updateContainerValue(updateValueHelper.getContainerValueId(), 
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
				
		buttonDraw.setOnClickListener(blackScholesActivityOnClickListener);
				
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
	
	private void createGraphView(
			double S, double X, double T, double sig, double r1, double r2){

		blackScholesModel = new BlackScholesModel();
		//Default parameter values
		blackScholesModel.setParameters(S, X, T, sig, r1, r2);
		

		
		graphView = new LineGraphView(getActivity(), "");
		graphView.getGraphViewStyle().setGridColor(Color.parseColor("#AAFFFFFF"));
		graphView.getGraphViewStyle().setVerticalLabelsColor(Color.parseColor("#AAFFFFFF"));
		graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.parseColor("#AAFFFFFF"));
		graphView.getGraphViewStyle().setTextSize(40);
		graphView.getGraphViewStyle().setNumVerticalLabels(6);
		graphView.getGraphViewStyle().setNumHorizontalLabels(6);
		
		
		//GraphViewSeriesStyle shadowStyle = new GraphViewSeriesStyle(Color.parseColor("#55000000"), 30);
		//shadow = new GraphViewSeries("shadow", shadowStyle, graphViewData.toArray(gvdArray));
		//graphView.addSeries(shadow);
		
		GraphViewSeriesStyle graphStyle1 = new GraphViewSeriesStyle(Color.parseColor("#FF9e2d37"), 15);
		GraphViewSeriesStyle graphStyle2 = new GraphViewSeriesStyle(Color.parseColor("#FF0667ba"), 15);
		
		graphViewData = createGraphViewData("putOption", S);
		GraphViewData[] gvdArrayPut = new GraphViewData[graphViewData.size()];
		putOptionSeries = new GraphViewSeries("graphStyle2", graphStyle2, graphViewData.toArray(gvdArrayPut));
		graphView.addSeries(putOptionSeries);
		
		graphViewData = createGraphViewData("callOption", S);
		GraphViewData[] gvdArrayCall = new GraphViewData[graphViewData.size()];
		callOptionSeries = new GraphViewSeries("graphStyle1", graphStyle1, graphViewData.toArray(gvdArrayCall));
		graphView.addSeries(callOptionSeries);
		

		
		graphView.addSeries(graphMinValue);
		graphView.addSeries(graphMaxValue);
		

	}

	private ArrayList<GraphViewData> createGraphViewData(String code, double S) {
		//We create 100 points so that 'curve' is smoothen out
		//To create 100 points, first we select 100 x-variable then apply y = f(x) to get the y-variable
		
		ArrayList<GraphViewData> graphViewData = new ArrayList<GraphViewData>();
		
		//Create/Select 100 x-variables
		double min = S - 5.0;
		double max = S + 5.0;
		ArrayList<Double> range = new ArrayList<Double>();
		
		double d = (max - min) / 100;
		int i = 0;
		
		while(i < 101){
			range.add(min + (d * i));
			i++;
		}
		
		//Create 100 points (x,y) from x-variables
		for(double s : range){
			if (code == "callOption"){
				double x = s;
				double y = blackScholesModel.calculateCallOption(s); //y = f(x)
				
				graphViewData.add(new GraphViewData(x, y));
			} else if (code == "putOption"){
				
				double x = s;
				double y = blackScholesModel.calculatePutOption(s); //y = f(x)
				
				graphViewData.add(new GraphViewData(x, y));
				
			}

			
		}
		
		//Set Min/Max coordinates
		double x = 0.0;
		int y = 0;
		
		//Set Min
		GraphViewData gvd = graphViewData.get(0); 
		x = gvd.getX();
		y = (int) gvd.getY();
		GraphViewData graphMinCoordinate = new GraphViewData(x, y);
		graphMinValue = new GraphViewSeries(new GraphViewData[] {graphMinCoordinate});
		//Set Max
		gvd = graphViewData.get(graphViewData.size() - 1);
		x = gvd.getX();
		y = (int) (gvd.getY() + 1);
		GraphViewData graphMaxCoordinate = new GraphViewData(x, y);
		graphMaxValue = new GraphViewSeries(new GraphViewData[] {graphMaxCoordinate});
		
		return graphViewData;
				
		
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
				graphFragmentContainer.removeView(graphContainer);
				graphFragmentContainer.addView(numPadContainer);
				numPadVisible = true;
			}
			
		} else if (command == 0){
			if(numPadVisible == false){
				//do nothing
			} else {
				//hide numPad
				graphFragmentContainer.removeView(numPadContainer);
				graphFragmentContainer.addView(graphContainer, 0);
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
			buttonDraw.callOnClick();
			return false;
		} else {
			return true; //return true will call super.onBackPressed() at MainActivity
		}
		
	}
	
}
