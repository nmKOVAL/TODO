import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lab2.ui.theme.Lab2Theme

// Головний клас Activity, який запускається при відкритті програми
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab2Theme {
                MainActivityScreen()
            }
        }
    }
}

// Головний екран програми
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainActivityScreen() {
    val itemList = remember { mutableStateListOf<Item>() }
    val textFieldName = remember { mutableStateOf("") }

// Функція для оновлення ID завдань після видалення
    fun updateItemIds() {
        itemList.forEachIndexed { index, item ->
            item.id = index + 1
        }
    }
// Головний контейнер екрану
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF9FAFB))
    ) {
        // Заголовок програми
        Text(
            text = "ToDo List",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        // Список завдань
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .animateContentSize()
        ) {
            // Відображення елементів списку за індексом
            itemsIndexed(itemList) { _, item ->
                ToDoItem(
                    item = item,
                    onDelete = {
                        itemList.remove(item)
                        updateItemIds()
                    }
                )
            }
        }
        // Блок для введення завдання та кнопки додавання
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Поле введення тексту
            TextField(
                value = textFieldName.value,
                onValueChange = { textFieldName.value = it },
                label = { Text("Назва завдання") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                )
            )
            // Кнопка для додавання нового завдання
            Button(
                onClick = {
                    if (textFieldName.value.isNotEmpty()) {

                        itemList.add(Item(id = itemList.size + 1, name = textFieldName.value))
                        textFieldName.value = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(text = "Додати завдання", color = Color.White)
            }
        }
    }
}
// Функція для відображення одного завдання у списку
@Composable
fun ToDoItem(item: Item, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.White)
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ліва частина завдання: ID і текст
        Column {
            Text(text = "ID: ${item.id}", fontWeight = FontWeight.Bold)
            Text(text = "Завдання: ${item.name}")
        }
        // Кнопка для видалення завдання
        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Видалити",
                tint = Color.Red
            )
        }
    }
}

// Клас даних для завдання
data class Item(var id: Int, val name: String)

// Попередній перегляд екрану
@Preview
@Composable
fun MainActivityPreview() {
    Lab2Theme {
        MainActivityScreen()
    }
}