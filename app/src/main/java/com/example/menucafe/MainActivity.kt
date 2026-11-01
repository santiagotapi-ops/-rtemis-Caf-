package com.example.menucafe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.menucafe.ui.theme.MenuCafeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MenuCafeTheme {
                Surface(modifier = Modifier.fillMaxSize()){
                    MenuCafeApp()
                }

            }
        }
    }
}

data class Producto(
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val imagen: Int
)

@Composable
fun MenuCafeApp() {

    val listaProductos = listOf(
        Producto("Moka Italiana", "Método de extracción por presión mediante vapor de agua.", 9000, R.drawable.moka),
        Producto("Prensa Francesa", "Método de inmersión total con filtro de émbolo metálico creado en 1852.", 12000, R.drawable.prensa),
        Producto("Café Espresso", "Bebida muy concentrada, de cuerpo denso y con una capa superior rica en aceites emulsificados.", 7000, R.drawable.espresso),
        Producto("Hario V60", "Método Japonés de filtrado por vertido manual en cono a 60°.", 10000, R.drawable.v60),
        Producto("Chemex", "Filtrado por vertido manual diseñado por el químico alemán Peter Schlumbohm en 1941.", 12000, R.drawable.chemex),
        Producto("Cold Brew", "Inmersión prolongada en frío con el fin de extraer menos acidez y amargor, produciendo así una bebida suave, dulce y de baja acidez.", 15000, R.drawable.cold),
        Producto("Sifón Japonés", "Método híbrido de inmersión y vacíos de presión impulsados por calor que produce una taza limpia, aromática y clara mediante un proceso teatral y visual.", 17000, R.drawable.sifon),
        Producto("Café Turco", "Preparado en una pequeña vasija de cobre mezclando café molido en polvo impalpable con agua y azúcar, llevándolo a ebullición casi hasta hervir.", 17000, R.drawable.turco),
        Producto("AeroPress", "Método híbrido que combina inmersión con presión de aire impulsada manualmente dando un café con amargor mínimo, bajo sedimento y cuerpo equilibrado.", 23000, R.drawable.aeropress)
    )

    Column (modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Ártemis Café ☕",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Bienvenido al lugar donde el cafe se convierte en arte.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = {Text("Que Deseas Hoy ☕")},
            modifier = Modifier.fillMaxWidth()

        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "Metodos de preparación",
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))

        for (producto in listaProductos) {
            ProductCard(
                nombre = producto.nombre,
                descripcion = producto.descripcion,
                precio = producto.precio,
                imagen = producto.imagen
            )
            Spacer(modifier = Modifier.height(15.dp))
        }

    }

}


@Composable
fun ProductCard(nombre: String, descripcion: String, precio: Int, imagen: Int) {

    Card(modifier = Modifier.fillMaxWidth()){
        Column(
            modifier = Modifier.padding(16.dp))
        {
            Image(
                painter = painterResource(
                    id = imagen),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = nombre,
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = descripcion,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(5.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$" + precio,
                    style = MaterialTheme.typography.bodySmall
                )
                Button(onClick = {}){
                    Text(text = "Agregar")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MenuCafePreview() {
    MenuCafeTheme {
        MenuCafeApp()
    }
}