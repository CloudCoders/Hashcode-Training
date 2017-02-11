import model.Cell
import model.Pizza
import services.SlicerService

fun main(args: Array<String>) {
    val expected = Array(3) { index ->
        when (index) {
            0 -> arrayOf(T(), T(), T(), T(), T())
            1 -> arrayOf(T(), M(), M(), M(), T())
            2 -> arrayOf(T(), T(), T(), T(), T())
            else -> arrayOf(T())
        }
    }

    val slicer = SlicerService()
    val pizza = Pizza(expected, 3, 5, 1, 6)
    val slices = slicer.getSlices(pizza)

    slices.forEach(::println)
}

private fun T() = Cell("T", false)
private fun M() = Cell("M", false)