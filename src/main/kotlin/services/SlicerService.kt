package services

import model.Pizza
import model.Slice

class SlicerService {

    fun getSlices(pizza: Pizza): List<Slice> {
        var slices: MutableList<Slice> = emptyList<Slice>().toMutableList()

        for (row in 0..(pizza.R - 1)) {
            for (column in 0..(pizza.C - 1)) {
                if (!pizza.table[row][column].used) {
                    val slice = getSliceFrom(pizza, row, column)
                    if (slice != null) {
                        slices.add(slice)
                    }
                }
            }
        }

        return slices
        //return slices.map { slice -> getBetterSlice(pizza, slice) }
    }

    private fun getSliceFrom(pizza: Pizza, row: Int, column: Int): Slice? {
        var currentColumn = column
        var currentRow = row
        var currentSize = 1
        var numColumns = 1

        while (currentSize <= pizza.H && currentColumn < pizza.C) {
            while (currentSize <= pizza.H && currentRow < pizza.R) {
                val slice = Slice(column, currentColumn, row, currentRow)
                if (checkSlice(slice, pizza)) {
                    markCellsAsUsed(pizza, row, currentRow, column, currentColumn)
                    return slice
                }
                currentRow++
                currentSize += numColumns
            }
            numColumns++
            currentSize = numColumns
            currentRow = 0
            currentColumn++
        }

        return null
    }

    private fun getBetterSlice(pizza: Pizza, slice: Slice): Slice {
        if (checkSliceSize(slice, pizza.H)) {
            var currentColumn = slice.endColumn
            var currentRow = slice.endRow + 1
            var numColumns = ((slice.endColumn - slice.startColumn) + 1)
            var currentSize = numColumns * ((currentRow - slice.startRow) + 1)

            while (currentSize <= pizza.H && currentColumn < pizza.C) {
                while (currentSize <= pizza.H && currentRow < pizza.R) {
                    val betterSlice = Slice(slice.startColumn, currentColumn, slice.startRow, currentRow)
                    if (checkSlice(betterSlice, pizza)) {
                        markCellsAsUsed(pizza, betterSlice.startRow, currentRow, betterSlice.startColumn, currentColumn)
                        return betterSlice
                    }
                    currentRow++
                    currentSize += numColumns
                }
                numColumns++
                currentSize = numColumns
                currentRow = 0
                currentColumn++
            }
        }

        return slice
    }

    private fun checkSlice(slice: Slice, pizza: Pizza): Boolean =
            checkSliceIngredients(slice, pizza) && checkSliceSize(slice, pizza.H)

    private fun checkSliceSize(slice: Slice, H: Int): Boolean =
            ((slice.endColumn - slice.startColumn) + 1) * ((slice.endRow - slice.startRow) + 1) <= H

    private fun checkSliceIngredients(slice: Slice, pizza: Pizza): Boolean {
        var mushrooms = 0
        var tomatoes = 0
        for (row in slice.startRow..slice.endRow) {
            for (column in slice.startColumn..slice.endColumn) {
                if (pizza.table[row][column].ingredient.equals("M")) mushrooms++ else tomatoes++
            }
        }

        return tomatoes >= pizza.L && mushrooms >= pizza.L
    }

    private fun markCellsAsUsed(pizza: Pizza, startingRow: Int, endingRow: Int, startingColumn: Int, endingColumn: Int) {
        for (row in startingRow..endingRow) {
            for (column in startingColumn..endingColumn) {
                pizza.table[row][column].used = true
            }
        }
    }

}