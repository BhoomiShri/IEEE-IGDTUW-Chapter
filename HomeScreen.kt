package com.example.ieeeapp.ui.screens

import android.content.Intent
import android.provider.CalendarContract
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(events: List<Event>) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("IEEE IGDTUW", fontWeight = FontWeight.Bold)
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            if (events.isNotEmpty()) {
                CountdownSection(events[0].date)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Upcoming Events",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(events) { event ->
                    EventCard(event)
                }
            }
        }
    }
}

@Composable
fun CountdownSection(eventDate: String) {

    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val eventTime = formatter.parse(eventDate)?.time ?: 0L
    val currentTime = System.currentTimeMillis()
    val diff = eventTime - currentTime

    val days = TimeUnit.MILLISECONDS.toDays(diff)
    val hours = TimeUnit.MILLISECONDS.toHours(diff) % 24

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Next Event In:", color = Color.White)
            Text(
                "$days Days $hours Hours",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun EventCard(event: Event) {

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(modifier = Modifier.padding(12.dp)) {

            Image(
                painter = rememberAsyncImagePainter(event.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(event.title, fontWeight = FontWeight.Bold)
            Text(event.description)
            Text("📍 ${event.venue}")
            Text("🗓 ${event.date}")

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = android.net.Uri.parse(event.formLink)
                    context.startActivity(intent)
                }
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(4.dp))

            OutlinedButton(
                onClick = {
                    val intent = Intent(Intent.ACTION_INSERT).apply {
                        data = CalendarContract.Events.CONTENT_URI
                        putExtra(CalendarContract.Events.TITLE, event.title)
                        putExtra(CalendarContract.Events.EVENT_LOCATION, event.venue)
                    }
                    context.startActivity(intent)
                }
            ) {
                Text("Add to Calendar")
            }
        }
    }
}