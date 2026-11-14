package com.afterlife.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Folder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.VpnKey
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.afterlife.app.core.designsystem.AfterlifeTheme

private enum class AppTab(val title: String, val icon: ImageVector) {
    HOME("الرئيسية", Icons.Outlined.Home),
    TRUSTED("الشخص الموثوق", Icons.Outlined.Person),
    FIND("العثور على الهاتف", Icons.Outlined.LocationOn),
    VAULT("الخزنة", Icons.Outlined.Folder)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AfterlifeTheme {
                androidx.compose.runtime.CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        AfterlifeApp()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AfterlifeApp() {
    var selectedTab by remember { mutableStateOf(AppTab.HOME) }
    var showLegacy by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }

    val title = when {
        showSettings -> "الإعدادات"
        showLegacy -> "الإرث الرقمي"
        else -> selectedTab.title
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("AFTERLIFE", fontWeight = FontWeight.Bold, letterSpacing = 1.2.sp)
                        Text(title, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                },
                actions = {
                    Icon(
                        imageVector = Icons.Outlined.Security,
                        contentDescription = "حالة الحماية",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 18.dp).size(25.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        bottomBar = {
            if (!showLegacy && !showSettings) {
                NavigationBar {
                    AppTab.values().forEach { tab ->
                        NavigationBarItem(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            icon = { Icon(tab.icon, contentDescription = tab.title) },
                            label = { Text(tab.title, maxLines = 1) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        Box(Modifier.fillMaxSize().padding(padding)) {
            when {
                showSettings -> SettingsScreen(onBack = { showSettings = false })
                showLegacy -> LegacyScreen(onBack = { showLegacy = false })
                selectedTab == AppTab.HOME -> HomeScreen(
                    onOpenTab = { selectedTab = it },
                    onOpenLegacy = { showLegacy = true },
                    onOpenSettings = { showSettings = true }
                )
                selectedTab == AppTab.TRUSTED -> TrustedContactScreen()
                selectedTab == AppTab.FIND -> FindPhoneScreen()
                selectedTab == AppTab.VAULT -> VaultScreen()
            }
        }
    }
}

@Composable
private fun HomeScreen(
    onOpenTab: (AppTab) -> Unit,
    onOpenLegacy: () -> Unit,
    onOpenSettings: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(Modifier.fillMaxWidth().padding(20.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Security, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(28.dp))
                        Spacer(Modifier.width(10.dp))
                        Text("مساحتك الرقمية الآمنة", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    }
                    Text(
                        "اجمع وسائل حماية هاتفك وبياناتك المهمة وخططك الرقمية في مكان واحد.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(Modifier.size(9.dp).background(MaterialTheme.colorScheme.tertiary, CircleShape))
                        Spacer(Modifier.width(7.dp))
                        Text("التطبيق في وضع الإعداد الأولي", style = MaterialTheme.typography.labelMedium)
                    }
                }
            }
        }
        item {
            Text("مركز الحماية", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                StatusTile("الشخص الموثوق", "غير محدد", Icons.Outlined.Person, Modifier.weight(1f))
                StatusTile("الخزنة الرقمية", "بانتظار الإعداد", Icons.Outlined.Folder, Modifier.weight(1f))
            }
        }
        item {
            Text("خدمات AFTERLIFE", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            FeatureCard("الشخص الموثوق", "اختر شخصاً واحداً وفعّل الربط بموافقته", Icons.Outlined.Person) { onOpenTab(AppTab.TRUSTED) }
            FeatureCard("العثور على الهاتف", "إعداد استجابة آمنة لطلب موقع هاتفك", Icons.Outlined.LocationOn) { onOpenTab(AppTab.FIND) }
            FeatureCard("الخزنة الرقمية", "مساحة خاصة للملفات والمستندات المهمة", Icons.Outlined.Folder) { onOpenTab(AppTab.VAULT) }
            FeatureCard("الإرث الرقمي", "رتّب تعليماتك وملفاتك للمستقبل", Icons.Outlined.Description, onOpenLegacy)
            FeatureCard("الإعدادات", "الخصوصية والأمان وتفضيلات التطبيق", Icons.Outlined.Settings, onOpenSettings)
        }
        item {
            Text(
                "تنبيه: هذه نسخة واجهات أولية. لم يتم تفعيل إرسال SMS أو تحديد الموقع أو تشفير الملفات أو تسليم الإرث بعد.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun StatusTile(title: String, status: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Card(modifier = modifier, shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Text(title, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
            Text(status, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun FeatureCard(title: String, subtitle: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(46.dp).background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(14.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text("‹", color = MaterialTheme.colorScheme.primary, fontSize = 28.sp)
        }
    }
}

@Composable
private fun ScreenIntro(title: String, description: String) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text(description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun TrustedContactScreen() {
    var phone by remember { mutableStateOf("") }
    var label by remember { mutableStateOf("") }
    var saved by remember { mutableStateOf(false) }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item { ScreenIntro("الشخص الموثوق", "شخص واحد تختاره بنفسك، ولا يتم تفعيل الربط إلا بعد موافقته والتحقق من هويته.") }
        item {
            Card(shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("بيانات الشخص", fontWeight = FontWeight.Bold)
                    OutlinedTextField(value = label, onValueChange = { label = it; saved = false }, modifier = Modifier.fillMaxWidth(), label = { Text("الاسم أو الوصف") }, singleLine = true)
                    OutlinedTextField(value = phone, onValueChange = { phone = it; saved = false }, modifier = Modifier.fillMaxWidth(), label = { Text("رقم الهاتف مع مفتاح الدولة") }, placeholder = { Text("+964...") }, singleLine = true)
                    Button(onClick = { saved = label.isNotBlank() && phone.length >= 8 }, modifier = Modifier.fillMaxWidth()) { Text("حفظ بيانات أولية") }
                    if (saved) Text("تم حفظ البيانات داخل هذه الشاشة مؤقتاً. لم يتم إرسال دعوة أو تفعيل الربط.", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
        item {
            InfoCard("كيف ستعمل الحماية؟", listOf("إرسال دعوة للشخص المختار", "قبول صريح من الطرف الآخر", "توثيق الربط بين الجهازين", "إلغاء أو تغيير الربط عبر تحقق أمني"))
        }
    }
}

@Composable
private fun FindPhoneScreen() {
    var enabled by remember { mutableStateOf(false) }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item { ScreenIntro("العثور على الهاتف", "إعداد طريقة آمنة تساعد الشخص الموثوق على طلب موقع جهازك عند الحاجة، حتى عند عدم توفر الإنترنت.") }
        item {
            Card(shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Icon(Icons.Outlined.LocationOn, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(34.dp), contentDescription = null)
                    Text("حالة الخدمة", fontWeight = FontWeight.Bold)
                    Text(if (enabled) "مفعّلة تجريبياً في الواجهة فقط" else "غير مفعّلة", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    OutlinedButton(onClick = { enabled = !enabled }, modifier = Modifier.fillMaxWidth()) {
                        Text(if (enabled) "إيقاف العرض التجريبي" else "تفعيل العرض التجريبي")
                    }
                    Text("هذا المفتاح لا يطلب صلاحية الموقع ولا يرسل رسائل SMS حالياً.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
        item { InfoCard("متطلبات التشغيل الفعلي", listOf("موافقة صاحب الجهاز وصلاحيات Android", "رسائل طلب موثقة تشفيرياً", "التحقق من هوية المرسل ومقاومة انتحال SMS", "توضيح حالة GPS والشبكة للمستخدم")) }
    }
}

@Composable
private fun VaultScreen() {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item { ScreenIntro("الخزنة الرقمية", "مكان خاص لحفظ المستندات والملفات الحساسة مع تشفير محلي قبل إضافة أي بيانات مهمة.") }
        item {
            Card(shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.fillMaxWidth().padding(22.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Icon(Icons.Outlined.VpnKey, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(42.dp))
                    Text("الخزنة لم تُجهّز بعد", fontWeight = FontWeight.Bold)
                    Text("لن نطلب منك رفع ملفاتك قبل تنفيذ التشفير وقفل الخزنة بشكل صحيح.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Button(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) { Text("إضافة ملف — قريباً") }
                }
            }
        }
        item { InfoCard("خطة الحماية", listOf("تشفير الملفات على الجهاز", "قفل عبر PIN أو المصادقة الحيوية", "عدم حفظ كلمات المرور كنص صريح", "نسخ احتياطي مشفر اختياري")) }
    }
}

@Composable
private fun LegacyScreen(onBack: () -> Unit) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item { OutlinedButton(onClick = onBack) { Text("العودة للرئيسية") } }
        item { ScreenIntro("الإرث الرقمي", "نظّم تعليماتك وملفاتك وحدد من يمكنه الوصول إليها وفق شروط واضحة وآمنة.") }
        item { InfoCard("مكونات الإرث", listOf("رسالة أو وصية رقمية", "تحديد المستفيدين والصلاحيات", "اختيار الملفات التي يسمح بمشاركتها", "آلية تحقق موثوقة قبل إتاحة البيانات")) }
        item {
            Card(shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("حالة الإعداد", fontWeight = FontWeight.Bold)
                    Text("لم يتم إنشاء وصية رقمية بعد.")
                    Text("لن يعتمد التطبيق على عدم استخدام الهاتف وحده لإثبات الوفاة أو إطلاق البيانات.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Button(onClick = {}, enabled = false, modifier = Modifier.fillMaxWidth()) { Text("إنشاء وصية رقمية — قريباً") }
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen(onBack: () -> Unit) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item { OutlinedButton(onClick = onBack) { Text("العودة للرئيسية") } }
        item { ScreenIntro("الإعدادات", "إدارة الخصوصية والأمان وتفضيلات AFTERLIFE.") }
        item { InfoCard("إعدادات الحساب والجهاز", listOf("قفل التطبيق", "إدارة الأذونات", "إعدادات اللغة واتجاه العرض", "سياسة الخصوصية وشروط الاستخدام", "حول التطبيق وإصدار البناء")) }
        item { Text("لا توجد حسابات أو خدمات سحابية مرتبطة في هذه النسخة الأولية.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant) }
    }
}

@Composable
private fun InfoCard(title: String, lines: List<String>) {
    Card(shape = RoundedCornerShape(20.dp)) {
        Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            lines.forEachIndexed { index, line ->
                Row(verticalAlignment = Alignment.Top) {
                    Text("•", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.width(8.dp))
                    Text(line, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
