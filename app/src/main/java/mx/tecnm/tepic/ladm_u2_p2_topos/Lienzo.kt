package mx.tecnm.tepic.ladm_u2_p2_topos

import android.graphics.*
import android.view.MotionEvent
import android.view.View
import kotlinx.coroutines.*
import kotlinx.coroutines.android.awaitFrame
import kotlinx.coroutines.sync.Semaphore
import kotlin.random.Random

class Lienzo(p: MainActivity, Topo: ArrayList<Topo>) : View(p) {
    //VARIABLES
    //PANTALLA
    var pantalla: Int = 0
    val tamanoPantalla: RectF = RectF(0f, 0f, 1100f, 2200f)
    //CONTADOR
    var acierto: Int = 0
    //PLAY
    val botonJugar: Imagen = Imagen(500f, 1000f, BitmapFactory.decodeResource(this.resources, R.drawable.botonplay), "PLAY")
    //RESET
    val botonreset: Imagen = Imagen(500f, 1000f, BitmapFactory.decodeResource(this.resources, R.drawable.botonplay), "RESET")
    val fondo1: Imagen = Imagen(0f, 0f, BitmapFactory.decodeResource(this.resources, R.drawable.fondogeneral), "MENU")
    val fondo2: Imagen = Imagen(0f, 0f, BitmapFactory.decodeResource(this.resources, R.drawable.fondosecundario), "FONDO JUEGO")
    //TOPOS
    var Topos: ArrayList<Topo> = Topo
    var juego: toposmash?=null

    //METODO DIBUJAR
    override fun onDraw(c: Canvas) {
        super.onDraw(c)
        //FALLO EN DAR AL TOPO
        var fallo: Int = 0
        val pincel = Paint()
        //DISTINTAS VISTAS
        when (pantalla) {
            //PANTALLA DE INICIO
            0 -> {
                c.drawBitmap(fondo1.img, null, tamanoPantalla, pincel)
                botonJugar.dibujar(c, pincel)
            }
            //PANTALLA DE JUEGO
            1 -> {
                c.drawBitmap(fondo2.img, null, tamanoPantalla, pincel)
                pincel.textSize = 30f
                c.drawText("${this.acierto} Topos de ${this.juego!!.topominimo}", 15f, 150f, pincel)
                pincel.textSize = 50f
                c.drawText("Nivel: ${this.juego!!.dificultad}", 250f, 100f, pincel)
                for (i in this.Topos) {
                    i.dibujar(c, pincel)
                    fallo += i.error
                }
            }
            //PANTALLA INICIO REINICIO
            2 -> {
                c.drawBitmap(fondo1.img, null, tamanoPantalla, pincel)
                botonreset.dibujar(c, pincel)
            }
            //PANTALLA INICIO REINICIO
            3 -> {
                c.drawBitmap(fondo1.img, null, tamanoPantalla, pincel)
                botonreset.dibujar(c, pincel)
            }
        }
        //FALLAS 3 VECES
        if (fallo >= 3) {//Si pierdes
            pantalla = 3
            this.juego!!.nivel = false
            this.acierto = 0
        }
        //AUMENTA SI PASAS DE RONDA
        if (this.acierto >= 4 && this.juego!!.dificultad == 3) {
            pantalla = 2
            this.juego!!.nivel = false
            this.acierto = 0
        }
        //SUBIR DE DIFICULTAD
        if (this.acierto >= this.juego!!.topominimo) {
            juego!!.masdificil()
            this.acierto = 0
        }
        invalidate()
    }

    //METODOS DE TOUCH
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x
        var y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (botonJugar.estaEnArea(x, y) && pantalla == 0) {
                    this.pantalla = 1
                    juego!!.play()
                }
                if (botonreset.estaEnArea(x, y) && (pantalla in 2..3)) {
                    this.pantalla = 1
                    this.juego!!.reset()
                }
                for (i in Topos) {
                    if (i.estaEnArea(x, y)) {
                        if (i.golpear() && i.visible) {
                            this.acierto++
                        }
                    }
                }
            }
        }
        invalidate()
        return true
    }
}
