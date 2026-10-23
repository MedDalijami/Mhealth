package com.example.mhealthcat.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mhealthcat.elementsAndClasses.CreateSelectMenu
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.viewModels.DataDisplayViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.mhealthcat.elementsAndClasses.DataType
import com.example.mhealthcat.data.forms.SleepForm
import com.example.mhealthcat.data.forms.SocialForm
import com.example.mhealthcat.data.forms.SportForm
import com.example.mhealthcat.data.forms.WellbeingForm
import com.example.mhealthcat.ui.theme.RetroDark2
import com.example.mhealthcat.ui.theme.RetroPurple
import com.example.mhealthcat.R
import com.example.mhealthcat.data.filters.EntryFilter
import com.example.mhealthcat.data.filters.TimeFilter
import com.example.mhealthcat.data.repositories.Entry
import com.example.mhealthcat.ui.theme.RetroRed
import kotlinx.coroutines.launch
import com.example.mhealthcat.elementsAndClasses.CategoryChip
import com.example.mhealthcat.ui.theme.RetroPixelBorder
import kotlin.math.roundToInt

@Composable
fun Data() {
    val dataDisplayViewModel: DataDisplayViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
    ) {

        CreateDataMenu(dataDisplayViewModel = dataDisplayViewModel)

        if (dataDisplayViewModel.graphView.collectAsState().value) {
            val dataType = dataDisplayViewModel.selectedDataType.collectAsState().value

            key(dataType) {
                CreateGraph(dataDisplayViewModel)
            }
        } else CreateDataList(dataDisplayViewModel)

    }

}

