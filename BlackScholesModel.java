package com.example.stockprice;


public class BlackScholesModel {
	
	private double callOption;
	private double putOption;
	
	double S, X, T, sig, r1, r2;
	
	public BlackScholesModel() {

	}
	
	public void setParameters(double S, double X, double T, double sig, double r1, double r2) {
		
		this.S = S;
		this.X = X;
		this.T = T;
		this.sig = sig;
		this.r1 = r1;
		this.r2 = r2;
		
	}
	
	public double calculateCallOption(double S){
		
		double r  = this.r1 - this.r2;
		
		//d1 = [ln(S/X) + (r+(sig^2 /2)T] / sig(sqrt(T))
		
		double d1_1 = Math.log((S/X));
		double d1_2 = (r + 0.5 * Math.pow(sig, 2)) * T;
		double d1_3 = sig * Math.sqrt(T);
		double d1 = (d1_1 + d1_2) / d1_3;
		
		//d2 = d1 - sig(sqrt(T)
		double d2 = d1 - (sig * Math.sqrt(T));
		
		//Calculate callOption
		callOption = (Math.exp(((-r2)*T)) * S * normalDistribution(d1)) 
				- X * Math.exp((-r1)*T) * normalDistribution((d1 - (sig * Math.sqrt(T))));
		
		//putOption = (X * Math.exp(-r1 * T) * normalDistribution(-d2))
		//		- Math.exp(-r2 * T) * S * normalDistribution(-d1);
		
		return this.callOption;
		
	}
	
	public double calculatePutOption(double s2) {
		double r  = this.r1 - this.r2;
		
		//d1 = [ln(S/X) + (r+(sig^2 /2)T] / sig(sqrt(T))
		
		double d1_1 = Math.log((S/X));
		double d1_2 = (r + 0.5 * Math.pow(sig, 2)) * T;
		double d1_3 = sig * Math.sqrt(T);
		double d1 = (d1_1 + d1_2) / d1_3;
		
		//d2 = d1 - sig(sqrt(T)
		double d2 = d1 - (sig * Math.sqrt(T));
		
		//Calculate callOption
		//callOption = (Math.exp(((-r2)*T)) * S * normalDistribution(d1)) 
		//		- X * Math.exp((-r1)*T) * normalDistribution((d1 - (sig * Math.sqrt(T))));
		
		//Calculate PutOption
		putOption = (X * Math.exp(-r1 * T) * normalDistribution(-d2))
				- Math.exp(-r2 * T) * S * normalDistribution(-d1);
		
		return this.putOption;
	}
	
	public void calculate(double S, double X, double T, double sig, double r1, double r2){
		
		double r  = r1 - r2;
		
		//d1 = [ln(S/X) + (r+(sig^2 /2)T] / sig(sqrt(T))
		
		double d1_1 = Math.log((S/X));
		double d1_2 = (r + 0.5 * Math.pow(sig, 2)) * T;
		double d1_3 = sig * Math.sqrt(T);
		double d1 = (d1_1 + d1_2) / d1_3;
		
		//d2 = d1 - sig(sqrt(T)
		double d2 = d1 - (sig * Math.sqrt(T));
		
		//Calculate callOption
		callOption = (Math.exp(((-r2)*T)) * S * normalDistribution(d1)) 
				- X * Math.exp((-r1)*T) * normalDistribution((d1 - (sig * Math.sqrt(T))));
		
		putOption = (X * Math.exp(-r1 * T) * normalDistribution(-d2))
				- Math.exp(-r2 * T) * S * normalDistribution(-d1);
		
	}

	public double getCallOption(){
		return callOption;
	}
	
	public double getPutOption(){
		return putOption;
	}
	
	private double erf(double x){
		
		//A&S formula 7.1.26
        double a1 = 0.254829592;
        double a2 = -0.284496736;
        double a3 = 1.421413741;
        double a4 = -1.453152027;
        double a5 = 1.061405429;
        double p = 0.3275911;
        
        x = Math.abs(x);
        
        double t = 1 / (1 + p * x);

        return 1 - ((((((a5 * t + a4) * t) + a3) * t + a2) * t) + a1) * t * Math.exp(-1 * x * x);
		
	}
	
	private double normalDistribution(double z) {
		double sign = 1;
		if(z < 0) sign = -1;
		return 0.5 * (1.0 + sign * erf(Math.abs(z)/Math.sqrt(2)));
	}
	
}
