package model

data class Slice(val startColumn: Int,
                 val endColumn: Int,
                 val startRow: Int,
                 val endRow: Int) {

    fun getScore(): Int {
        return ((this.endColumn - this.startColumn) + 1) * ((this.endRow - this.startRow) + 1)
    }

}