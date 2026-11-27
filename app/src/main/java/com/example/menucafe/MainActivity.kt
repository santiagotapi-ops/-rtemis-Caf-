package com.example.menucafe

import android.R.attr.fontWeight
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.clickable
import androidx.compose.material3.CardDefaults
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.LaunchedEffect



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
    val imagen: Int,
    val categoria: String
)

data class Categoria(
    val emoji: String,
    val nombre: String
)


data class MetodoCafe(
    val nombre: String,
    val origen: String,
    val tiempo: String,
    val cuerpo: String,
    val acidez: String,
    val perfilSabor: String,
    val recomendacion: String,
    val imagen: Int
)

object Routes {
    const val HOME = "inicio"
    const val CART = "carrito"
    const val GUIDE = "guia"
}


@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun MenuCafeApp() {
    
    var searchQuery by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("Menú")
    }

    var cartQuantity by remember {
        mutableStateOf<Map<Int, Int>>(emptyMap())
    }

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route


    val categorias = listOf(
        Categoria("📋", "Menú"),
        Categoria("☕", "Café"),
        Categoria("🍹", "Bebidas Frias"),
        Categoria("🍩", "Pasteleria"),
        Categoria("🍟", "Comida Rápida"),
        Categoria("🍺", "Cervezas")
    )

    val listaProductos = listOf(
        // CAFÉS
        Producto(1, "Café Espresso", "Bebida muy concentrada, de cuerpo denso y con una capa superior rica en aceites emulsificados.", 6000, R.drawable.espresso, "Café"),
        Producto(2, "Café Latte", "Base de café espresso suavizada con abundante leche vaporizada y una fina capa de microespuma cremosa.", 7000, R.drawable.lattee, "Café"),
        Producto(3, "Moka Italiana", "Método de extracción por presión mediante vapor de agua.", 9000, R.drawable.moka, "Café"),
        Producto(4, "Prensa Francesa", "Método de inmersión total con filtro de émbolo metálico creado en 1852.", 12000, R.drawable.prensa, "Café"),
        Producto(5, "Hario V60", "Método Japonés de filtrado por vertido manual en cono a 60°.", 10000, R.drawable.v60, "Café"),
        Producto(6, "Chemex", "Filtrado por vertido manual diseñado por el químico alemán Peter Schlumbohm en 1941.", 12000, R.drawable.chemex, "Café"),
        Producto(7, "Cold Brew", "Inmersión prolongada en frío con el fin de extraer menos acidez y amargor, produciendo así una bebida suave, dulce y de baja acidez.", 15000, R.drawable.cold, "Café"),
        Producto(8, "Sifón Japonés", "Método híbrido de inmersión y vacíos de presión impulsados por calor que produce una taza limpia, aromática y clara mediante un proceso teatral y visual.", 17000, R.drawable.sifon, "Café"),
        Producto(9, "Café Turco", "Preparado en una pequeña vasija de cobre mezclando café molido en polvo impalpable con agua y azúcar, llevándolo a ebullición casi hasta hervir.", 17000, R.drawable.turco, "Café"),
        Producto(10, "AeroPress", "Método híbrido que combina inmersión con presión de aire impulsada manualmente dando un café con amargor mínimo, bajo sedimento y cuerpo equilibrado.", 23000, R.drawable.aeropress, "Café"),

        // PASTELERÍA
        Producto(11, "Torta de Zanahoria", "Porción de bizcocho húmedo y especiado con zanahoria rallada, un toque de canela y nueces.", 5500, R.drawable.zanahoria, "Pasteleria"),
        Producto(12, "Torta de Banano", "Porción esponjosa y aromática elaborada con bananos maduros seleccionados, ideal para acompañar tu café.", 5000, R.drawable.banano, "Pasteleria"),
        Producto(13, "Torta de Almojábana", "Porción suave y ligeramente dulce con el sabor tradicional del queso campesino horneado.", 5500, R.drawable.almohabana, "Pasteleria"),
        Producto(14, "Torta de Chocolate", "Porción intensa y húmeda de bizcocho de cacao cubierta con una suave capa de chocolate.", 6500, R.drawable.choco, "Pasteleria"),
        Producto(15, "Minidonas", "Porción de 6 minidonas esponjosas recién hechas con glaseado dulce y toppings variados.", 7000, R.drawable.donas, "Pasteleria"),

        // COMIDA RÁPIDA
        Producto(16, "Sandwich", "Pan artesanal relleno de jamón, queso fundido, lechuga fresca, tomate y salsa de la casa.", 6500, R.drawable.sandwich, "Comida Rápida"),
        Producto(17, "Sandwich de Pollo", "Pechuga de pollo desmechada y sazonada con queso fundido, vegetales frescos y salsa especial.", 7500, R.drawable.sanduchepolllo, "Comida Rápida"),
        Producto(18, "Salchipapa", "Porción generosa de papas a la francesa crujientes acompañadas de salchicha dorada y salsas al gusto.", 9000, R.drawable.salchi, "Comida Rápida"),
        Producto(19, "Hotdog", "Pan suave con salchicha americana, queso derretido, ripio de papa y salsas tradicionales.", 7500, R.drawable.hotdog, "Comida Rápida"),
        Producto(20, "Hamburguesa", "Carne artesanal a la parrilla con queso fundido, lechuga, tomate, cebolla y salsa de la casa en pan brioche.", 9000, R.drawable.hamburguesas, "Comida Rápida"),
        Producto(21, "Pizza Personal", "Masa crujiente horneada al momento con salsa napolitana, abundante queso mozzarella y jamón.", 8500, R.drawable.pizza, "Comida Rápida"),

        // BEBIDAS FRÍAS
        Producto(22, "Jugo en Agua", "Jugo natural refrescante preparado al instante con pulpa de fruta seleccionada y agua fría.", 6500, R.drawable.jugoagua, "Bebidas Frias"),
        Producto(23, "Jugo en Leche", "Bebida cremosa batida al momento con pulpa de fruta natural y leche entera bien fría.", 7500, R.drawable.jugoleche, "Bebidas Frias"),
        Producto(24, "Limonada", "Refrescante limonada natural recién exprimida con el equilibrio justo entre acidez y dulzor.", 4500, R.drawable.limonada, "Bebidas Frias"),
        Producto(25, "Té Hatsu", "Infusión de té listo para beber en presentaciones refrescantes y bajas en calorías (400 ml).", 6500, R.drawable.hatsu, "Bebidas Frias"),
        Producto(26, "Agua", "Botella de agua pura refrescante, ideal para acompañar cualquier bebida o comida.", 3500, R.drawable.agua, "Bebidas Frias"),

        // CERVEZAS
        Producto(27, "BBC", "Cerveza artesanal colombiana de cuerpo equilibrado, espuma cremosa y notas aromáticas.", 12000, R.drawable.bbc, "Cervezas"),
        Producto(28, "Club Colombia", "Cerveza premium colombiana tipo lager, dorada, refrescante y con notas suaves de malta.", 8000, R.drawable.club, "Cervezas"),
        Producto(29, "Heineken", "Cerveza lager internacional de sabor limpio, refrescante y con un sutil toque amargo.", 12000, R.drawable.hei, "Cervezas"),
        Producto(30, "Budweiser", "Cerveza tipo American Lager ligera, suave y muy refrescante.", 12000, R.drawable.bud, "Cervezas")
    )



    fun navigateTo (route: String){
        navController.navigate(route){
            popUpTo(
                navController.graph.findStartDestination().id
            ){
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    //AGREGAR AL CARRITO
    fun increaseQuantity(productid: Int) {
        val currentlyQuantify = cartQuantity[productid] ?: 0
        cartQuantity = cartQuantity + (productid to (currentlyQuantify + 1))
    }

    //ELIMINAR DEL CARRITO
    fun decreaseQuantity(productid: Int) {
        val currentlyQuantify = cartQuantity[productid] ?: 0
        if (currentlyQuantify > 1) {
             cartQuantity = cartQuantity + (productid to (currentlyQuantify - 1))
        }
        else{
            cartQuantity = cartQuantity - productid
        }
    }

    // VACIAR CARRITO
    fun clearCart() {
        cartQuantity = emptyMap()
    }

    //CONTAR CANTIDAD DE PRODUCTOS EN EL CARRITO
    val cartCount = cartQuantity.values.sum()



    Scaffold(
        topBar = {
            TopAppBar(
                /*colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                ),*/
                title = { Text(
                    text = "Ártemis Café ☕",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                    )
                }
            )
        },
        bottomBar = {
            NavigationBar(){
                NavigationBarItem(
                    selected = currentRoute == Routes.HOME,
                    onClick = {navigateTo(Routes.HOME)},
                    icon = {
                        Text("🏚️")
                    },
                    label = {
                        Text("Inicio")
                    }
                )
                NavigationBarItem(
                    selected = currentRoute == Routes.CART,
                    onClick = {navigateTo(Routes.CART)},
                    icon = {
                        Text("🛒")
                    },
                    label = {
                        Text("Carrito ($cartCount)")
                    }
                )
                NavigationBarItem(
                    selected = currentRoute == Routes.GUIDE,
                    onClick = {navigateTo(Routes.GUIDE)},
                    icon = {
                        Text("📖")
                    },
                    label = {
                        Text("Guia de Metodos")
                    }
                )
            }
        }
    ){

        inerPadding ->

        NavHost(
            navController = navController,
            startDestination = Routes.HOME,
            modifier = Modifier
                .fillMaxSize()
                .padding(inerPadding)
        ){
            //PANTALLAS
            //VISTA HOME
            composable(Routes.HOME) {
                HomeScreen(
                    searchQuery = searchQuery,
                    onSearchChange = { newText ->
                        searchQuery = newText
                    },
                    categorias = categorias,
                    selectedCategory = selectedCategory,
                    onCategorySelect = { nuevaCategoria ->
                        selectedCategory = nuevaCategoria
                        searchQuery = ""
                    },
                    listaProductos = listaProductos,
                    cartQuantity = cartQuantity,
                    onIncrease = { productId ->
                        increaseQuantity(productId)
                    },
                    onDecrease = { productId ->
                        decreaseQuantity(productId)
                    }
                )
            }

            //VISTA CARRITO
            composable(Routes.CART){
                CartScreen(
                    listaProductos = listaProductos,
                    cartQuantity = cartQuantity,
                    onIncrease = {productId ->
                        increaseQuantity(productId)
                    },
                    onDecrease = {productId ->
                        decreaseQuantity(productId)
                    },
                    onClearCart = {
                        clearCart()
                    }
                )
            }

            //VISTA GUIA DE METODOS
            composable(Routes.GUIDE){
                GuideScreen()
            }
        }


        }
    }


@Composable
fun HomeScreen(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    categorias: List<Categoria>,
    selectedCategory: String,
    onCategorySelect: (String) -> Unit,
    listaProductos: List<Producto>,
    cartQuantity: Map<Int, Int>,
    onIncrease: (Int) -> Unit,
    onDecrease: (Int) -> Unit
) {
    // 1. Estado para controlar el scroll de la lista
    val listState = rememberLazyListState()

    // 2. Regresa al primer elemento cada vez que cambia la categoría o la búsqueda
    LaunchedEffect(selectedCategory, searchQuery) {
        listState.scrollToItem(0)
    }

    val productosFiltrados = if (searchQuery.isNotBlank()) {
        listaProductos.filter { producto ->
            producto.nombre.contains(searchQuery, ignoreCase = true)
        }
    } else if (selectedCategory == "Menú") {
        listaProductos
    } else {
        listaProductos.filter { producto ->
            producto.categoria == selectedCategory
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "\"El lugar donde el café se convierte en arte.\"",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // BUSCADOR
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            label = { Text("Qué Deseas Hoy ☕") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        // CATEGORÍAS
        Text(
            text = "Categorías",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(14.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(categorias) { categoria ->
                CategoryBadge(
                    categoria = categoria,
                    isSelected = searchQuery.isBlank() && selectedCategory == categoria.nombre,
                    onClick = { onCategorySelect(categoria.nombre) }
                )
            }
        }

        // TÍTULO DINÁMICO DE LA SECCIÓN
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = if (searchQuery.isNotBlank()) "Resultados de búsqueda" else selectedCategory,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(10.dp))

        // 3. Conecta listState al LazyColumn
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            if (productosFiltrados.isEmpty()) {
                item {
                    Text(
                        text = "No se encontraron productos.",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            } else {
                items(productosFiltrados) { producto ->
                    val quantity = cartQuantity[producto.id] ?: 0
                    ProductCard(
                        producto = producto,
                        cantidad = quantity,
                        onIncrement = { onIncrease(producto.id) },
                        onDecrement = { onDecrease(producto.id) }
                    )
                }
            }
        }
    }
}

//PANTALLA CARRITO
@Composable
fun CartScreen(
    listaProductos: List<Producto>,
    cartQuantity: Map<Int, Int>,
    onIncrease: (Int) -> Unit,
    onDecrease: (Int) -> Unit,
    onClearCart: () -> Unit
) {
    val productosSeleccionados = listaProductos.filter { producto ->
        (cartQuantity[producto.id] ?: 0) > 0
    }

    val total = productosSeleccionados.sumOf { producto ->
        val cantidad = cartQuantity[producto.id] ?: 0
        producto.precio * cantidad
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Tu Carrito",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            if (productosSeleccionados.isNotEmpty()) {
                OutlinedButton(onClick = onClearCart) {
                    Text(text = "Limpiar carrito")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (productosSeleccionados.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No hay productos en el carrito.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(productosSeleccionados) { producto ->
                    val cantidad = cartQuantity[producto.id] ?: 0
                    val subtotal = producto.precio * cantidad

                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = producto.nombre,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Precio: $${producto.precio}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Cantidad: $cantidad",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Precio por cantidad: $$subtotal",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                // BOTÓN DE -
                                OutlinedButton(
                                    onClick = { onDecrease(producto.id) },
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.size(40.dp)
                                ) {
                                    Text(text = "-")
                                }

                                // MOSTRAR CANTIDAD
                                Text(
                                    text = cantidad.toString(),
                                    style = MaterialTheme.typography.titleMedium
                                )

                                // BOTÓN DE +
                                OutlinedButton(
                                    onClick = { onIncrease(producto.id) },
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

        Spacer(modifier = Modifier.height(16.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total:",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$$total",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


//PANTALLA GUÍA DE MÉTODOS
@Composable
fun GuideScreen() {
    val listaMetodos = remember {
        listOf(
            MetodoCafe(
                nombre = "Café Espresso",
                origen = "Italia · Presión (9 bares)",
                tiempo = "25 - 30 seg",
                cuerpo = "Muy alto y denso",
                acidez = "Media",
                perfilSabor = "Intenso, concentrado, con crema avellanada y notas a cacao tostado.",
                recomendacion = "Ideal si buscas un golpe rápido de energía o una taza corta y potente.",
                imagen = R.drawable.espresso
            ),
            MetodoCafe(
                nombre = "Café Latte",
                origen = "Italia · Espresso y leche vaporizada",
                tiempo = "30 - 45 seg",
                cuerpo = "Cremoso y sedoso",
                acidez = "Muy baja",
                perfilSabor = "Suave y naturalmente dulce; la leche vaporizada equilibra la intensidad del espresso con una fina capa de microespuma.",
                recomendacion = "Perfecto si prefieres una bebida láctea, reconfortante y menos amarga.",
                imagen = R.drawable.lattee
            ),
            MetodoCafe(
                nombre = "Hario V60",
                origen = "Japón · Goteo manual",
                tiempo = "2:30 - 3:00 min",
                cuerpo = "Ligero y sedoso",
                acidez = "Brillante",
                perfilSabor = "Taza limpia que resalta notas florales, cítricas y frutales del grano.",
                recomendacion = "Perfecto para cafés de origen único con tueste claro o medio.",
                imagen = R.drawable.v60
            ),
            MetodoCafe(
                nombre = "Chemex",
                origen = "Alemania · Filtrado grueso",
                tiempo = "3:30 - 4:30 min",
                cuerpo = "Muy ligero y cristalino",
                acidez = "Media-Alta",
                perfilSabor = "Sin aceites ni sedimentos; sabor dulce, delicado y aromático.",
                recomendacion = "Excelente para compartir y apreciar matices suaves sin amargor.",
                imagen = R.drawable.chemex
            ),
            MetodoCafe(
                nombre = "Prensa Francesa",
                origen = "Francia · Inmersión total",
                tiempo = "4:00 min",
                cuerpo = "Alto y robusto",
                acidez = "Baja",
                perfilSabor = "Conserva los aceites naturales del café, dando textura cremosa y notas achocolatadas.",
                recomendacion = "Ideal para quienes prefieren un café con cuerpo pesado y sabor tradicional.",
                imagen = R.drawable.prensa
            ),
            MetodoCafe(
                nombre = "AeroPress",
                origen = "EE. UU. · Inmersión y presión",
                tiempo = "1:30 - 2:00 min",
                cuerpo = "Medio y equilibrado",
                acidez = "Media-Baja",
                perfilSabor = "Dulce, redondo, con mínimo amargor y prácticamente sin sedimento.",
                recomendacion = "La opción más versátil si buscas un punto medio entre filtrado y espresso.",
                imagen = R.drawable.aeropress
            ),
            MetodoCafe(
                nombre = "Cold Brew",
                origen = "Extracción en frío",
                tiempo = "12 - 18 horas",
                cuerpo = "Medio-Alto",
                acidez = "Muy baja",
                perfilSabor = "Refrescante, naturalmente dulce, con notas a caramelo y cero astringencia.",
                recomendacion = "Recomendado para días calurosos o estómagos sensibles a la acidez.",
                imagen = R.drawable.cold
            ),
            MetodoCafe(
                nombre = "Sifón Japonés",
                origen = "Europa / Japón · Vacío y vapor",
                tiempo = "3:00 - 4:00 min",
                cuerpo = "Ligero a medio",
                acidez = "Media",
                perfilSabor = "Taza sumamente aromática, limpia y vibrante con final prolongado.",
                recomendacion = "Una experiencia visual única en mesa para disfrutar sin prisa.",
                imagen = R.drawable.sifon
            ),
            MetodoCafe(
                nombre = "Moka Italiana",
                origen = "Italia · Vapor a presión",
                tiempo = "4:00 - 5:00 min",
                cuerpo = "Alto",
                acidez = "Baja",
                perfilSabor = "Fuerte, tostado y con carácter clásico similar al espresso casero.",
                recomendacion = "Ideal si te gustan los sabores intensos y con personalidad.",
                imagen = R.drawable.moka
            ),
            MetodoCafe(
                nombre = "Café Turco",
                origen = "Medio Oriente · Decocción",
                tiempo = "3:00 - 4:00 min",
                cuerpo = "Muy denso",
                acidez = "Baja",
                perfilSabor = "Espeso, especiado y profundo, preparado con molienda extrafina.",
                recomendacion = "Para disfrutar en sorbos pequeños dejando reposar el poso al fondo.",
                imagen = R.drawable.turco
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Guía de Métodos",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Toca cada método para conocer su perfil de sabor y elegir tu taza ideal.",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(listaMetodos) { metodo ->
                MethodCard(metodo = metodo)
            }
        }
    }
}


//TARJETAS DE CATEGORÍAS
@Composable
fun CategoryBadge(
    categoria: Categoria,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            }
        )
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = categoria.emoji)
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = categoria.nombre,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}


/*
Si la cantidad es 0, aparece boton agregar
al precionar agregar la cantidad aumenta en 1
si la cantidad es mayor a 0 aparece boton de + y -
al presionar + la cantidad aumenta en 1
al presionar - la cantidad disminuye en 1
en la barra inferior aparece el total de los productos
 */

//TARJETA PRODUCTO
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


//TARJETA METODO
@Composable
fun MethodCard(metodo: MetodoCafe) {
    var expandido by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expandido = !expandido }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = metodo.imagen),
                    contentDescription = metodo.nombre,
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = metodo.nombre,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = metodo.origen,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "⏱️ ${metodo.tiempo}  ·  ☕ Cuerpo: ${metodo.cuerpo}",
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                Text(
                    text = if (expandido) "▲" else "▼",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            if (expandido) {
                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "🍋 Acidez: ${metodo.acidez}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Perfil de sabor: ${metodo.perfilSabor}",
                    style = MaterialTheme.typography.bodySmall
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "✨ Ideal para: ${metodo.recomendacion}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Medium
                )
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