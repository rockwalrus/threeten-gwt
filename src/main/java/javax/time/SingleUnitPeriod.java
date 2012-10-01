/*
 * Copyright (c) 2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.time;

import static javax.time.calendrical.LocalPeriodUnit.DAYS;
import static javax.time.calendrical.LocalPeriodUnit.FOREVER;
import static javax.time.calendrical.LocalPeriodUnit.SECONDS;

import java.io.Serializable;

import javax.time.calendrical.AdjustableDateTime;
import javax.time.calendrical.DateTimePlusMinusAdjuster;
import javax.time.calendrical.PeriodUnit;

/**
 * A period of time measured using a single unit, such as {@code 3 Days}.
 * <p>
 * This represents an amount of time measured as an amount of a single unit.
 * A set of standard units is provided in {@link javax.time.calendrical.LocalPeriodUnit}
 * and others can be added.
 * <p>
 * Basic mathematical operations are provided - plus(), minus(), multipliedBy(),
 * dividedBy(), negated() and abs(), all of which return a new instance.
 * 
 * <h4>Implementation notes</h4>
 * This class is immutable and thread-safe.
 */
public final class SingleUnitPeriod
        implements DateTimePlusMinusAdjuster, Comparable<SingleUnitPeriod>, Serializable {

    /**
     * A constant for a period of zero, measured in days.
     */
    public static final SingleUnitPeriod ZERO_DAYS = new SingleUnitPeriod(0, DAYS);
    /**
     * A constant for a period of zero, measured in seconds.
     */
    public static final SingleUnitPeriod ZERO_SECONDS = new SingleUnitPeriod(0, SECONDS);

    /**
     * Serialization version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The amount of the period.
     */
    private final long amount;
    /**
     * The unit the period is measured in.
     */
    private final PeriodUnit unit;

    /**
     * Obtains a {@code SingleUnitPeriod} from an amount and unit.
     * <p>
     * The parameters represent the two parts of a phrase like '6 Days'.
     *
     * @param amount  the amount of the period, measured in terms of the unit, positive or negative
     * @param unit  the unit that the period is measured in, must not be the 'Forever' unit, not null
     * @return the {@code SingleUnitPeriod} instance, not null
     * @throws DateTimeException if the period unit is {@link javax.time.calendrical.LocalPeriodUnit#FOREVER}.
     */
    public static SingleUnitPeriod of(long amount, PeriodUnit unit) {
        return new SingleUnitPeriod(amount, unit);
    }

    //-----------------------------------------------------------------------
    /**
     * Constructor.
     *
     * @param amount  the amount of the period, measured in terms of the unit, positive or negative
     * @param unit  the unit that the period is measured in, must not be the 'Forever' unit, not null
     * @throws DateTimeException if the period unit is {@link javax.time.calendrical.LocalPeriodUnit#FOREVER}.
     */
    private SingleUnitPeriod(long amount, PeriodUnit unit) {
        DateTimes.checkNotNull(unit, "PeriodUnit must not be null");
        if (unit == FOREVER) {
            throw new DateTimeException("Cannot create a period of the Forever unit");
        }
        this.amount = amount;
        this.unit = unit;
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this period has an amount of zero.
     * <p>
     * A {@code SingleUnitPeriod} can be positive, zero or negative.
     * This method checks whether the amount is zero.
     *
     * @return true if this period has an amount of zero
     */
    public boolean isZero() {
        return amount == 0;
    }

    //-----------------------------------------------------------------------
    /**
     * Gets the amount of this period.
     * <p>
     * For example, in the period '5 Days', the amount is '5'.
     *
     * @return the amount of time of this period, positive or negative
     */
    public long getAmount() {
        return amount;
    }

    /**
     * Gets the amount of this period, converted to an {@code int}.
     * <p>
     * For example, in the period '5 Days', the amount is '5'.
     *
     * @return the amount of time of this period, positive or negative
     * @throws ArithmeticException if the amount exceeds the capacity of an {@code int}
     */
    public int getAmountInt() {
        return DateTimes.safeToInt(amount);
    }

    /**
     * Gets the unit of this period.
     * <p>
     * For example, in the period '5 Days', the unit is 'Days'.
     *
     * @return the period unit, not null
     */
    public PeriodUnit getUnit() {
        return unit;
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this period with a different amount of time.
     * <p>
     * Calling this method returns a new period with the same unit but different amount.
     * For example, it could be used to change '3 Days' to '5 Days'.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param amount  the amount of time to set in the returned period, positive or negative
     * @return a {@code SingleUnitPeriod} based on this period with the specified amount, not null
     */
    public SingleUnitPeriod withAmount(long amount) {
        if (amount == this.amount) {
            return this;
        }
        return new SingleUnitPeriod(amount, unit);
    }

    /**
     * Returns a copy of this period with a different unit.
     * <p>
     * Calling this method returns a new period with the same amount but different unit.
     * For example, it could be used to change '3 Days' to '3 Months'.
     * This is rarely a useful operation but is included for completeness.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param unit  the unit to set in the returned period, must not be the 'Forever' unit, not null
     * @return a {@code SingleUnitPeriod} based on this period with the specified unit, not null
     * @throws DateTimeException if the period unit is {@link javax.time.calendrical.LocalPeriodUnit#FOREVER}.
     */
    public SingleUnitPeriod withUnit(PeriodUnit unit) {
        if (this.unit.equals(unit)) {
            return this;
        }
        return new SingleUnitPeriod(amount, unit);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this period with the specified period added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param periodToAdd  the period to add, positive or negative
     * @return a {@code SingleUnitPeriod} based on this period with the specified period added, not null
     * @throws DateTimeException if the specified period has a different unit
     * @throws ArithmeticException if the calculation overflows
     */
    public SingleUnitPeriod plus(SingleUnitPeriod periodToAdd) {
        DateTimes.checkNotNull(periodToAdd, "Period must not be null");
        if (periodToAdd.getUnit().equals(unit) == false) {
            throw new DateTimeException("Cannot add '" + periodToAdd + "' to '" + this + "' as the units differ");
        }
        return plus(periodToAdd.getAmount());
    }

    /**
     * Returns a copy of this period with the specified period added.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param amountToAdd  the period to add, measured in the unit of the period, positive or negative
     * @return a {@code SingleUnitPeriod} based on this period with the specified amount added, not null
     * @throws ArithmeticException if the calculation overflows
     */
    public SingleUnitPeriod plus(long amountToAdd) {
        return withAmount(DateTimes.safeAdd(this.amount, amountToAdd));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this period with the specified period subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param periodToSubtract  the period to subtract, positive or negative
     * @return a {@code SingleUnitPeriod} based on this period with the specified period subtracted, not null
     * @throws DateTimeException if the specified has a different unit
     * @throws ArithmeticException if the calculation overflows
     */
    public SingleUnitPeriod minus(SingleUnitPeriod periodToSubtract) {
        DateTimes.checkNotNull(periodToSubtract, "Period must not be null");
        if (periodToSubtract.getUnit().equals(unit) == false) {
            throw new DateTimeException("Cannot subtract '" + periodToSubtract + "' from '" + this + "' as the units differ");
        }
        return minus(periodToSubtract.getAmount());
    }

    /**
     * Returns a copy of this period with the specified period subtracted.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param amountToSubtract  the period to subtract, measured in the unit of the period, positive or negative
     * @return a {@code SingleUnitPeriod} based on this period with the specified amount subtracted, not null
     * @throws ArithmeticException if the calculation overflows
     */
    public SingleUnitPeriod minus(long amountToSubtract) {
        return withAmount(DateTimes.safeSubtract(this.amount, amountToSubtract));
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this period with the amount multiplied by the specified scalar.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param scalar  the value to multiply by, positive or negative
     * @return a {@code SingleUnitPeriod} based on this period multiplied by the specified scalar, not null
     * @throws ArithmeticException if the calculation overflows
     */
    public SingleUnitPeriod multipliedBy(long scalar) {
        return withAmount(DateTimes.safeMultiply(amount, scalar));
    }

    /**
     * Returns a copy of this period with the amount divided by the specified divisor.
     * <p>
     * This uses the {@code /} operator and integer division to provide the result.
     * For example, the result of '11 Days' divided by 4 is '2 Days'.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param divisor  the value to divide by, positive or negative
     * @return a {@code SingleUnitPeriod} based on this period divided by the specified divisor, not null
     * @throws ArithmeticException if the divisor is zero
     */
    public SingleUnitPeriod dividedBy(long divisor) {
        return withAmount(amount / divisor);
    }

    /**
     * Returns a copy of this period with the amount as the remainder following
     * division by the specified divisor.
     * <p>
     * This uses the {@code %} operator to provide the result, which may be negative.
     * For example, the remainder of '11 Days' divided by 4 is '3 Days'.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @param divisor  the value to divide by, positive or negative
     * @return a {@code SingleUnitPeriod} based on this period divided by the specified divisor, not null
     * @throws ArithmeticException if the divisor is zero
     */
    public SingleUnitPeriod remainder(long divisor) {
        return withAmount(amount % divisor);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a copy of this period with the amount negated.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @return a {@code SingleUnitPeriod} based on this period with the amount negated, not null
     * @throws ArithmeticException if the amount is {@code Long.MIN_VALUE}
     */
    public SingleUnitPeriod negated() {
        return withAmount(DateTimes.safeNegate(amount));
    }

    /**
     * Returns a copy of this period with a positive amount.
     * <p>
     * This instance is immutable and unaffected by this method call.
     *
     * @return a {@code SingleUnitPeriod} based on this period with an absolute amount, not null
     * @throws ArithmeticException if the amount is {@code Long.MIN_VALUE}
     */
    public SingleUnitPeriod abs() {
        return amount < 0 ? negated() : this;
    }

    //-------------------------------------------------------------------------
    /**
     * Adds this period to the specified date-time object.
     * 
     * @param dateTime  the date-time object to adjust, not null
     * @return an object of the same type with the adjustment made, not null
     * @throws DateTimeException if unable to add
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public AdjustableDateTime doAdd(AdjustableDateTime dateTime) {
        return dateTime.plus(amount, unit);
    }

    /**
     * Subtracts this period from the specified date-time object.
     * 
     * @param dateTime  the date-time object to adjust, not null
     * @return an object of the same type with the adjustment made, not null
     * @throws DateTimeException if unable to subtract
     * @throws ArithmeticException if numeric overflow occurs
     */
    @Override
    public AdjustableDateTime doSubtract(AdjustableDateTime dateTime) {
        return dateTime.minus(amount, unit);
    }

    //-----------------------------------------------------------------------
    /**
     * Calculates the duration of this period, which may be an estimate.
     * <p>
     * This returns the duration of the period, by multiplying the {@link PeriodUnit#getDuration() duration}
     * of a single unit by the amount. Since the duration of the unit may be an estimate,
     * the duration of this period may also be an estimate.
     * To determine if the duration is an estimate, use {@link PeriodUnit#isDurationEstimated()}.
     *
     * @return the duration of this period, which may be an estimate, not null
     */
    public Duration toDuration() {
        return unit.getDuration().multipliedBy(amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Compares this period to another period with the same unit.
     * <p>
     * If the specified period has a different unit, then an exception is thrown.
     *
     * @param otherPeriod  the other period to compare to, not null
     * @return the comparator value, negative if less, positive if greater
     * @throws IllegalArgumentException if the units are different
     */
    @Override
    public int compareTo(SingleUnitPeriod otherPeriod) {
        if (unit.equals(otherPeriod.getUnit()) == false) {
            throw new IllegalArgumentException("Units cannot be compared: " + unit + " and " + otherPeriod.getUnit());
        }
        return DateTimes.safeCompare(amount, otherPeriod.amount);
    }

    //-----------------------------------------------------------------------
    /**
     * Checks if this period is equal to another period.
     * <p>
     * The comparison is based on the unit and value.
     *
     * @param obj  the object to check, null returns false
     * @return true if this is equal to the other period
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
           return true;
        }
        if (obj instanceof SingleUnitPeriod) {
            SingleUnitPeriod other = (SingleUnitPeriod) obj;
            return this.amount == other.amount &&
                    this.unit.equals(other.unit);
        }
        return false;
    }

    /**
     * A hash code for this period.
     *
     * @return a suitable hash code
     */
    @Override
    public int hashCode() {
        return unit.hashCode() ^ (int) (amount ^ (amount >>> 32));
    }

    //-----------------------------------------------------------------------
    /**
     * Outputs this period as a {@code String}, such as {@code 6 Days}.
     * <p>
     * The output will consist of the amount, a space and the unit name.
     *
     * @return a string representation of this period, not null
     */
    @Override
    public String toString() {
        return amount + " " + unit.getName();
    }

}