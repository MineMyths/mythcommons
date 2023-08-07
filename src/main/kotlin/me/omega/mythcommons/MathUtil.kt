package me.omega.mythcommons

import kotlin.math.*

fun roundToFive(value: Double): Long {
    var counter = 0
    var temp = value.roundToInt()
    while (temp / 10 >= 10) {
        counter++
        temp /= 10
    }
    val nearest = 5 * 10.0.pow(counter.toDouble())
    return (ceil(value / nearest) * nearest).toLong()
}

fun toQuaternion(x: Float, y: Float, z: Float): FloatArray {
    val xRad = x * Math.PI / 180
    val yRad = y * Math.PI / 180
    val zRad = z * Math.PI / 180

    val qx = sin(xRad / 2) * cos(yRad / 2) * cos(zRad / 2) - cos(xRad / 2) * sin(yRad / 2) * sin(zRad / 2)
    val qy = cos(xRad / 2) * sin(yRad / 2) * cos(zRad / 2) + sin(xRad / 2) * cos(yRad / 2) * sin(zRad / 2)
    val qz = cos(xRad / 2) * cos(yRad / 2) * sin(zRad / 2) - sin(xRad / 2) * sin(yRad / 2) * cos(zRad / 2)
    val qw = cos(xRad / 2) * cos(yRad / 2) * cos(zRad / 2) + sin(xRad / 2) * sin(yRad / 2) * sin(zRad / 2)

    return floatArrayOf(qx.toFloat(), qy.toFloat(), qz.toFloat(), qw.toFloat())
}

fun flipQuaternion(
    axis: String,
    qx: Float,
    qy: Float,
    qz: Float,
    qw: Float
): FloatArray {

    // create a flip quaternion based on the axis
    val qFlip = when (axis) {
        "x" -> floatArrayOf(1f, 0f, 0f, 0f)
        "y" -> floatArrayOf(0f, 1f, 0f, 0f)
        "z" -> floatArrayOf(0f, 0f, 1f, 0f)
        else -> floatArrayOf(0f, 0f, 0f, 1f)
    }

    // multiply the flip quaternion by the original quaternion using the Hamilton product
    return floatArrayOf(
        qFlip[0] * qw - qFlip[1] * qx - qFlip[2] * qy - qFlip[3] * qz,
        qFlip[0] * qx + qFlip[1] * qw + qFlip[2] * qz - qFlip[3] * qy,
        qFlip[0] * qy - qFlip[1] * qz + qFlip[2] * qw + qFlip[3] * qx,
        qFlip[0] * qz + qFlip[1] * qy - qFlip[2] * qx + qFlip[3] * qw
    )
}