package com.example.ieeeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.ieeeapp.ui.screens.HomeScreen
import com.example.ieeeapp.ui.screens.Event
import com.example.ieeeapp.ui.theme.IEEEAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            IEEEAppTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {

                    val sampleEvents = listOf(
                        Event(
                            title = "AI Workshop 2026",
                            description = "Hands-on AI session by industry experts.",
                            date = "2026-03-01 10:00",
                            venue = "Seminar Hall A",
                            imageUrl = "https://via.placeholder.com/600",
                            formLink = "https://forms.gle/example"
                        ),
                        Event(
                            title = "Web Development Bootcamp",
                            description = "Full-stack development training.",
                            date = "2026-03-15 14:00",
                            venue = "Auditorium",
                            imageUrl = "https://via.placeholder.com/600",
                            formLink = "https://forms.gle/example2"
                        )
                    )

                    HomeScreen(events = sampleEvents)
                }
            }
        }
    }
}