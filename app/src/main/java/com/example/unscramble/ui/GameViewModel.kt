package com.example.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameViewModel: ViewModel() {
    //Game UI state
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private lateinit var currentWord: String

    //set of words used in the game
    private var usedWords: MutableSet<String> = mutableSetOf()

    var userGuess by mutableStateOf("")
        private set

    private fun pickRandomWordAndShuffle(): String{
        //Continue picking until word is not in useWord
        currentWord = allWords.random()
        if(usedWords.contains(currentWord)){
            return pickRandomWordAndShuffle()
        } else  {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    //starting point?????
    init {
        resetGame()
    }

    //shuffle fun
    private fun shuffleCurrentWord(word:String): String{
        val tempWord = word.toCharArray()
        //scramble the word
        tempWord.shuffle()
        while (String(tempWord).equals(word)){
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun resetGame(){
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambleWord = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessWord: String){
        userGuess = guessWord
    }
}