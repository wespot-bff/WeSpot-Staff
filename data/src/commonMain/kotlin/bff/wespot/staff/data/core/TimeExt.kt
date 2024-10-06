package bff.wespot.staff.data.core

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern

@OptIn(FormatStringsInDatetimeFormats::class)
fun String.toLocalDateFromDashPattern(): LocalDate {
    val formatter = LocalDate.Format {
        byUnicodePattern("yyyy-MM-dd")
    }
    return formatter.parse(this)
}
