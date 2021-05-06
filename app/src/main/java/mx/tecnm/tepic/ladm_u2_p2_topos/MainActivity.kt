package mx.tecnm.tepic.ladm_u2_p2_topos

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.sync.Semaphore

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //ARRAY DE HOYOS
        val hoyos  = arrayListOf(
                BitmapFactory.decodeResource(this.resources,R.drawable.topovacio),
                BitmapFactory.decodeResource(this.resources,R.drawable.topobien),
                BitmapFactory.decodeResource(this.resources,R.drawable.topomal))
        //ARRAY TOPOS DIBUJADOS
        val Topos = arrayListOf(
                Topo(50f, 900f, hoyos),
                Topo(50f, 1000f, hoyos),
                Topo(300f, 900f, hoyos),
                Topo(300f, 1050f, hoyos),
                Topo(700f, 650f, hoyos),
                Topo(700f, 760f, hoyos))

        val juego = toposmash(Lienzo(this,Topos),hoyos,Topos)

        setContentView(juego.Lienzo)
    }
}