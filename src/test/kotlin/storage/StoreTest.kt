package storage

import model.Cell
import model.Pizza
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Test

/**

    3 4 1 5
    TMMM
    MMMM
    TTTM

 */
class StoreShould {

    private fun pizza(): Pizza {
        val store = Store()
        val pizza = store.read("test.in")
        return pizza
    }

    @Test
    fun `read a pizza row information`() {
        val pizza = pizza()

        assertThat(pizza.R, `is`(3))
    }

    @Test
    fun `read a pizza column information`() {
        val pizza = pizza()

        assertThat(pizza.C, `is`(4))
    }

    @Test
    fun `read a pizza min ingredients per slice information`() {
        val pizza = pizza()

        assertThat(pizza.L, `is`(1))
    }

    @Test
    fun `read a pizza max ingredients per slice information`() {
        val pizza = pizza()

        assertThat(pizza.H, `is`(5))
    }

    @Test
    fun `read a pizza table information`(){
        val pizza = pizza().table

        val expectedPizza = Array(3) { index ->
            getExpectedPizzaRow(index)
        }

        assertThat(pizza.size, `is`(expectedPizza.size))
        assertThat(pizza, `is`(expectedPizza))
    }

    /**
     * TMMM
     * MMMM
     * TTTM
     */
    private fun getExpectedPizzaRow(index: Int): Array<Cell> {
        val firstRow =  arrayOf(T(), M(), M(), M())
        val secondRow = arrayOf(M(), M(), M(), M())
        val thirdRow =  arrayOf(T(), T(), T(), M())

        return when(index){
            0 -> firstRow
            1 -> secondRow
            2 -> thirdRow
            else -> firstRow
        }
    }

    private fun M() = Cell("M", false)

    private fun T() = Cell("T", false)
}