package com.sistdist.CuentaBancaria;

class CuentaBancaria {
	
	double saldo;
	
	CuentaBancaria(double saldo) {
		this.saldo = saldo;
	}
	
	public void incrementar(double valor) {
		saldo = saldo + valor;
	}
	
	public void  decrementar(double valor) {
		saldo = saldo - valor;
	}

	public double getSaldo() {
		return saldo;
	}
	
}

abstract class OperacionBancaria extends Thread {
	long cant;
	CuentaBancaria cuenta;

	public OperacionBancaria(long cant, CuentaBancaria cuenta) {
		this.cant = cant;
		this.cuenta = cuenta;
	}

	@Override
	public void run() {
		for(long i = 0; i < cant; i++) {
			operar( 1 );
		}
	}

	public abstract void operar( double valor );
}

class OperacionBancariaIncrementar extends OperacionBancaria {
	public OperacionBancariaIncrementar(long cant, CuentaBancaria cuenta) {
		super(cant, cuenta);
	}

	@Override
	public void operar( double valor ) {
		cuenta.incrementar( valor );
	}
}

class OperacionBancariaDecrementar extends OperacionBancaria {
	public OperacionBancariaDecrementar(long cant, CuentaBancaria cuenta) {
		super(cant, cuenta);
	}

	@Override
	public void operar( double valor ) {
		cuenta.decrementar( valor );
	}
}


public class Banco {

	public static void main(String[] args) {
		try {
		
			CuentaBancaria cuenta = new CuentaBancaria(2000000);
			CuentaBancaria cuenta2 = new CuentaBancaria(2000000);
			
			OperacionBancariaIncrementar inc = new OperacionBancariaIncrementar(1000000, cuenta);
			OperacionBancariaDecrementar dec = new OperacionBancariaDecrementar(1000000, cuenta);
			
			OperacionBancariaIncrementar inc2 = new OperacionBancariaIncrementar(1000000, cuenta2);
			OperacionBancariaDecrementar dec2 = new OperacionBancariaDecrementar(1000000, cuenta2);
			
			inc.start();
			dec.start();
			
			inc2.start();
			dec2.start();


			inc2.join();
			dec2.join();

			inc.join();
			dec.join();
			
			System.out.printf( "El saldo de la cuenta es %f \n", cuenta.getSaldo() );
			System.out.printf( "El saldo de la cuenta2 es %f \n", cuenta2.getSaldo() );
		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
