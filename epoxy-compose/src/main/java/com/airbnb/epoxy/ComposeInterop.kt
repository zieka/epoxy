package com.airbnb.epoxy

import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView

@Composable
inline fun <reified T : EpoxyModel<*>> epoxyInterop(crossinline modelBuilder: T.() -> Unit) {

    val context = ContextAmbient.current
    val model = T::class.java.newInstance().apply(modelBuilder)

    AndroidView({
        FrameLayout(context).apply {
            addView(model.buildView(this))
        }
    }) { view ->
        @Suppress("UNCHECKED_CAST")
        (model as EpoxyModel<View>).bind(view.getChildAt(0))
    }
}

@Composable
fun EliRow(index: Int) {
    log("Eli Row $index")
    Column {
        log("making column $index")
        Text(
            text = "Hello World! $index",
            fontSize = TextUnit.Companion.Sp(20),
            modifier = Modifier.padding(16.dp)
        )
    }
}

fun ModelCollector.composable(id: String, composeFunction: @Composable () -> Unit) {
    add(ComposeEpoxyModel(composeFunction).apply {
        id(id)
    })
}

data class ComposeEpoxyModel(
    val composeFunction: @Composable () -> Unit
) : EpoxyModelWithView<ComposeView>() {

    override fun buildView(parent: ViewGroup) = ComposeView(parent.context)

    override fun bind(view: ComposeView) {
//        log("binding ${id()}")
        view.setContent(composeFunction)
    }

    override fun unbind(view: ComposeView) {
//        log("unbinding ${id()}")
        // Crashes with "Layer is redrawn for LayoutNode in state NeedsRemeasure" if we set empty content
//        view.setContent(emptyContent())
    }
}

fun log(msg: String) = Log.d("Eli test", msg)