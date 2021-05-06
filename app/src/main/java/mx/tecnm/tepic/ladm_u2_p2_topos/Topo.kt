package mx.tecnm.tepic.ladm_u2_p2_topos


import android.graphics.Bitmap
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlin.random.Random

class Topo : Imagen {
    //VARIABLES
    //IMAGEN
    val imagenes: ArrayList<Bitmap>
    //ESTADO TOPO
    var golpeable: Boolean
    var estaGolpeado: Boolean
    //MODALIDAD
    var modo: Int = 0
    var dificultad: Int
    //PROGRAMA
    var ejecutando: Boolean
    var error:Int
    var velocidad:Int

    //CONSTRUCTOR DEL TOPO
    constructor(posX: Float, posY: Float, imagenes: ArrayList<Bitmap>) : super(posX, posY, imagenes[0], "TOPO") {
        this.imagenes = imagenes
        cambiarImagen(this.modo)
        this.estaGolpeado = false
        this.golpeable = false
        this.dificultad = 1
        this.ejecutando = false
        this.error = 0
        this.velocidad = 80
    }
    //METODOS
    //INICIO
    suspend fun inicio(s: Semaphore) {
        this.ejecutando = true
        delay(400)
        s.acquire()
        delay(400)
        movimiento()
        s.release()
        this.ejecutando = false
    }
    //OCULTARSE NORMAL
    suspend fun ocultarse(){
        cambiarImagen(3)
        delay(this.velocidad.toLong())
        cambiarImagen(2)
        delay(this.velocidad.toLong())
        cambiarImagen(1)
        delay(this.velocidad.toLong())
    }
    //MOVIMIENTO
    suspend fun movimiento() {
        this.golpeable = false
        cambiarImagen(1)
        delay(this.velocidad.toLong())
        cambiarImagen(2)
        delay(this.velocidad.toLong())
        this.golpeable = true
        cambiarImagen(3)
        delay(this.velocidad.toLong())
        cambiarImagen(4)
        //delay((1200 / this.dificultad).toLong()+ Random.nextLong(200))
        for(i in 0..3){
            if (this.estaGolpeado) {
                ocultarse()
                break
            }
            delay((360-(80*dificultad)).toLong())
        }
        if(!estaGolpeado && this.visible){
            ocultarse();
            this.error++
        }
        this.estaGolpeado = false
        cambiarImagen(0)
    }
    //CAMBIAR IMAGEN
    fun cambiarImagen(modo: Int) {
        this.modo = modo
        when (modo) {
            0 -> {this.img = imagenes[0]}
            1 -> {this.img = imagenes[1]}
            2 -> {this.img = imagenes[2]}
        }
    }
    //GOLPEAR
    fun golpear():Boolean {
        if (this.golpeable) {
            this.estaGolpeado = true
            return true
        }
        return false
    }

}