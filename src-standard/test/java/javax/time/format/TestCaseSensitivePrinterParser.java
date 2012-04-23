/*
 * Copyright (c) 2009-2012, Stephen Colebourne & Michael Nascimento Santos
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
package javax.time.format;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Test CaseSensitivePrinterParser.
 */
@Test(groups={"implementation"})
public class TestCaseSensitivePrinterParser extends AbstractTestPrinterParser {

    //-----------------------------------------------------------------------
    public void test_print() throws Exception {
        CaseSensitivePrinterParser pp = CaseSensitivePrinterParser.SENSITIVE;
        StringBuilder buf = new StringBuilder();
        pp.print(printContext, buf);
        assertEquals(buf.toString(), "");
    }

    public void test_print_nulls() throws Exception {
        CaseSensitivePrinterParser pp = CaseSensitivePrinterParser.SENSITIVE;
        pp.print(null, null);
    }

    //-----------------------------------------------------------------------
    public void test_parse_changeStyle_sensitive() throws Exception {
        CaseSensitivePrinterParser pp = CaseSensitivePrinterParser.SENSITIVE;
        int result = pp.parse(parseContext, "a", 0);
        assertEquals(result, 0);
        assertEquals(parseContext.isCaseSensitive(), true);
    }

    public void test_parse_changeStyle_insensitive() throws Exception {
        CaseSensitivePrinterParser pp = CaseSensitivePrinterParser.INSENSITIVE;
        int result = pp.parse(parseContext, "a", 0);
        assertEquals(result, 0);
        assertEquals(parseContext.isCaseSensitive(), false);
    }

    //-----------------------------------------------------------------------
    public void test_toString_sensitive() throws Exception {
        CaseSensitivePrinterParser pp = CaseSensitivePrinterParser.SENSITIVE;
        assertEquals(pp.toString(), "ParseCaseSensitive(true)");
    }

    public void test_toString_insensitive() throws Exception {
        CaseSensitivePrinterParser pp = CaseSensitivePrinterParser.INSENSITIVE;
        assertEquals(pp.toString(), "ParseCaseSensitive(false)");
    }

}
