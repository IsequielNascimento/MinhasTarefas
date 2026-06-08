package com.example.mytasks.utils

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar

class DateUtilsTest {

    @Test
    fun `formatarData inclui o ano de 4 digitos`() {
        val ms = startOfTodayUtcMs()
        val result = formatarData(ms)
        val anoAtual = Calendar.getInstance().get(Calendar.YEAR).toString()
        assertTrue("formatarData deve conter o ano", result.contains(anoAtual))
    }

    @Test
    fun `formatarDataCurta nao inclui o ano`() {
        val ms = startOfTodayUtcMs()
        val anoAtual = Calendar.getInstance().get(Calendar.YEAR).toString()
        assertFalse("formatarDataCurta nao deve conter o ano", formatarDataCurta(ms).contains(anoAtual))
    }

    @Test
    fun `formatarData e mais longa que formatarDataCurta`() {
        val ms = startOfTodayUtcMs()
        assertTrue(formatarData(ms).length > formatarDataCurta(ms).length)
    }

    @Test
    fun `startOfTodayUtcMs retorna meia-noite UTC`() {
        val todayStart = startOfTodayUtcMs()
        val cal = Calendar.getInstance(java.util.TimeZone.getTimeZone("UTC"))
        cal.timeInMillis = todayStart
        assertTrue(cal.get(Calendar.HOUR_OF_DAY) == 0)
        assertTrue(cal.get(Calendar.MINUTE) == 0)
        assertTrue(cal.get(Calendar.SECOND) == 0)
        assertTrue(cal.get(Calendar.MILLISECOND) == 0)
    }

    @Test
    fun `isHoje retorna true para timestamp atual`() {
        assertTrue(isHoje(System.currentTimeMillis()))
    }

    @Test
    fun `isHoje retorna false para ontem`() {
        val ontem = startOfTodayUtcMs() - 1L
        assertFalse(isHoje(ontem))
    }

    @Test
    fun `isHoje retorna false para amanha`() {
        val amanha = startOfTodayUtcMs() + 86_400_000L
        assertFalse(isHoje(amanha))
    }

    @Test
    fun `isPassado retorna true para ontem`() {
        val ontem = startOfTodayUtcMs() - 1L
        assertTrue(isPassado(ontem))
    }

    @Test
    fun `isPassado retorna false para agora`() {
        assertFalse(isPassado(System.currentTimeMillis()))
    }
}
