package edu.mioib.qaplocalsearch.algorithm.neighboursgenerator;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Test;

public class TwoOptNeighboursIteratorUnitTest {

	@Test
	public void shouldDoesNotHasNextForEmptyState() {
		int[] state = new int[]{};
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);

		assertFalse(iterator.hasNextNeighbour());
	}

	@Test
	public void shouldHasNextForNonEmptyState() {
		int[] state = new int[] { 1, 2, 3, 4 };
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);

		assertTrue(iterator.hasNextNeighbour());
	}

	@Test
	public void shouldDoesNotHasNextAfterLastElementOfState() {
		int[] state = new int[] { 1, 2 };
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);
		iterator.nextNeighbour();

		assertFalse(iterator.hasNextNeighbour());
	}

	@Test
	public void shouldReturnCorrectNeightbours() {
		int[] state = new int[] { 1, 2, 3, 4 };
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);
		
		assertTrue(iterator.hasNextNeighbour());
		iterator.nextNeighbour();
		assertArrayEquals(new int[]{2, 1, 3, 4}, state);
		assertTrue(iterator.hasNextNeighbour());
		iterator.nextNeighbour();
		assertArrayEquals(new int[] { 3, 2, 1, 4 }, state);
		assertTrue(iterator.hasNextNeighbour());
		iterator.nextNeighbour();
		assertArrayEquals(new int[] { 4, 2, 3, 1 }, state);
		assertTrue(iterator.hasNextNeighbour());
		iterator.nextNeighbour();
		assertArrayEquals(new int[] { 1, 3, 2, 4 }, state);
		assertTrue(iterator.hasNextNeighbour());
		iterator.nextNeighbour();
		assertArrayEquals(new int[] { 1, 4, 3, 2 }, state);
		assertTrue(iterator.hasNextNeighbour());
		iterator.nextNeighbour();
		assertArrayEquals(new int[] { 1, 2, 4, 3 }, state);
		assertFalse(iterator.hasNextNeighbour());
	}
	
	@Test
	public void shouldDoesNotThrowNoSuchElementExceptionForUnexpectedNext() {
		int[] state = new int[] { 1, 2 };
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);
		iterator.nextNeighbour();
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldThrowNoSuchElementExceptionForUnexpectedNext() {
		int[] state = new int[] { 1, 2 };
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);
		iterator.nextNeighbour();
		iterator.nextNeighbour();
	}

	@Test
	public void shouldReturnsCorrectOriginalStateAfterUseOfNext() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);
		iterator.nextNeighbour();
		iterator.nextNeighbour();
		iterator.switchToOriginalState();
		assertArrayEquals(new int[] { 1, 2, 3 }, state);
	}

	@Test
	public void shouldReturnsCorrectOriginalStateAfterUseOfTheBest() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);
		iterator.nextNeighbour();
		iterator.saveCurrentNeighbourAsTheBest();
		iterator.switchToTheBestNeighbour();
		iterator.switchToOriginalState();
		assertArrayEquals(new int[] { 1, 2, 3 }, state);
	}

	@Test
	public void shouldReturnsCorrectOriginalStateAfterIteratorCreation() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);
		iterator.switchToOriginalState();
		assertArrayEquals(new int[] { 1, 2, 3 }, state);
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowIllegalStateExceptionWhenSaveIsCallBeforeNext() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);
		iterator.saveCurrentNeighbourAsTheBest();
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowIllegalStateExceptionWhenGetBestIsCallBeforeSave() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);
		iterator.nextNeighbour();
		iterator.switchToTheBestNeighbour();
	}

	@Test
	public void shouldReturnsCorrectTheBestAfterUseOfNext() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);
		iterator.nextNeighbour();
		int[] nextNeigbour = state.clone();
		iterator.saveCurrentNeighbourAsTheBest();
		iterator.nextNeighbour();
		iterator.switchToTheBestNeighbour();
		assertArrayEquals(nextNeigbour, state);
	}

	@Test
	public void shouldReturnsCorrectTheBestAfterUseOfGetState() {
		int[] state = new int[] { 1, 2, 3 };
		TwoOptStateHolder iterator = new TwoOptStateHolder(state);
		iterator.nextNeighbour();
		int[] nextNeigbour = state.clone();
		iterator.saveCurrentNeighbourAsTheBest();
		iterator.nextNeighbour();
		iterator.switchToOriginalState();
		iterator.switchToTheBestNeighbour();
		assertArrayEquals(nextNeigbour, state);
	}

}
