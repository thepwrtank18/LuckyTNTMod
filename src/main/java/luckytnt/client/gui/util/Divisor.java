package luckytnt.client.gui.util;

import com.google.common.annotations.VisibleForTesting;
import it.unimi.dsi.fastutil.ints.IntIterator;
import java.util.NoSuchElementException;

public class Divisor implements IntIterator {
	private final int denominator;
	private final int quotient;
	private final int mod;
	private int returnedParts;
	private int remainder;

	public Divisor(int divisor, int denominator) {
		this.denominator = denominator;
		if (denominator > 0) {
			quotient = divisor / denominator;
			mod = divisor % denominator;
		} else {
			quotient = 0;
			mod = 0;
		}

	}

	public boolean hasNext() {
		return returnedParts < denominator;
	}

	public int nextInt() {
		if (!hasNext()) {
			throw new NoSuchElementException();
		} else {
			int i = quotient;
			remainder += mod;
			if (remainder >= denominator) {
				remainder -= denominator;
				++i;
			}

			++returnedParts;
			return i;
		}
	}

	@VisibleForTesting
	public static Iterable<Integer> asIterable(int divisor, int denominator) {
		return () -> {
			return new Divisor(divisor, denominator);
		};
	}
}