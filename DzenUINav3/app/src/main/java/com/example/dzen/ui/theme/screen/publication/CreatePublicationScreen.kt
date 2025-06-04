@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.dzen.ui.theme.screen.publication

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.example.dzen.ui.theme.CreatePublicationBackground
import com.example.dzen.ui.theme.CreatePublicationBorder
import com.example.dzen.ui.theme.Grey400
import com.example.dzen.ui.theme.PrimaryCyan
import com.example.dzen.ui.theme.screen.main_screen.Category

@Composable
fun CreatePublicationScreen(
    modifier: Modifier = Modifier,
    publicationViewModel: PublicationViewModel,
    onPublicationAdded: () -> Unit
) {
    var subtitle by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var showCategoryDialog by remember { mutableStateOf(false) }

    val selectedCategory = publicationViewModel.selectedCategory.collectAsState()

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Create New Publication", fontWeight = FontWeight.Bold, fontSize = 24.sp)

        CategoryPicker(
            text = selectedCategory.value?.title ?: "",
            placeholder = "Select Category",
            onClick = { showCategoryDialog = true }
        )

        SelectCategoryDialog(
            visible = showCategoryDialog,
            onDismissRequest = { showCategoryDialog = false },
            listCategory = Category.mockData,
            selectedCategory = selectedCategory.value,
            onClickSave = { category ->
                publicationViewModel.setSelectedCategory(category)
                showCategoryDialog = false
            }
        )

        OutlinedTextField(
            value = subtitle,
            onValueChange = { subtitle = it },
            label = { Text("Subtitle") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { imagePickerLauncher.launch("image/*") }) {
            Text(text = "Select Image")
        }

        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(it),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        } ?: run {
            Text(text = "No image selected", style = MaterialTheme.typography.bodyMedium)
        }

        Button(
            onClick = {
                publicationViewModel.createPublication(
                    title = selectedCategory.value?.title ?: "",
                    subtitle = subtitle,
                    imgUri = imageUri.toString(),
                )
                onPublicationAdded()
            },
        ) {
            Text("Add Publication")
        }
    }
}

@Composable
fun CategoryPicker(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    onClick: (Boolean) -> Unit,
    isError: Boolean = false,
) {
    val borderColor = animateColorAsState(
        targetValue = if (isError) MaterialTheme.colorScheme.error else CreatePublicationBorder,
        label = "animateBorderColor",
    )
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                shape = MaterialTheme.shapes.small,
            )
            .background(CreatePublicationBackground)
            .border(
                width = 1.dp,
                color = borderColor.value,
                shape = MaterialTheme.shapes.small,
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onClick(true) }
            .padding(20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text.ifEmpty { placeholder },
            color = if (text.isEmpty()) LocalContentColor.current.copy(alpha = 0.5f) else Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun SelectCategoryDialog(
    modifier: Modifier = Modifier,
    visible: Boolean,
    onDismissRequest: () -> Unit,
    listCategory: List<Category>,
    selectedCategory: Category?,
    onClickSave: (Category) -> Unit,
) {
    if (visible) {
        var selectedCategoryLocal by remember { mutableStateOf(selectedCategory) }

        BaseDialogSelect(
            modifier = modifier.fillMaxHeight(0.9f),
            title = "Select category",
            onClickSave = { selectedCategoryLocal?.let { category -> onClickSave(category) } },
            onDismissRequest = onDismissRequest,
            backgroundColor = Color.White,
        ) {
            ListCategory(
                modifier = Modifier
                    .fillMaxHeight(0.8f)
                    .padding(horizontal = 26.dp),
                listCategory = listCategory,
                selectedCategory = selectedCategoryLocal,
                onClickCategory = { category -> selectedCategoryLocal = category }
            )
        }
    }
}

@Composable
private fun ListCategory(
    modifier: Modifier = Modifier,
    contextPadding: PaddingValues = PaddingValues(0.dp),
    listCategory: List<Category>,
    selectedCategory: Category?,
    onClickCategory: (Category) -> Unit
) {

    LazyColumn(
        modifier = modifier,
        contentPadding = contextPadding
    ) {
        items(
            items = listCategory,
            key = { it.id }
        ) { category ->
            Category(
                modifier = Modifier.fillMaxWidth(),
                category = category,
                onClickItem = { onClickCategory(category) },
                selected = category == selectedCategory
            )
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun Category(
    modifier: Modifier = Modifier,
    category: Category,
    selected: Boolean,
    onClickItem: () -> Unit,
) {
    Row(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClickItem
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = category.title,
            style = MaterialTheme.typography.bodyMedium,
        )
        RadioButton(
            selected = selected,
            onClick = onClickItem,
            colors = RadioButtonDefaults.colors(
                selectedColor = PrimaryCyan,
                unselectedColor = Grey400,
            )
        )
    }
}

@Composable
fun BaseDialogSelect(
    modifier: Modifier = Modifier,
    title: String,
    shape: RoundedCornerShape = RoundedCornerShape(16.dp),
    backgroundColor: Color = LocalContentColor.current,
    onDismissRequest: () -> Unit,
    onClickSave: () -> Unit,
    context: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }) {
        Column(
            modifier = modifier
                .clip(shape)
                .background(backgroundColor)
                .padding(top = 26.dp, bottom = 26.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            DialogTitle(
                modifier = Modifier,
                title = title,
            )
            VerticalSpace(height = 16.dp)
            context()
            VerticalSpace(height = 16.dp)
            SaveButton(
                modifier = Modifier
                    .padding(horizontal = 26.dp)
                    .fillMaxWidth(),
                onClickSave = onClickSave
            )
        }
    }
}

@Composable
fun VerticalSpace(modifier: Modifier = Modifier, height: Dp) {
    Spacer(modifier = modifier.padding(vertical = height))
}

@Composable
private fun DialogTitle(
    modifier: Modifier = Modifier,
    title: String,
) {
    androidx.compose.material.Text(
        modifier = modifier,
        text = title,
        style = androidx.compose.material.MaterialTheme.typography.h6
    )
}

@Composable
private fun SaveButton(
    modifier: Modifier = Modifier,
    onClickSave: () -> Unit,
) {
    androidx.compose.material.Button(
        modifier = modifier,
        onClick = { onClickSave() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = PrimaryCyan,
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(6.dp),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        androidx.compose.material.Text(
            text = "Save",
            style = androidx.compose.material.MaterialTheme.typography.body1,
        )
    }
}
