package com.andydotdaniel.jajanku.ui.components

enum class BudgetView(val value : Int) {
    MONTHLY(0),
    WEEKLY(1),
    DAILY(2);

    companion object {
        fun from(findValue: Int): BudgetView = entries.first { it.value == findValue }
    }
}