@Composable
fun CreateDataMenu(dataDisplayViewModel: DataDisplayViewModel) {

    val toggleOptions = listOf("Grafični prikaz", "Prikazano kot seznam")
    val graphView by dataDisplayViewModel.graphView.collectAsState()
    val timeFilter by dataDisplayViewModel.timeFilter.collectAsState()
    val customPeriod by dataDisplayViewModel.customPeriod.collectAsState()
    val ratingRange by dataDisplayViewModel.ratingRange.collectAsState()
    var showPeriodPicker by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        CreateSelectMenu(
            onSelect = { dataDisplayViewModel.changeDataType(it) },
            selectedItem = dataDisplayViewModel.selectedDataType.collectAsState().value.displayName,
            selectItemsList = dataDisplayViewModel.dataTypesAsStringList(),
            label = "Prikazani podatki"
        )

        CreateSelectMenu(
            modifier = Modifier.fillMaxWidth(),
            onSelect = {
                if (it == TimeFilter.CUSTOM.displayName) showPeriodPicker = true
                else dataDisplayViewModel.changeTimeFilter(it)
            },
            selectedItem = dataDisplayViewModel.timeFilterText(timeFilter, customPeriod),
            selectItemsList = dataDisplayViewModel.timeFiltersAsStringList(),
            label = "Obdobje"
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = dataDisplayViewModel.ratingRangeText(ratingRange),
                style = MaterialTheme.typography.bodyMedium
            )
            RangeSlider(
                value = ratingRange.first.toFloat()..ratingRange.last.toFloat(),
                onValueChange = { range ->
                    dataDisplayViewModel.changeRatingRange(
                        range.start.roundToInt(),
                        range.endInclusive.roundToInt()
                    )
                },
                valueRange = EntryFilter.ALL_RATINGS.first.toFloat()..EntryFilter.ALL_RATINGS.last.toFloat(),
                steps = 3,
                colors = SliderDefaults.colors(
                    thumbColor = RetroPixelBorder,
                    activeTrackColor = RetroPixelBorder
                )
            )
        }

        if (showPeriodPicker) {
            CustomPeriodDialog(
                onConfirm = { start, end ->
                    dataDisplayViewModel.setCustomPeriod(start, end)
                    showPeriodPicker = false
                },
                onDismiss = { showPeriodPicker = false }
            )
        }
        CreateToggle(
            modifier = Modifier.fillMaxWidth(),
            selected = if (graphView) 0 else 1,
            onToggle = { dataDisplayViewModel.toggleGraphView(it) },
            options = toggleOptions
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomPeriodDialog(
    onConfirm: (startMillis: Long, endMillis: Long) -> Unit,
    onDismiss: () -> Unit
) {
    val state = rememberDateRangePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    val start = state.selectedStartDateMillis ?: return@TextButton
                    onConfirm(start, state.selectedEndDateMillis ?: start)
                },
                enabled = state.selectedStartDateMillis != null
            ) { Text("Potrdi") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Prekliči") }
        }
    ) {
        DateRangePicker(
            state = state,
            title = { Text("Izberi obdobje", modifier = Modifier.padding(16.dp)) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun CreateChartLegend(slices: List<PieChartData.Slice>) {
    val total = slices.sumOf { it.value.toDouble() }.toFloat()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 20.dp)

    ) {
        slices.chunked(2).forEach { row ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                row.forEach { slice ->
                    val percentage = if (total > 0) (slice.value / total * 100).toInt() else 0
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.weight(1f)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(color = slice.color, shape = CircleShape)
                        )
                        Text(
                            text = "${slice.label} — ${slice.value.toInt()} ($percentage%)",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateToggle(
    modifier: Modifier = Modifier,
    selected: Int = 0,
    onToggle: (Int) -> Unit,
    options: List<String>
) {
    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = { onToggle(index) },
                selected = index == selected,
                label = { Text(label) },
                icon = {},
                colors = SegmentedButtonDefaults.colors(
                    activeContainerColor = RetroPurple
                )
            )

        }
    }

}


@Composable
fun CreateGraph(
    dataDisplayViewModel: DataDisplayViewModel,
) {
    val dataType = dataDisplayViewModel.selectedDataType.collectAsState().value

    val sleepItems by dataDisplayViewModel.listOfSleepData.collectAsState()
    val socialItems by dataDisplayViewModel.listOfSocialData.collectAsState()
    val sportItems by dataDisplayViewModel.listOfSportData.collectAsState()
    val wellbeingItems by dataDisplayViewModel.listOfWellbeingData.collectAsState()

    val chartData: PieChartData = remember(dataType, sleepItems, socialItems, sportItems, wellbeingItems) {
        when (dataType) {
            DataType.ALL -> dataDisplayViewModel.returnAllCategoriesChartData()
            DataType.SLEEP -> dataDisplayViewModel.returnSleepChartData()
            DataType.SOCIAL -> dataDisplayViewModel.returnSocialChartData()
            DataType.SPORT -> dataDisplayViewModel.returnSportChartData()
            DataType.WELLBEING -> dataDisplayViewModel.returnWellbeingChartData()
        }
    }
    val pieChartConfig = PieChartConfig(
        showSliceLabels = false,
        isAnimationEnable = true,
        animationDuration = 1500,
        backgroundColor = Color.Transparent,
        isSumVisible = true,
        sumUnit = "Entries",
        isClickOnSliceEnabled = true
    )
    var selectedSlice by remember { mutableStateOf<PieChartData.Slice?>(null) }


    val total = chartData.slices.sumOf { it.value.toDouble() }.toInt()

    val centerText =
        if (selectedSlice != null) selectedSlice!!.value.toInt().toString() else total.toString()
    val centerLabel =
        if (selectedSlice != null) "${selectedSlice!!.label} od vseh vnosov" else "Vseh vnosov"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CreateChartLegend(chartData.slices)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(400.dp)
        ) {
            PieChart(
                modifier = Modifier.size(380.dp),
                pieChartData = chartData,
                pieChartConfig = pieChartConfig,
                onSliceClick = { slice ->
                    selectedSlice = if (selectedSlice == slice) null else slice
                }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = centerText,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White
                )
                Text(
                    text = centerLabel,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
        }


    }
}

@Composable
fun CreateDataList(dataDisplayViewModel: DataDisplayViewModel) {
    val dataType = dataDisplayViewModel.selectedDataType.collectAsState().value

    val sleepItems by dataDisplayViewModel.listOfSleepData.collectAsState()
    val socialItems by dataDisplayViewModel.listOfSocialData.collectAsState()
    val sportItems by dataDisplayViewModel.listOfSportData.collectAsState()
    val wellbeingItems by dataDisplayViewModel.listOfWellbeingData.collectAsState()

    val items = when (dataType) {
        DataType.SLEEP -> sleepItems
        DataType.SOCIAL -> socialItems
        DataType.SPORT -> sportItems
        DataType.WELLBEING -> wellbeingItems
        DataType.ALL -> (sleepItems + socialItems + sportItems + wellbeingItems).sortedByDescending { it.timestamp }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = 16.dp)

    ) {
        items(items.size) { index ->
            val showCategory = dataType == DataType.ALL

            when (val item = items[index]) {
                is SleepForm -> CreateDeleteSlider(
                    onDismiss = { dataDisplayViewModel.removeSleepItem(item) }
                ) {
                    CreateSleepListItem(
                        item = item,
                        dataDisplayViewModel = dataDisplayViewModel,
                        showCategory = showCategory
                    )
                }

                is SocialForm -> CreateDeleteSlider(
                    onDismiss = { dataDisplayViewModel.removeSocialItem(item) }
                ) {
                    CreateSocialListItem(
                        item = item,
                        dataDisplayViewModel = dataDisplayViewModel,
                        showCategory = showCategory
                    )
                }

                is SportForm -> CreateDeleteSlider(
                    onDismiss = { dataDisplayViewModel.removeSportItem(item) }
                ) {
                    CreateSportListItem(
                        item = item,
                        dataDisplayViewModel = dataDisplayViewModel,
                        showCategory = showCategory
                    )
                }

                is WellbeingForm -> CreateDeleteSlider(
                    onDismiss = { dataDisplayViewModel.removeWellbeingItem(item) }
                ) {
                    CreateWellbeingListItem(
                        item = item,
                        dataDisplayViewModel = dataDisplayViewModel,
                        showCategory = showCategory
                    )
                }
            }
        }
    }
}

