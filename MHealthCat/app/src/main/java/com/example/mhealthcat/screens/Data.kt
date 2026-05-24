package com.example.mhealthcat.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import com.example.mhealthcat.ui.theme.RetroPurple

@Composable
fun Data() {
    val dataDisplayViewModel: DataDisplayViewModel = viewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp, start = 20.dp, end = 20.dp),
    ) {

        CreateDataMenu(dataDisplayViewModel = dataDisplayViewModel)


    }

}

@Composable
fun CreateDataMenu(dataDisplayViewModel: DataDisplayViewModel) {

    val toggleOptions = listOf("Grafični prikaz", "Prikazano kot seznam")
    val graphView by dataDisplayViewModel.graphView.collectAsState()

    Column(
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