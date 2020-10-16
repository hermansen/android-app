package se.swosch.jackson.ui.main

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class MainFragmentTest {

    @Test
    fun test() {
        val date = LocalDate.of(2020, 10, 3)
        val result = DateTimeFormatter.ofPattern("EEEE, d MMM")
            .withZone(ZoneId.of("Europe/Stockholm"))
            .format(date)

        println(result)

        val otherResult = DateTimeFormatter.ofPattern("EEEE, d MMM", Locale.forLanguageTag("sv"))
            .withZone(ZoneId.systemDefault())
            .format(date)

        println(otherResult)
    }
}