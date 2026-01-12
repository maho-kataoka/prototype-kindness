package com.example.kindness

import android.os.Bundle
import kotlinx.coroutines.launch
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KindnessTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF5F1E8)
                ) {
                    KindnessScreen()
                }
            }
        }
    }
}

@Composable
fun KindnessTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFFD4A574),
            surface = Color(0xFFF5F1E8),
            background = Color(0xFFF5F1E8)
        ),
        content = content
    )
}

@Composable
fun KindnessScreen() {
    var inputText by remember { mutableStateOf("") }
    var responseText by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val maxCharacters = 300

    val kindResponses = listOf(
        "お疲れさま。今日もよく頑張ったんだね。",
        "大丈夫だよ。あなたは十分頑張ってるよ。",
        "辛かったね。少しずつでいいからね。",
        "あなたの気持ち、ちゃんと届いてるよ。",
        "無理しないでね。休むことも大切だよ。",
        "そのままのあなたで大丈夫だよ。",
        "今日も一日、お疲れさまでした。",
        "あなたの存在、とても大切だよ。"
    )

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        // 初回起動時に温かいメッセージを表示
        delay(300)
        responseText = "ここはあなたの安心できる場所です"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // タイトル
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text(
                text = "🤎",
                fontSize = 28.sp,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = "優しい返答",
                fontSize = 32.sp,
                color = Color(0xFF6B5B4A)
            )
            Text(
                text = "✨",
                fontSize = 28.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Text(
            text = "あなたの言葉に、心を込めて寄り添います",
            fontSize = 14.sp,
            color = Color(0xFF8B7B6A),
            modifier = Modifier.padding(bottom = 40.dp)
        )

        // 返答表示カード
        if (responseText != null) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 2.dp
                )
            ) {
                Text(
                    text = responseText!!,
                    fontSize = 16.sp,
                    color = Color(0xFF6B5B4A),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    lineHeight = 24.sp
                )
            }
        }

        // 入力カード
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "あなたの言葉を聞かせてください",
                    fontSize = 14.sp,
                    color = Color(0xFF8B7B6A),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                OutlinedTextField(
                    value = inputText,
                    onValueChange = {
                        if (it.length <= maxCharacters) {
                            inputText = it
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    placeholder = {
                        Text(
                            text = "今の気持ちや、伝えたいことを自由に書いてください…",
                            color = Color(0xFFB0A090),
                            fontSize = 14.sp
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFD4A574),
                        unfocusedBorderColor = Color(0xFFE0D5C7),
                        focusedContainerColor = Color(0xFFFAF8F3),
                        unfocusedContainerColor = Color(0xFFFAF8F3)
                    ),
                    enabled = !isLoading,
                    shape = RoundedCornerShape(12.dp)
                )

                Text(
                    text = "${inputText.length} / $maxCharacters",
                    fontSize = 12.sp,
                    color = Color(0xFFB0A090),
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 4.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (inputText.isNotBlank()) {
                            isLoading = true
                            // ランダムな返答を選択
                            val randomResponse = kindResponses.random()
                            // 少し待ってから表示（考えている感を出す）
                            scope.launch {
                                delay(1500)
                                responseText = randomResponse
                                inputText = ""
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    enabled = inputText.isNotBlank() && !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD4A574),
                        disabledContainerColor = Color(0xFFE8D9C5)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (isLoading) "🤔 返答を考えています" else "💗 送信する",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "あなたの言葉を大切にしています",
            fontSize = 12.sp,
            color = Color(0xFFB0A090)
        )
    }
}