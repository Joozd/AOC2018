package day24

import common.extensions.grabInts

class Group private constructor(
    initialUnits: Int,
    private val hitpointsPerUnit: Int,
    private val damagePerUnit: Int,
    private val attackType: String,
    val initiative: Int, // not private because they get sorted by this
    private val weakTo: List<String>,
    private val immuneTo: List<String>
    ) {
    var units = initialUnits; private set

    var boost = 0

    val attackDamage get() = maxOf (0, units * (damagePerUnit + boost)) // a group can get negative units after being attacked.

    // copying is probably a lot faster than building it from a string again. Didn't test, might if I feel like it.
    fun copyWithBoost(boost: Int) = Group(units, hitpointsPerUnit, damagePerUnit, attackType, initiative, weakTo, immuneTo).apply{
        this.boost = boost
    }

    private var target: Group? = null

    private var targeted = false

    fun resetTargeted(){
        targeted = false
    }

    fun findTarget(possibleTargets: Collection<Group>){
        target = possibleTargets
            .filter { !it.targeted && it.checkReceivedDamage(this) != 0 }
            .sortedByDescending { it.attackDamage }
            .maxByOrNull { it.checkReceivedDamage(this)}
            .also{ it?.targeted = true }
    }

    // returns true if they damaged the target, false if not or no target
    fun attackTarget() =
        target?.receiveDamageFrom(this) ?: false


    private fun checkReceivedDamage(attacker: Group): Int = when(attacker.attackType){
        in weakTo -> (attacker.attackDamage * 2)//.also { println("attacking weakness, damage doubled - ${attacker.attackDamage} * 2 = $it")}
        in immuneTo -> 0
        else -> attacker.attackDamage//.also { println ( "$attackType !in $weakTo")}
    }

    private fun receiveDamageFrom(attacker: Group): Boolean{
        val killed = checkReceivedDamage(attacker) / hitpointsPerUnit
        units -= killed
        return killed > 0
    }

    override fun toString(): String =
        "$units units w/ $hitpointsPerUnit hp, weak = $weakTo, immune = $immuneTo, attack = $damagePerUnit $attackType, initiative = $initiative"


    companion object{
        private val damageTypeRegex = """does \d+ ([a-z]+) damage""".toRegex()
        private val weaknessRegex = """weak to ([a-z].+?)[;)]""".toRegex()
        private val immuneRegex = """immune to ([a-z].+?)[;)]""".toRegex()

        fun ofLine(line: String): Group{
            val intsInLine = line.grabInts()
            val initialUnits = intsInLine[0]
            val hitpointsPerUnit = intsInLine[1]
            val attackDamage = intsInLine[2]
            val initiative = intsInLine[3]

            val damageType = damageTypeRegex.find(line)!!.groupValues[1]
            val weakTo = weaknessRegex.find(line)?.groupValues?.get(1)?.split(", ") ?: emptyList()
            val immuneTo = immuneRegex.find(line)?.groupValues?.get(1)?.split(", ") ?: emptyList()

            return Group(initialUnits, hitpointsPerUnit, attackDamage, damageType, initiative, weakTo, immuneTo)
        }
    }
}