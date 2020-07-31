package com.github.pokemon.viewmodel

import androidx.lifecycle.*
import com.github.pokemon.data.RetrofitFactory
import com.github.pokemon.base.App
import com.github.pokemon.data.api.PokeApi
import com.github.pokemon.base.CompletionState
import com.github.pokemon.base.ErrorState
import com.github.pokemon.base.LoadState
import com.github.pokemon.data.entity.PokeEntity
import com.github.pokemon.data.protocol.PokeResp
import com.github.pokemon.data.repository.PokeRepository
import com.github.pokemon.data.repository.PokemonFactory
import com.github.pokemon.viewmodel.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SecondViewModel : BaseViewModel() {

    private val pokemonRepository: PokeRepository by lazy {
        PokemonFactory.makePokemonRepository(RetrofitFactory.createApi(PokeApi::class.java), App.db)
    }



    fun postOf8():LiveData<PokeResp> = pokemonRepository.postOf3().executeFlow().asLiveData()

    @ExperimentalCoroutinesApi
    fun postOfData2(): LiveData<PokeResp> = pokemonRepository.postOf2().executeFlow().asLiveData()


    @ExperimentalCoroutinesApi
    fun postOfData6(): LiveData<List<PokeEntity>> = pokemonRepository.postOf2().executeFlow()
            .map { it.results }.asLiveData()


    @ExperimentalCoroutinesApi
    fun postOfData7(): LiveData<List<PokeEntity>> = pokemonRepository.postOf2()
            .zip(pokemonRepository.postOf2()) { t, t1 ->
                val toMutableList = t.results.toMutableList()
                toMutableList.addAll(t1.results)
                toMutableList
            }.executeFlow().asLiveData()



    // 私有的 MutableLiveData 可变的，对内访问
    private val _pokemon = MutableLiveData<PokeResp>()


    /**
     *  不推荐，视情况而定
     *
     * 方法一
     */
    @ExperimentalCoroutinesApi
    fun postOfData3() = viewModelScope.launch {
        pokemonRepository.postOf2().onStart {
            // 在调用 flow 请求数据之前，做一些准备工作，例如显示正在加载数据的按钮
            mStateLiveData.value = LoadState
        }.catch { ex ->
            // 捕获上游出现的异常
            mStateLiveData.value = ErrorState(ex)
        }.onCompletion {
            // 请求完成
            mStateLiveData.value = CompletionState
        }.collectLatest {
            _pokemon.postValue(it)
        }
    }


    /**
     * 方法二
     */
    @ExperimentalCoroutinesApi
    fun postOfData4(): LiveData<PokeResp> = liveData {
        pokemonRepository.postOf2()
                .onStart {
                    // 在调用 flow 请求数据之前，做一些准备工作，例如显示正在加载数据的按钮
                    mStateLiveData.value = LoadState
                }
                .catch { ex ->
                    // 捕获上游出现的异常
                    mStateLiveData.value = ErrorState(ex)
                }
                .onCompletion {
                    // 请求完成
                    mStateLiveData.value = CompletionState
                }
                .collectLatest {
                    _pokemon.postValue(it)
                    emit(it)
                }
    }


    /**
     * 方法三
     */
    @ExperimentalCoroutinesApi
    fun postOfData5(): LiveData<PokeResp> = pokemonRepository.postOf2()
            .onStart {
                // 在调用 flow 请求数据之前，做一些准备工作，例如显示正在加载数据的按钮
                mStateLiveData.value = LoadState
            }
            .catch { ex ->
                // 捕获上游出现的异常
                mStateLiveData.value = ErrorState(ex)
            }
            .onCompletion {
                // 请求完成
                mStateLiveData.value = CompletionState
            }.asLiveData()

}