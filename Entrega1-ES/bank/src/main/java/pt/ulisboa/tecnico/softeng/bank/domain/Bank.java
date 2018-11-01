package pt.ulisboa.tecnico.softeng.bank.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;


public class Bank {
	public static Set<Bank> banks = new HashSet<>();

	public static final int CODE_SIZE = 4;

	private final String name;
	private final String code;
	private final Set<Account> accounts = new HashSet<>();
	private final Set<Client> clients = new HashSet<>();
	private final List<Operation> log = new ArrayList<>();

	public Bank(String name, String code) {
		checkCode(code);
		if (name == null || (name.trim().length() < 1)){
			throw new BankException();
		}
		this.name = name;
		this.code = code;

		Bank.banks.add(this);
	}

	private void checkCode(String code) {
		if(code==null){
			throw new BankException();
		}
		if (code.length() != Bank.CODE_SIZE) {
			throw new BankException();
		}
		
		for(Bank b : banks){
			if(b.getCode().equals(code)){
				throw new BankException();
			}
		}
	}

	String getName() {
		return this.name;
	}

	String getCode() {
		return this.code;
	}

	int getNumberOfAccounts() {
		return this.accounts.size();
	}

	int getNumberOfClients() {
		return this.clients.size();
	}

	void addAccount(Account account) {
		this.accounts.add(account);
	}

	boolean hasClient(Client client) {
		return this.clients.contains(client);
	}

	void addClient(Client client) {
		this.clients.add(client);
	}

	void addLog(Operation operation) {
		this.log.add(operation);
	}

	public Account getAccount(String IBAN) {
		
		/* Null IBAN */
		if( IBAN == null || IBAN == "" || IBAN.contains(" ") )
			throw new BankException();
		
		/* Bank with no accounts */
		
		if( this.accounts.size() == 0 ){
			throw new BankException();
		}
		
		/*IBAN length > CODE_SIZE*/
		
		if ( IBAN.length() <= Bank.CODE_SIZE ){
			throw new BankException();
		}
		
		/* IBAN not from this bank */
		
		if ( !IBAN.startsWith( this.getCode().toString() ) ){
			throw new BankException();
		}
		
		for (Account account : this.accounts) {
			if (account.getIBAN().equals(IBAN)) {
				return account;
			}
		}
		throw new BankException();
	}

	public Operation getOperation(String reference) {
		for (Operation operation : this.log) {
			if (operation.getReference().equals(reference)) {
				return operation;
			}
		}
		return null;
	}

	public static String processPayment(String IBAN, int amount) {
		for (Bank bank : Bank.banks) {
			if (bank.getAccount(IBAN) != null) {
				return bank.getAccount(IBAN).withdraw(amount);
			}
		}
		return null;
	}

}
