package services

import model.Pizza
import model.Slice

class SlicerService {

    fun getSlices(pizza: Pizza): Array<Slice> {
        var slices = emptyArray<Slice>()

        for (row in 0..(pizza.R - 1)) {
            for (column in 0..(pizza.C - 1)) {
                if (!pizza.table[row][column].used) {
                    val slice = getSliceFrom(pizza, row, column)
                    if (slice != null) {
                        slices = slices.plusElement(slice)
                    }
                }
            }
        }

        return slices
    }

    private fun getSliceFrom(pizza: Pizza, row: Int, column: Int): Slice? {
        var currentColumn = column
        var currentRow = row
        var currentSize = 1
        var numColumns = 1

        while (currentSize < pizza.H && currentColumn < pizza.C) {
            while (currentSize < pizza.H && currentRow < pizza.R) {
                val slice = Slice(column, currentColumn, row, currentRow)
                if (checkSlice(slice, pizza, pizza.L, pizza.H)) {
                    markCellsAsUsed(pizza, row, currentRow, column, currentColumn)
                    return slice
                }
                currentRow++
                currentSize += numColumns
            }
            numColumns++
            currentSize = numColumns
        }

        return null
    }

    fun checkSlice(slice: Slice, pizza: Pizza, L: Int, H: Int): Boolean {
        var mushrooms = 0
        var tomatoes = 0
        for (row in slice.startRow..slice.endRow) {
            for (column in slice.startColumn..slice.endColumn) {
                if (pizza.table[row][column].ingredient.equals("M")) mushrooms++ else tomatoes++
            }
        }

        val sliceSize = ((slice.endColumn - slice.startColumn) + 1) * ((slice.endRow - slice.startRow) + 1)

        return sliceSize <= H && tomatoes >= L && mushrooms >= L

    }

    private fun markCellsAsUsed(pizza: Pizza, startingRow: Int, endingRow: Int, startingColumn: Int, endingColumn: Int) {
        for (row in startingRow..endingRow) {
            for (column in startingColumn..endingColumn) {
                pizza.table[row][column].used = true
            }
        }
    }

}