package ru.kamotora.lab3;

import android.util.Log;

import java.math.BigInteger;
import java.util.function.Supplier;

public class SimpleNumbersFounder implements Supplier<Long> {

    private final int needCountDigits;
    private final int digitForSearch;
    private final char digitForSearchChar;

    private SimpleNumbersFounder(int needCountDigits, int digitForSearch) {
        if (needCountDigits < 1)
            throw new IllegalArgumentException("needCountOnes");
        this.needCountDigits = needCountDigits;
        if (digitForSearch > 10 || digitForSearch < 1)
            throw new IllegalArgumentException("digitForSearch");
        this.digitForSearch = digitForSearch;
        this.digitForSearchChar = (char) (digitForSearch + '0');
    }

    /**
     * Вернуть простое число, в котором {@link SimpleNumbersFounder#needCountDigits} единиц
     *
     * @return найденное простое число или -1, если не удалось найти
     */
    @Override
    public Long get() {
        Log.d(getClass().getSimpleName(), String.format("Start counting for %d with digit %d%n", needCountDigits, digitForSearch));
        if (needCountDigits <= 1)
            return (long) digitForSearch;
        long startFrom = BigInteger.TEN.pow(needCountDigits - 1).longValue();
        for (long current = startFrom; current < 1111111111111111111L; current++) {
            long sqrtRes = (long) Math.ceil(Math.sqrt(current));
            boolean isSimple = true;
            for (long divider = 2; divider <= sqrtRes; divider++) {
                if (current % divider == 0) {
                    isSimple = false;
                    break;
                }
            }
            if (isSimple) {
                long currentOnes = Long.toString(current)
                        .chars()
                        .filter(digit -> digit == digitForSearchChar)
                        .count();
                if (currentOnes == needCountDigits) {
                    Log.d(getClass().getSimpleName(), String.format("Counting for %d with digit %d ended successfully%n"
                            , needCountDigits, digitForSearch));

                    return current;
                }
            }
        }
        Log.d(getClass().getSimpleName(), String.format("Counting for %d with digit %d ended with -1%n"
                , needCountDigits, digitForSearch));
        return -1L;
    }

    public static SimpleNumbersFounder init(int countOnes) {
        return new SimpleNumbersFounder(countOnes, 1);
    }

    public static SimpleNumbersFounder init(int countDigits, int digitForSearch) {
        return new SimpleNumbersFounder(countDigits, digitForSearch);
    }
}
