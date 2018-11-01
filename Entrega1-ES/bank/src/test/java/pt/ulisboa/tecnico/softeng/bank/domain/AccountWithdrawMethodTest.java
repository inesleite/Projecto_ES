package pt.ulisboa.tecnico.softeng.bank.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import pt.ulisboa.tecnico.softeng.bank.exception.BankException;

public class AccountWithdrawMethodTest {
	private Bank bank;
	private Account account;
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	
	@Before
	public void setUp() {
		this.bank = new Bank("Money", "BK01");
		Client client = new Client(this.bank, "António");
		this.account = new Account(this.bank, client);
		this.account.deposit(100);
	}

	@Test
	public void success() {
		String reference = this.account.withdraw(40);

		Assert.assertEquals(60, this.account.getBalance());
		Operation operation = this.bank.getOperation(reference);
		Assert.assertEquals(Operation.Type.WITHDRAW, operation.getType());
		Assert.assertEquals(this.account, operation.getAccount());
		Assert.assertEquals(40, operation.getValue());
	}

	@Test
	public void withdrawZeroValue(){
		exception.expect(BankException.class);
		this.account.withdraw(0);
	}
	@Test
	public void withdrawNegativeValue(){
		exception.expect(BankException.class);
		this.account.withdraw(-20);
	}
	@Test
	public void withdrawValueGreaterThanExpected(){
		exception.expect(BankException.class);
		this.account.withdraw(200);
	}
	@After
	public void tearDown() {
		Bank.banks.clear();
	}

}
