package br.edu.utfpr.fuelsmart

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.utfpr.fuelsmart.databinding.ActivitySplashBinding
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Carregar o GIF usando Glide
        Glide.with(this).load(R.drawable.gif_splash).into(binding.splashImage)

        // Exibir a splash screen por 3 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            // Após 3 segundos, iniciar a MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 milissegundos = 3 segundos
    }
}