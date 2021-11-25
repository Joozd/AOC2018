fun main() {
    println("Welcome to Joozd's AOC 2021 solution runner.")
    println("Please tell us what day you want to run: ")
    when (readLine()) {
        "0" -> day0.Day0().runTimed()
        "1" -> day1.Day1().runTimed()
        "2" -> day2.Day2().runTimed()
        "3" -> day3.Day3().runTimed()
        "4" -> day4.Day4().runTimed()
        "5" -> day5.Day5().runTimed()
        "6" -> day6.Day6().runTimed()
    }
}