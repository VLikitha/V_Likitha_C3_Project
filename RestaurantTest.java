import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
	
		
	LocalTime openingTime = LocalTime.parse("10:30:00");
	LocalTime closingTime = LocalTime.parse("22:00:00");
	int initialMenuSize;
	
	Restaurant restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
	
	
	
	@BeforeEach
	public void before() {
		restaurant.addToMenu("Sweet corn soup", 119);
		restaurant.addToMenu("Vegetable lasagne", 269);
		initialMenuSize = restaurant.getMenu().size();
	}

	@Test
	public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
		Restaurant restaurantSpy = Mockito.spy(restaurant);
 		LocalTime as = LocalTime.parse("11:00:00");
		when(restaurantSpy.getCurrentTime()).thenReturn(as);
		assertEquals(true, restaurantSpy.isRestaurantOpen());
		
	}

	@Test
	public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
		Restaurant restaurantSpy = Mockito.spy(restaurant);
 		LocalTime as = LocalTime.parse("23:00:00");
		when(restaurantSpy.getCurrentTime()).thenReturn(as);
		assertEquals(false, restaurantSpy.isRestaurantOpen());

		//adding to improve code coverage
		as = LocalTime.parse("09:00:00");
		when(restaurantSpy.getCurrentTime()).thenReturn(as);
		assertEquals(false, restaurantSpy.isRestaurantOpen());
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// >>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	@Test
	public void adding_item_to_menu_should_increase_menu_size_by_1() {

		restaurant.addToMenu("Sizzling brownie", 319);
		assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
	}

	@Test
	public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

		restaurant.removeFromMenu("Vegetable lasagne");
		assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
	}

	@Test
	public void removing_item_that_does_not_exist_should_throw_exception() {
		assertThrows(itemNotFoundException.class, () -> restaurant.removeFromMenu("French fries"));
	}
	
	@Test
	public void testGetTotalValue() {
		List<Item> items = new ArrayList<>();
		Item dish = new Item("soup",200);
		items.add(dish);
    	dish = new Item("vegetable lasagne",250);
    	items.add(dish); 
    	assertEquals(450,restaurant.getOrderValue(items));
	}
	// <<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	
	
}