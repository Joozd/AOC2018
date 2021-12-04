package day16

import common.extensions.grabInts
import common.extensions.toInt

class Device {
    private val reg = IntArray(4) { 0 }

    /**
     * Opcodes:
     */
    //addittion
    private val addr = Opcode { a: Int, b: Int, c: Int -> reg[c] = reg[a] + reg[b] }
    private val addi = Opcode { a: Int, b: Int, c: Int -> reg[c] = reg[a] + b }

    //multiplication
    private val mulr = Opcode { a: Int, b: Int, c: Int -> reg[c] = reg[a] * reg[b] }
    private val muli = Opcode { a: Int, b: Int, c: Int -> reg[c] = reg[a] * b }

    //bitwise AND
    private val banr = Opcode { a: Int, b: Int, c: Int -> reg[c] = reg[a] and reg[b] }
    private val bani = Opcode { a: Int, b: Int, c: Int -> reg[c] = reg[a] and b }

    //bitwise OR
    private val borr = Opcode { a: Int, b: Int, c: Int -> reg[c] = reg[a] or reg[b] }
    private val bori = Opcode { a: Int, b: Int, c: Int -> reg[c] = reg[a] or b }

    //assignment
    private val setr = Opcode { a: Int, _, c: Int -> reg[c] = reg[a] }
    private val seti = Opcode { a: Int, _, c: Int -> reg[c] = a }

    //Greater-than testing
    private val gtir = Opcode { a: Int, b: Int, c: Int -> reg[c] = (a > reg[b]).toInt() }
    private val gtri = Opcode { a: Int, b: Int, c: Int -> reg[c] = (reg[a] > b).toInt() }
    private val gtrr = Opcode { a: Int, b: Int, c: Int -> reg[c] = (reg[a] > reg[b]).toInt() }

    //Equality  testing
    private val eqir = Opcode { a: Int, b: Int, c: Int -> reg[c] = (a == reg[b]).toInt() }
    private val eqri = Opcode { a: Int, b: Int, c: Int -> reg[c] = (reg[a] == b).toInt() }
    private val eqrr = Opcode { a: Int, b: Int, c: Int -> reg[c] = (reg[a] == reg[b]).toInt() }

    private val allOpcodes = listOf(addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr)

    private val foundOpcodes = HashMap<Int, Opcode>()

    val opCodes get() = foundOpcodes.map { it.key to getOpcodeName(it.value) }.toMap()

    fun possibleOpcodes(test: Test, usedOpcodes: List<Opcode> = allOpcodes): List<Opcode> {
        val originalReg = reg.toList()
        val result = usedOpcodes.filter {
            setReg(test.before)
            it(test.opcode[1], test.opcode[2], test.opcode[3])
            reg.toList() == test.after
        }
        setReg(originalReg)
        //println("Found ${result.size} opcodes for test")
        return result
    }

    fun findOpCodes(tests: List<Test>) {
        val unresolvedSamples = ArrayList(tests)
        while (unresolvedSamples.isNotEmpty() && foundOpcodes.size != allOpcodes.size){
            val unfoundOpcodes = allOpcodes.filter { it !in foundOpcodes.values }
            unresolvedSamples.forEach {
                it.matchingOpcodes = possibleOpcodes(it, unfoundOpcodes)
            }
            val resolvedSamples = unresolvedSamples.filter { it.matchingOpcodes.size == 1 }
            resolvedSamples.forEach {
                foundOpcodes[it.opcode.first()] = it.matchingOpcodes.first()
            }
            unresolvedSamples.removeAll(resolvedSamples)
        }
    }

    private fun getOpcodeName(opcode: Opcode): String?{
        val opCodes = listOf(addr, addi, mulr, muli, banr, bani, borr, bori, setr, seti, gtir, gtri, gtrr, eqir, eqri, eqrr)
        val opcodeNames = listOf("addr", "addi", "mulr", "muli", "banr", "bani", "borr", "bori", "setr", "seti", "gtir", "gtri", "gtrr", "eqir", "eqri", "eqrr")
        val map = opCodes.zip(opcodeNames).toMap()
        return map[opcode]
    }

    private fun setReg(values: Iterable<Int>){
        values.forEachIndexed{ index, i -> reg[index] = i }
    }

    fun run(program: List<List<Int>>): Int{
        program.forEach {
            runInstruction(it)
        }
        return reg[0]
    }

    private fun runInstruction(i: List<Int>){
        foundOpcodes[i[0]]!!(i[1], i[2], i[3])
    }




    fun interface Opcode{
        operator fun invoke(a: Int, b: Int, c: Int)
    }

    class Test(val before: List<Int>, val opcode: List<Int>, val after: List<Int>, var matchingOpcodes: List<Opcode> = emptyList()){
        companion object{
            fun ofString(testString: String): Test{
                val numbers = testString.grabInts().chunked(4)
                return Test(numbers[0], numbers[1], numbers[2])

            }
        }
    }

}