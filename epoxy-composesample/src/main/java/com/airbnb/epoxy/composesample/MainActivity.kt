package com.airbnb.epoxy.composesample

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.epoxyInterop

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: EpoxyRecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val composeView: ComposeView = findViewById(R.id.compose_view)

        composeView.setContent {
            LazyColumnFor((0..100).toList()) {
                epoxyInterop<OldTextRowModel_> {
                    index(it)
                }
            }
        }

//        recyclerView.withModels {
////
//        }

/*        GlobalScope.launch(Dispatchers.Default) {
            while (true) {
                delay(2000)
                // This these updates, there are crashes on scroll
                recyclerView.requestModelBuild()
            }
        }*/
    }
}

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class OldTextRow(context: Context) : AppCompatTextView(context) {
    init {
        text = "hello from old text row"
    }

    @ModelProp
    fun setIndex(index: Int) {
        text = "Index $index"
    }
}