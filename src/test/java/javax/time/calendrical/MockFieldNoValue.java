/*
 * Copyright (c) 2008-2012, Stephen Colebourne & Michael Nascimento Santos
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
package javax.time.calendrical;

import static javax.time.calendrical.ChronoUnit.MONTHS;
import static javax.time.calendrical.ChronoUnit.WEEKS;

import javax.time.DateTimeException;
import javax.time.jdk8.Jdk8Methods;

/**
 * Mock DateTimeField that returns null.
 */
public enum MockFieldNoValue implements DateTimeField {

    INSTANCE;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public PeriodUnit getBaseUnit() {
        return WEEKS;
    }

    @Override
    public PeriodUnit getRangeUnit() {
        return MONTHS;
    }

    @Override
    public DateTimeValueRange range() {
        return DateTimeValueRange.of(1, 20);
    }

    @Override
    public int compare(DateTimeAccessor dateTime1, DateTimeAccessor dateTime2) {
        return Jdk8Methods.compare(doGet(dateTime1), doGet(dateTime2));
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean doIsSupported(DateTimeAccessor dateTime) {
        return true;
    }

    @Override
    public DateTimeValueRange doRange(DateTimeAccessor dateTime) {
        return DateTimeValueRange.of(1, 20);
    }

    @Override
    public long doGet(DateTimeAccessor dateTime) {
        throw new DateTimeException("Mock");
    }

    @Override
    public <R extends DateTime> R doWith(R dateTime, long newValue) {
        throw new DateTimeException("Mock");
    }

    //-----------------------------------------------------------------------
    @Override
    public boolean resolve(DateTimeBuilder dateTimeBuilder, long value) {
        return false;
    }

}
