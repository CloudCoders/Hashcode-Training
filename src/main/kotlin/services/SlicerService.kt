package services

import model.Pizza
import model.Slice

class SlicerService {

    fun slice(pizza: Pizza, L: Int, H: Int): Array<Slice> {
        val slices = emptyArray<Slice>()

        for (row in 0..pizza.R) {
            for (column in 0..pizza.C) {
                if (!pizza.table[row][column].used) {

                    var startingRow = row
                    var startingColumn = column
                    var ingredients = 1
                    var startingIngredient = pizza.table[row][column].ingredient
                    var currentSize = 1

                    for (currentRow in startingRow..pizza.R) {
                        while (currentSize < H) {
                            var currentColumn = 0
                            while (currentColumn < pizza.C) {
                                if (!pizza.table[currentRow][currentColumn].ingredient.equals(startingIngredient)) ingredients++
                                currentSize = (currentRow + 1) * (currentColumn + 1)
                                currentColumn++
                            }

                            if (ingredients != L) {
                                currentSize = (currentRow + 1)
                            } else {
                                val slice = Slice(startingRow, currentRow, startingColumn, currentColumn)
                                slices.plus(slice)
                                markCellsAsUsed(pizza, startingRow, currentRow, startingColumn, currentColumn)
                            }
                            currentColumn = 0
                        }
                    }


                }
            }
        }

        return slices
    }

    fun checkSlice(slice: Slice, pizza: Pizza, L: Int, H: Int): Boolean {
        var ingredients = 0
        val seenIngredients = emptyArray<String>()
        for (row in slice.startRow..slice.endRow) {
            for (column in slice.startColumn..slice.endColumn) {
                if (!seenIngredients.contains(pizza.table[row][column].ingredient)) {
                    seenIngredients.plus(pizza.table[row][column].ingredient)
                    ingredients++
                    if (ingredients > L) return false
                }
            }
        }

        val sliceSize = ((slice.endColumn - slice.startColumn) + 1) * ((slice.endRow - slice.startRow) + 1)

        return sliceSize <= H && ingredients == L

    }

    private fun markCellsAsUsed(pizza: Pizza, startingRow: Int, endingRow: Int, startingColumn: Int, endingColumn: Int) {
        for (row in startingRow..endingRow) {
            println("row $row")
            for (column in startingColumn..(endingColumn - 1)) {
                println("column $column")
                pizza.table[row][column].used = true
            }
        }
    }

}