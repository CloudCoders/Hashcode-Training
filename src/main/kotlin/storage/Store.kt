package storage

import model.Cell
import model.Pizza
import model.Slice
import java.io.File
import java.util.*

class Store {

    fun read(fileName: String): Pizza {

        val scanner = Scanner(File(fileName))

        val R = scanner.nextInt()
        val C = scanner.nextInt()
        val L = scanner.nextInt()
        val H = scanner.nextInt()
        scanner.nextLine()

        val pizza = Array(R) { index ->
            var line = scanner.nextLine()

            val row = Array(C) { index ->
                Cell(line[index].toString(), false)
            }

            row
        }

        return Pizza(pizza, R, C, L, H)
    }

    fun write(fileName: String, slices: List<Slice>) {
        File("$fileName").printWriter().use { out ->
            out.println("${slices.size}")
            slices.forEach { slice ->
                out.println("${slice.startRow} ${slice.startColumn} ${slice.endRow} ${slice.endColumn}")
            }
        }
    }

}