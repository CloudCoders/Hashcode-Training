import services.SlicerService
import storage.Store

val store = Store()
var slicer = SlicerService()

fun main(args: Array<String>) {
    todos()
}

fun todos() {
    val files = arrayOf("small", "medium", "example", "big")
    files.forEach { file ->
        run {
            val pizza = store.read("$file.in")
            val slices = slicer.getSlices(pizza)
            println("$file: ${slices.fold(0) { total, next -> total + next.getScore() }}")
            store.write("$file.out", slices)
        }
    }
}