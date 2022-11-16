package day24

import common.Solution

class Day24: Solution {
    override val day = 24
    private val lineGroups = readInput() // testInput
        .split("Infection:").map{ s ->
            s.lines().filter { it.isNotBlank() && it.first().isDigit() }
        }

    private val immuneGroupsPrototypes by lazy { makeGroups(lineGroups.first()) }
    private val infectionGroupsPrototypes by lazy { makeGroups(lineGroups.last()) }

    override fun answer1(): Any {
        val immuneGroups = makeImmuneGroups()
        val infectionGroups = makeInfectionGroups()
        runSimulation(immuneGroups, infectionGroups)
        return countUnitsStanding(immuneGroups, infectionGroups)
    }

    override fun answer2(): Any {
        var boost = 0
        var immuneGroups = makeImmuneGroups(boost)
        var infectionGroups = makeInfectionGroups()
        while(!runSimulation(immuneGroups, infectionGroups)){
            immuneGroups = makeImmuneGroups(boost++)
            infectionGroups = makeInfectionGroups()
        }
        return immuneGroups.sumOf { it.units }
    }

    private fun makeInfectionGroups(boost: Int = 0) = infectionGroupsPrototypes.map { it.copyWithBoost(boost) }.toMutableList()
    private fun makeImmuneGroups(boost: Int = 0) = immuneGroupsPrototypes.map { it.copyWithBoost(boost) }.toMutableList()

    //returns true if immune wins, false if infection wins
    private fun runSimulation(immuneGroups: MutableList<Group>, infectionGroups: MutableList<Group>): Boolean {
        while (immuneGroups.isNotEmpty() && infectionGroups.isNotEmpty()) {
            val allGroups = combineGroups(immuneGroups, infectionGroups)
            findTargets(immuneGroups, infectionGroups)

            // do attacks, if 0 attacks dealt damage we have a stalemate (both parties immune to the other)
            if (allGroups.count { it.attackTarget() } == 0)
                return false

            cleanUpGroups(immuneGroups, infectionGroups)

        }
        return infectionGroups.isEmpty()
    }

    private fun countUnitsStanding(vararg groups: List<Group>) =
        groups.toList().flatten().sumOf { it.units }

    private fun findTargets(
        group1: List<Group>,
        group2: List<Group>
    ) {
        group1.sortedByDescending { it.attackDamage }.forEach {
            it.findTarget(group2)
        }
        group2.sortedByDescending { it.attackDamage }.forEach {
            it.findTarget(group1)
        }
    }

    private fun cleanUpGroups(vararg groups: MutableCollection<Group>){
        groups.forEach{ g ->
            g.removeAll(buildSet {
                g.forEach {
                    it.resetTargeted() // bit hacky to put it here, nesting this doesn't make it all too readable sorry
                    if (it.units <= 0)
                        add(it)
                }
            })
        }
    }

    private fun combineGroups(vararg groups: List<Group>) =
        groups.toList()
            .flatten()
            .sortedByDescending { it.initiative }

    private fun makeGroups(lines: List<String>) = lines.map { Group.ofLine(it) }
}