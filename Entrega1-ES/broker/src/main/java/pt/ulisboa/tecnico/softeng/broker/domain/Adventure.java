package pt.ulisboa.tecnico.softeng.broker.domain;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pt.ulisboa.tecnico.softeng.broker.exception.BrokerException;
import pt.ulisboa.tecnico.softeng.broker.interfaces.ActivityInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.BankInterface;
import pt.ulisboa.tecnico.softeng.broker.interfaces.HotelInterface;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room;

public class Adventure {
	private static Logger logger = LoggerFactory.getLogger(Adventure.class);

	private static int counter = 0;

	private final String ID;
	private final Broker broker;
	private final LocalDate begin;
	private final LocalDate end;
	private final int age;
	private final String IBAN;
	private final int amount;
	private String bankPayment;
	private String roomBooking;
	private String activityBooking;

	public Adventure(Broker broker, LocalDate begin, LocalDate end, int age, String IBAN, int amount) {
		
		if( broker == null || begin == null || end == null || 
				age < 18 || age > 99 || amount < 0 ||
				IBAN == "" || IBAN == null  )
			throw new BrokerException();
		
		if(!dateCompare(begin, end))
			throw new BrokerException();
		
		this.ID = broker.getCode() + Integer.toString(++counter);
		this.broker = broker;
		this.begin = begin;
		this.end = end;
		this.age = age;
		this.IBAN = IBAN;
		this.amount = amount;

		broker.addAdventure(this);
	}

	public String getID() {
		return this.ID;
	}
	
	public Boolean dateCompare(LocalDate begin, LocalDate end){
		return (end.isAfter(begin) || end.equals(begin) );
	}

	public Broker getBroker() {
		return this.broker;
	}

	public LocalDate getBegin() {
		return this.begin; 
	}

	public LocalDate getEnd() {
		return this.end;
	}

	public int getAge() {
		return this.age;
	}

	public String getIBAN() {
		return this.IBAN;
	}

	public int getAmount() {
		return this.amount;
	}

	public String getBankPayment() {
		return this.bankPayment;
	}

	public String getRoomBooking() {
		return this.roomBooking;
	}

	public String getActivityBooking() {
		return this.activityBooking;
	}

	public void process() {
		logger.debug("process ID:{} ", this.ID);
		this.bankPayment = BankInterface.processPayment(this.IBAN, this.amount);
		this.roomBooking = HotelInterface.reserveHotel(Room.Type.SINGLE, this.begin, this.end);
		this.activityBooking = ActivityInterface.reserveActivity(this.begin, this.end, this.age);
	}

}