private fun categoryOf(item: Entry): DataType = when (item) {
    is SleepForm -> DataType.SLEEP
    is SocialForm -> DataType.SOCIAL
    is SportForm -> DataType.SPORT
    is WellbeingForm -> DataType.WELLBEING
    else -> DataType.ALL
}

@Composable
fun CreateWellbeingListItem(
    item: WellbeingForm,
    dataDisplayViewModel: DataDisplayViewModel,
    showCategory: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(RetroDark2)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if (showCategory) {
            CategoryChip(DataType.WELLBEING)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Počutje ocenjeno z ${item.rating} ⭐",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                dataDisplayViewModel.formatTimestamp(item.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            item.generalFeelings,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            "Skrbi me: ${item.generalFears}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            "Nekaj dobrega, kar se je zgodilo danes: ${item.somethingGoodThatHappened}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

    }
}



@Composable
fun CreateSportListItem(
    item: SportForm,
    dataDisplayViewModel: DataDisplayViewModel,
    showCategory: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(RetroDark2)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if (showCategory) {
            CategoryChip(DataType.SPORT)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "${item.activity} ocenjena z ${item.rating} ⭐",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                dataDisplayViewModel.formatTimestamp(item.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            "Trajanje: ${if (item.hours == 0) "${item.minutes}min" else "${item.hours}h ${item.minutes}min"}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (item.comment.isNotBlank()) {
            Text(
                item.comment,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun CreateDeleteSlider (
    onDismiss : () -> Unit,
    foregroundContent: @Composable () -> Unit
) {
    val dismissBoxState = rememberSwipeToDismissBoxState (
        initialValue = SwipeToDismissBoxValue.Settled,
    )
    val coroutineScope = rememberCoroutineScope ()

    SwipeToDismissBox(
        state = dismissBoxState,
        enableDismissFromStartToEnd = false,
        onDismiss = { dismissValue ->
            if (dismissValue == SwipeToDismissBoxValue.EndToStart){
                coroutineScope.launch {
                    dismissBoxState.reset()
                    onDismiss ()
                }
            }
            else {
                coroutineScope.launch {
                    dismissBoxState.reset()
                }
            }
        },
        backgroundContent = {
            val color by animateColorAsState(
                when (dismissBoxState.currentValue) {
                    SwipeToDismissBoxValue.EndToStart -> RetroRed.copy(alpha = dismissBoxState.progress)
                    else -> Color.Transparent
                },
                label = "background color"
            )

            Row (
                modifier = Modifier
                    .fillMaxSize()
                    .background(color),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.padding(end = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.delete_forever),
                        contentDescription = "delete",
                        tint = Color.White,
                        modifier = Modifier
                            .size(40.dp)
                    )
                    Text(text = "Izbriši")
                }
            }
        }
    ){
        foregroundContent()
    }

}

@Composable
fun CreateSocialListItem(
    item: SocialForm,
    dataDisplayViewModel: DataDisplayViewModel,
    showCategory: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(RetroDark2)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if (showCategory) {
            CategoryChip(DataType.SOCIAL)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "${item.socialInteraction} ocenjen z ${item.rating} ⭐",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                dataDisplayViewModel.formatTimestamp(item.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            "Družil_a sem se ${companionsText(item.people, item.numberOfPeople)} ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            "Trajanje: ${if (item.hours == 0) "${item.minutes}min" else "${item.hours}h ${item.minutes}min"}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (item.comment.isNotBlank()) {
            Text(
                item.comment,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun CreateSleepListItem(
    item: SleepForm,
    dataDisplayViewModel: DataDisplayViewModel,
    showCategory: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(RetroDark2)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        if (showCategory) {
            CategoryChip(DataType.SLEEP)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Spanec ocenjen z ${item.rating} ⭐",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                dataDisplayViewModel.formatTimestamp(item.timestamp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Text(
            "Trajanje: ${if (item.hours == 0) "${item.minutes}min" else "${item.hours}h ${item.minutes}min"}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (item.comment.isNotBlank()) {
            Text(
                item.comment,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DataPreview() {
    MHealthCatTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Data()
        }
    }
}

private fun companionsText(people: String, count: Int): String =
    if (count != 1) {
        when (people) {
            "Prijatelji" -> "z $count prijatelji"
            "Partner/ka" -> "z $count partnerji"
            "Družina" -> "z $count družinskimi člani"
            "Neznanci" -> "z $count neznanci"
            "Sodelavci" -> "z $count sodelavci"
            else -> "z $count ljudmi"
        }
    } else {
        when (people) {
            "Prijatelji" -> "s prijateljem"
            "Partner/ka" -> "s partnerjem/ko"
            "Družina" -> "z družinskim članom"
            "Neznanci" -> "z neznancem"
            "Sodelavci" -> "s sodelavcem"
            else -> "z osebo"
        }
    }