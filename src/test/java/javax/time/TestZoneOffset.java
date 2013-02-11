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
package javax.time;

import static javax.time.calendrical.ChronoField.OFFSET_SECONDS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.time.calendrical.ChronoField;
import javax.time.calendrical.DateTimeAccessor;
import javax.time.calendrical.DateTimeField;
import javax.time.calendrical.JulianDayField;

import org.testng.annotations.Test;

/**
 * Test ZoneOffset.
 */
@Test
public class TestZoneOffset extends AbstractDateTimeTest {

    //-----------------------------------------------------------------------
    @Override
    protected List<DateTimeAccessor> samples() {
        DateTimeAccessor[] array = {ZoneOffset.ofHours(1), ZoneOffset.ofHoursMinutesSeconds(-5, -6, -30) };
        return Arrays.asList(array);
    }

    //-----------------------------------------------------------------------
	@Test(groups={"tck"})
    public void test_serialization() throws Exception {
        ZoneOffset test = ZoneOffset.of("+01:30");
        ZoneOffset result = (ZoneOffset) writeThenRead(test);
        assertEquals(result.getTotalSeconds(), 90 * 60);
    }

    @Test(groups={"tck"})
    public void test_serialization_format() throws ClassNotFoundException, IOException {
        assertEqualsSerialisedForm(ZoneOffset.of("+18:00"));
    }

    //-----------------------------------------------------------------------
    @Override
    protected List<DateTimeField> validFields() {
        DateTimeField[] array = {
            OFFSET_SECONDS,
        };
        return Arrays.asList(array);
    }

    @Override
    protected List<DateTimeField> invalidFields() {
        List<DateTimeField> list = new ArrayList<DateTimeField>(Arrays.<DateTimeField>asList(ChronoField.values()));
        list.removeAll(validFields());
        list.add(JulianDayField.JULIAN_DAY);
        list.add(JulianDayField.MODIFIED_JULIAN_DAY);
        list.add(JulianDayField.RATA_DIE);
        return list;
    }

    @Test(groups={"implementation"})
    public void test_factory_ofTotalSecondsSame() {
        assertSame(ZoneOffset.ofTotalSeconds(0), ZoneOffset.UTC);
    }

}
