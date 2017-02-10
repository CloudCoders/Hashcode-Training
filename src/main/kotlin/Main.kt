import model.Pizza
import model.Slice
import model.Cell

class Main {

    fun algo(pizza: Pizza): Array<Slice> {
        var i : Int = 0
        var j : Int = 0
        var rowEnd : Int = 0
        var colEnd : Int = 0


        while(i < pizza.R) {
            if(!pizza.table[i][j].used) {
                var minIngredients : Boolean = false
                var maxSliceSize : Boolean = false

                rowEnd = i
                colEnd = j
                var slice : List<Array<Cell>>
                while(!minIngredients && !maxSliceSize) {
                    //1st Check -> 1 down
                    slice = pizza.table
                            .slice(IntRange(i, rowEnd + 1))
                            .slice(IntRange(j, colEnd))
                    minIngredients = checkMinIngredients(slice, pizza.L)
                    maxSliceSize = checkMaxSliceSize(rowEnd + 1 - i, colEnd - j, pizza.H)
                    if (minIngredients && maxSliceSize) break

                    //2nd Check -> 1 right
                    slice = pizza.table
                            .slice(IntRange(i, rowEnd))
                            .slice(IntRange(j, colEnd+1))
                    minIngredients = checkMinIngredients(slice, pizza.L)
                    maxSliceSize = checkMaxSliceSize(rowEnd -i , colEnd + 1 - j, pizza.H)
                    if (minIngredients && maxSliceSize) break


                    //3rd Check -> 1 down + 1 right
                    slice = pizza.table
                            .slice(IntRange(i, rowEnd+1))
                            .slice(IntRange(j, colEnd+1))
                    minIngredients = checkMinIngredients(slice, pizza.L)
                    maxSliceSize = checkMaxSliceSize(rowEnd + 1 - i , colEnd + 1 - j, pizza.H)
                    if (minIngredients && maxSliceSize) break

                }
            }
            i++
        }
        pizza.table[0][0]

    }

    fun checkMinIngredients(slice: List<Array<Cell>>, L: Int): Boolean {
        var mushroom: Int = 0
        var tomato: Int = 0
        for(row in slice) {
            for(cell in row) {
                if (cell.ingredient == "T") tomato++ else mushroom++
            }
        }
        return (tomato > L) && (mushroom > L)
    }

    fun checkMaxSliceSize(rows : Int, columns: Int, max : Int) : Boolean {
        return rows*columns < max
    }
}