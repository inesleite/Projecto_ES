package pt.ulisboa.tecnico.softeng.hotel.domain;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pt.ulisboa.tecnico.softeng.hotel.domain.Hotel;
import pt.ulisboa.tecnico.softeng.hotel.domain.Room.Type;
import pt.ulisboa.tecnico.softeng.hotel.exception.HotelException;

public class HotelConstructorTest {

	@Before
	public void setUp() {

	}

	@Test
	public void success() {
		Hotel hotel = new Hotel("XPTO123", "Londres");

		Assert.assertEquals("Londres", hotel.getName());
		Assert.assertTrue(hotel.getCode().length() == Hotel.CODE_SIZE);
		Assert.assertEquals(0, hotel.getNumberOfRooms());
		Assert.assertEquals(1, Hotel.hotels.size());
	}
	
	@Test(expected = HotelException.class)
	public void emptyCode(){
		Hotel hotel = new Hotel("XPTO123", "");
	}
	
	@Test(expected = HotelException.class)
	public void nullCode(){
		Hotel hotel = new Hotel("XPTO123", "");
	}
	@Test(expected = HotelException.class)
	public void nullLocation(){
		Hotel hotel = new Hotel("XPTO123", null);
	}
	
	@Test(expected = HotelException.class)
	public void unique(){
		Hotel hotel = new Hotel("XPTO123", "Londres");
		Hotel hotel2 = new Hotel("XPTO123", "Lds");
	}
	
	@Test(expected = HotelException.class)
	public void lessThanSevenChars(){
		Hotel hotel = new Hotel("XP11", "Londres");
	}
	
	@Test(expected = HotelException.class)
	public void MoreThanSevenChars(){
		Hotel hotel = new Hotel("XP11po0943", "Londres");
	}
	
	@After
	public void tearDown() {
		Hotel.hotels.clear();
	}
	
}
