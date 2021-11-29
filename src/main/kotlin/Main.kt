fun main() {
    println("Welcome to Joozd's AOC 2018 solution runner.")
    println("Please tell us what day you want to run: ")
    when (readLine()) {
        "0" -> day0.Day0().runTimed()
        "1" -> day1.Day1().runTimed()
        "2" -> day2.Day2().runTimed()
        "3" -> day3.Day3().runTimed()
        "4" -> day4.Day4().runTimed()
        "5" -> day5.Day5().runTimed()
        "6" -> day6.Day6().runTimed()
        "7" -> day7.Day7().runTimed()
        "8" -> day8.Day8().runTimed()
        "9" -> day9.Day9().runTimed()
        "10" -> day10.Day10().runTimed()
        "11" -> day11.Day11().runTimed()
        "12" -> day12.Day12().runTimed()
        "13" -> day13.Day13().runTimed()
    }
}