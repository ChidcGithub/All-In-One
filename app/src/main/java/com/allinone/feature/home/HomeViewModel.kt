package com.allinone.feature.home

import androidx.lifecycle.viewModelScope
import com.allinone.core.base.BaseViewModel
import com.allinone.core.di.AppModule
import com.allinone.navigation.AllDemoModules
import com.allinone.navigation.DemoModule
import com.allinone.navigation.ModuleCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    private val _modules = MutableStateFlow(AllDemoModules)
    val modules: StateFlow<List<DemoModule>> = _modules.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<ModuleCategory?>(null)
    val selectedCategory: StateFlow<ModuleCategory?> = _selectedCategory.asStateFlow()

    val categories = ModuleCategory.entries

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
        filterModules()
    }

    fun onCategorySelected(category: ModuleCategory?) {
        _selectedCategory.value = category
        filterModules()
    }

    private fun filterModules() {
        viewModelScope.launch {
            val query = _searchQuery.value.lowercase()
            val category = _selectedCategory.value

            val filtered = AllDemoModules.filter { module ->
                val matchesQuery = query.isEmpty() ||
                        module.title.lowercase().contains(query) ||
                        module.description.lowercase().contains(query)
                val matchesCategory = category == null || module.category == category
                matchesQuery && matchesCategory
            }
            _modules.value = filtered
        }
    }

    fun getModuleCountByCategory(): Map<ModuleCategory, Int> {
        return ModuleCategory.entries.associateWith { category ->
            AllDemoModules.count { it.category == category }
        }
    }
}
