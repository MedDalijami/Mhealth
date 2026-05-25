package com.example.mhealthcat.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mhealthcat.ElementsAndClasses.CreateSelectMenu
import com.example.mhealthcat.ui.theme.MHealthCatTheme
import com.example.mhealthcat.viewModels.DataDisplayViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import co.yml.charts.ui.piechart.charts.PieChart
import co.yml.charts.ui.piechart.models.PieChartConfig
import co.yml.charts.ui.piechart.models.PieChartData
import com.example.mhealthcat.ElementsAndClasses.DataType
import com.example.mhealthcat.ui.theme.RetroPurple

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

            // Needs to rebuild the graph every time something changes
            when(dataType) {
                DataType.SLEEP -> CreateGraph(dataDisplayViewModel, DataType.SLEEP)
                DataType.SOCIAL -> CreateGraph(dataDisplayViewModel, DataType.SOCIAL)
                DataType.SPORT -> CreateGraph(dataDisplayViewModel, DataType.SPORT)
                DataType.WELLBEING -> CreateGraph(dataDisplayViewModel, DataType.WELLBEING)
            }
        }

    }

}

@Composable
fun CreateDataMenu(dataDisplayViewModel: DataDisplayViewModel) {

    val toggleOptions = listOf("Grafični prikaz", "Prikazano kot seznam")
    val graphView by dataDisplayViewModel.graphView.collectAsState()

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

        CreateToggle(
            modifier = Modifier.fillMaxWidth(),
            selected = if (graphView) 0 else 1,
            onToggle = { dataDisplayViewModel.toggleGraphView(it) },
            options = toggleOptions
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

        } }

}



@Composable
fun CreateGraph (
    dataDisplayViewModel: DataDisplayViewModel,
    dataType: DataType,
) {
    val dataType1 = dataDisplayViewModel.selectedDataType.collectAsState().value

    val chartData: PieChartData = when (dataType1) {
        DataType.SLEEP -> dataDisplayViewModel.returnSleepChartData()
        DataType.SOCIAL -> dataDisplayViewModel.returnSocialChartData()
        DataType.SPORT -> dataDisplayViewModel.returnSportChartData()
        DataType.WELLBEING -> dataDisplayViewModel.returnWellbeingChartData()
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