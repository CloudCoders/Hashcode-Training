import services.SlicerService
import storage.Store

fun main(args: Array<String>) {
    val files = arrayOf("small", "medium", "example", "big")
    val store = Store()
    val slicer = SlicerService()
    files.forEach { file ->
        run {
            val pizza = store.read("$file.in")
            val slices = slicer.getSlices(pizza)
            store.write("$file.out", slices)
        }
    }
}