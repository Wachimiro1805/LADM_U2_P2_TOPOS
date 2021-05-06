package mx.tecnm.tepic.ladm_u2_p2_topos


import android.graphics.Bitmap
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Semaphore
import kotlin.random.Random

//DECLARACION CLASE DEL JUEGO
class toposmash {
    //variables
    //PANTALLA
    val Lienzo: Lienzo
    //TOPO
    val hoyos: ArrayList<Bitmap>
    var Topos: ArrayList<Topo>
    var topominimo: Int
    //JUEGO
    var dificultad: Int
    var nivel: Boolean
    var jugando: Boolean

    //CONSTRUCTOR DE LA CLASE
    constructor(Lienzo: Lienzo, sprites: ArrayList<Bitmap>, Topos: ArrayList<Topo>) {
        this.Lienzo = Lienzo
        this.hoyos = sprites
        this.Topos = Topos
        this.dificultad = 1
        this.topominimo = 3
        this.Lienzo.juego = this
        this.nivel = true
        this.jugando = true
        this.Topos[4].visible = false

    }
    //METODOS

    //PLAY
    fun play() {
        var semaforo = Semaphore(4)
        GlobalScope.launch(Dispatchers.IO) {
            while (jugando) {
                delay(1500)
                while (nivel) {
                    for (i in Topos) {
                        async(Dispatchers.IO) {
                            i.inicio(semaforo)
                        }
                        delay((800 + Random.nextLong(500)))
                    }
                    delay(800+ Random.nextLong(1000))
                }
                delay(1000)
            }
        }
    }
    //DIFICULTAD
    fun masdificil() {
        this.dificultad++
        when (dificultad) {
            1 -> {
                this.topominimo = 3
            }
            2 -> {
                this.topominimo = 4
                this.Topos[3].visible = true
            }
            3 -> {
                this.topominimo = 5
                this.Topos[4].visible = true
            }
        }
        for (topo in this.Topos) {
            topo.dificultad = this.dificultad
            topo.velocidad -=25
        }
        this.Lienzo.Topos = this.Topos

    }
    //RESET
    fun reset() {
        this.Topos = arrayListOf(Topo(10f, 700f, hoyos),
            Topo(350f, 480f, hoyos),
            Topo(350f, 1000f, hoyos),
            Topo(10f, 950f, hoyos),
            Topo(360f, 750f, hoyos),
            Topo(10f, 460f, hoyos))
        this.Topos[4].visible = false
        this.dificultad = 1
        this.topominimo = 3
        this.Lienzo.Topos = this.Topos
        this.nivel = true
        this.jugando = true
    }
}