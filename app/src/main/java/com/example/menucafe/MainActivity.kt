package com.example.menucafe

import android.os.Bundle


import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf


import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.menucafe.ui.theme.MenuCafeTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.style.contentPadding
import androidx.compose.material3.OutlinedButton
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight


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
    val id : Int,
    val nombre: String,
    val descripcion: String,
    val precio: Int,
    val imagen: Int
)

data class Categoria(
    val emoji: String,
    val nombre: String
)

@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun MenuCafeApp() {
    
    var searchQuery by remember {
        mutableStateOf("")
    }

    var cartQuantity by remember {
        mutableStateOf<Map<Int, Int>>(emptyMap())
    }

    val categorias = listOf(
        Categoria("☕", "Café"),
        Categoria("🥤", "Bebidas Calientes"),
        Categoria("🍹", "Bebidas Frias"),
        Categoria("🍺", "Cervezas"),
        Categoria("🍩", "Pasteleria"),
        Categoria("🍟", "Comida Rápida"),
        Categoria("🛒", "Otros")
    )

    val listaProductos = listOf(
        Producto(1,"Café Espresso", "Bebida muy concentrada, de cuerpo denso y con una capa superior rica en aceites emulsificados.", 7000, R.drawable.espresso),
        Producto(3,"Moka Italiana", "Método de extracción por presión mediante vapor de agua.", 9000, R.drawable.moka),
        Producto(4,"Prensa Francesa", "Método de inmersión total con filtro de émbolo metálico creado en 1852.", 12000, R.drawable.prensa),
        Producto(5,"Hario V60", "Método Japonés de filtrado por vertido manual en cono a 60°.", 10000, R.drawable.v60),
        Producto(6,"Chemex", "Filtrado por vertido manual diseñado por el químico alemán Peter Schlumbohm en 1941.", 12000, R.drawable.chemex),
        Producto(7,"Cold Brew", "Inmersión prolongada en frío con el fin de extraer menos acidez y amargor, produciendo así una bebida suave, dulce y de baja acidez.", 15000, R.drawable.cold),
        Producto(8,"Sifón Japonés", "Método híbrido de inmersión y vacíos de presión impulsados por calor que produce una taza limpia, aromática y clara mediante un proceso teatral y visual.", 17000, R.drawable.sifon),
        Producto(9,"Café Turco", "Preparado en una pequeña vasija de cobre mezclando café molido en polvo impalpable con agua y azúcar, llevándolo a ebullición casi hasta hervir.", 17000, R.drawable.turco),
        Producto(10,"AeroPress", "Método híbrido que combina inmersión con presión de aire impulsada manualmente dando un café con amargor mínimo, bajo sedimento y cuerpo equilibrado.", 23000, R.drawable.aeropress)
    )

    val ProductosFiltrados = listaProductos.filter { producto ->
        producto.nombre.contains(
            searchQuery,
            ignoreCase = true)
    }

    //AGREGAR AL CARRITO
    fun incrementQuantity(productid: Int) {
        val currentlyQuantify = cartQuantity[productid] ?: 0
        cartQuantity = cartQuantity + (productid to (currentlyQuantify + 1))
    }

    //ELIMINAR DEL CARRITO
    fun decrementQuantity(productid: Int) {
        val currentlyQuantify = cartQuantity[productid] ?: 0
        if (currentlyQuantify > 1) {
             cartQuantity = cartQuantity + (productid to (currentlyQuantify - 1))
        }
        else{
            cartQuantity = cartQuantity - productid
        }
    }

    //CONTAR CANTIDAD DE PRODUCTOS EN EL CARRITO
    val cartCount = cartQuantity.values.sum()



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Ártemis Café ☕") }
            )
        },
        bottomBar = {
            NavigationBar(){
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Text("🏚️")
                    },
                    label = {
                        Text("Inicio")
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = {
                        Text("🛒")
                    },
                    label = {
                        Text("Carrito ($cartCount)")
                    }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = {
                        Text("🙍‍♂️")
                    },
                    label = {
                        Text("Perfil")
                    }
                )
            }
        }
    ){

        inerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inerPadding)
                .padding(horizontal = 16.dp)
        ) {

            //Encabezado

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "El lugar donde el café se convierte en arte.",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            //BUSCADOR
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {newText ->
                    searchQuery = newText
                },
                label = { Text("Que Deseas Hoy ☕") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()

            )
            Spacer(modifier = Modifier.height(20.dp))

            //Categorias

            Text(
                text = "Categorías",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(14.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ){
                items(categorias.size){
                    CategoryBadge(categoria = categorias[it])
                }

            }


            //Encabezado

            Spacer(modifier = Modifier.height(14.dp))
            Text(
                text = "Destacados",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(14.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(22.dp)
            ) {
                if (ProductosFiltrados.isEmpty()){
                    item {
                        Text(
                            text = "No se encontraron productos.",
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                } else {
                    items(ProductosFiltrados) {
                        producto ->
                        val quantity = cartQuantity[producto.id] ?: 0
                        ProductCard(
                            producto = producto,
                            cantidad = quantity,
                            onIncrement = {
                                incrementQuantity(producto.id)
                            },
                            onDecrement = {
                                decrementQuantity(producto.id)
                            }
                        )
                        }
                    }
                }
            }
        }
    }


// TARGETAS DE CATEGORIAS
@Composable
fun CategoryBadge(categoria: Categoria){
    Card {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = categoria.emoji
            )
            Spacer(modifier = Modifier.width(5.dp))

            Text(
                text = categoria.nombre,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}


//TARGETAS DE PRODUCTOS
/*
Si la cantidad es 0, aparece boton agregar
al precionar agregar la cantidad aumenta en 1
si la cantidad es mayor a 0 aparece boton de + y -
al presionar + la cantidad aumenta en 1
al presionar - la cantidad disminuye en 1
en la barra inferior aparece el total de los productos
 */
@Composable
fun ProductCard(
    producto: Producto,
    cantidad: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {


    Card(modifier = Modifier.fillMaxWidth()) {

        Column(
            modifier = Modifier.padding(16.dp)
        )
        {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Image(
                    painter = painterResource(id = producto.imagen),
                    contentDescription = producto.nombre,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                Card(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Text(
                        text = "OFERTA!!",
                        modifier = Modifier.padding(
                            horizontal = 8.dp,
                            vertical = 4.dp
                        ),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = producto.descripcion,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$" + producto.precio,
                    style = MaterialTheme.typography.bodySmall
                )
                if (cantidad == 0) {
                    Button(onClick = onIncrement) {
                        Text(text = "Agregar")
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // BOTON DE -
                        OutlinedButton(
                            onClick = onDecrement,
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Text(text = "-")
                        }

                        //MOSTRAR CANTIDAD
                        Text(
                            text = cantidad.toString(),
                            style = MaterialTheme.typography.titleMedium
                            )

                        // BOTON DE +
                        OutlinedButton(
                            onClick = onIncrement,
                            contentPadding = PaddingValues(0.dp),
                            modifier = Modifier.size(40.dp)
                        ) {
                            Text(text = "+")
                        }


                    }
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