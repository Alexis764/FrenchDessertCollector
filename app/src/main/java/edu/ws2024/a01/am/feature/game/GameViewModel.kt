package edu.ws2024.a01.am.feature.game

import android.annotation.SuppressLint
import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ws2024.a01.am.R
import edu.ws2024.a01.am.navigation.Routes
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val gameItemProvider: GameProvider
) : ViewModel() {

    private val _isRestartDialogVisible = MutableLiveData<Boolean>()
    val isRestartDialogVisible: LiveData<Boolean> = _isRestartDialogVisible

    fun showRestartDialog() {
        _isRestartDialogVisible.value = true
    }

    fun hideRestartDialog() {
        _isRestartDialogVisible.value = false
    }


    private val _isQuitDialogVisible = MutableLiveData<Boolean>()
    val isQuitDialogVisible: LiveData<Boolean> = _isQuitDialogVisible

    fun showQuitDialog() {
        _isQuitDialogVisible.value = true
    }

    fun hideQuitDialog() {
        _isQuitDialogVisible.value = false
    }


    private val _isPauseScreenVisible = MutableLiveData<Boolean>()
    val isPauseScreenVisible: LiveData<Boolean> = _isPauseScreenVisible

    fun showPauseScreen() {
        _isPauseScreenVisible.value = true
        gameCountTimer.cancel()
        secondsTimer.cancel()
        itemsTimer.cancel()
    }

    fun hidePauseScreen() {
        _isPauseScreenVisible.value = false
        initGameCountTime(currentMilliseconds)
        initSecondsTimer()
        itemsTimer.start()
    }


    private val _startNextScreen = MutableLiveData<Boolean>()
    val startNextScreen: LiveData<Boolean> = _startNextScreen

    private var baguettePoints: Int = 0
    private var macaronPoints: Int = 0
    private var puffPoints: Int = 0
    private var fihshbonePoints: Int = 0
    private var bonePoints: Int = 0
    private var secondsTime: Int = 0

    fun navigateToGameOverScreen(navController: NavHostController, username: String) {
        navController.navigate(
            Routes.GameOverScreen.createRoute(
                baguettePoints = baguettePoints,
                macaronPoints = macaronPoints,
                puffPoints = puffPoints,
                fihshbonePoints = fihshbonePoints,
                bonePoints = bonePoints,
                secondsTime = secondsTime,
                totalScore = _playerScore.value!!,
                username = username
            )
        ) {
            popUpTo(Routes.GameScreen.route) {
                inclusive = true
            }
        }
    }


    private var boxWidth: Float = 0f

    private val mainItemList = mutableListOf<GameItemModel>()
    private val _itemList = mutableStateListOf<GameItemModel>()
    val itemList: List<GameItemModel> = _itemList

    private lateinit var itemsTimer: CountDownTimer

    fun initItems(boxWidth: Float) {
        this.boxWidth = boxWidth
        mainItemList.addAll(gameItemProvider(boxWidth))

        itemsTimer = object : CountDownTimer(
            (mainItemList.size * 2000).toLong(),
            2000
        ) {
            override fun onTick(time: Long) {
                if (_itemList.size < mainItemList.size) {
                    val item = (time / 2000).toInt()
                    _itemList.add(mainItemList[item])
                }
            }

            override fun onFinish() {}
        }.start()

        initGameCountTime(30000)
        initSecondsTimer()
    }

    fun checkItemCollected(gameItemModel: GameItemModel) {
        when (gameItemModel.image) {
            R.drawable.baguette -> {
                addScorePoints(gameItemModel.score)
                baguettePoints += gameItemModel.score
            }

            R.drawable.macaron -> {
                addScorePoints(gameItemModel.score)
                macaronPoints += gameItemModel.score
            }

            R.drawable.bone -> {
                minusScorePoints(gameItemModel.score)
                bonePoints += gameItemModel.score
            }

            R.drawable.puff -> {
                addScorePoints(gameItemModel.score)

                gameCountTimer.cancel()
                initGameCountTime(currentMilliseconds + 30000)

                puffPoints += gameItemModel.score
            }

            R.drawable.fishbone -> {
                minusScorePoints(gameItemModel.score)
                minusHealthPoints()
                fihshbonePoints += gameItemModel.score
            }
        }

        returnGameItem(gameItemModel)
    }

    fun checkItemFall(gameItemModel: GameItemModel) {
        when (gameItemModel.image) {
            R.drawable.baguette -> minusHealthPoints()
            R.drawable.macaron -> minusHealthPoints()
            R.drawable.puff -> minusHealthPoints()
        }

        returnGameItem(gameItemModel)
    }

    private fun returnGameItem(gameItemModel: GameItemModel) {
        val itemIndex = _itemList.indexOf(gameItemModel)
        _itemList[itemIndex] = _itemList[itemIndex].copy(
            positionY = 0f,
            positionX = (0..(boxWidth.toInt() - 50)).random().toFloat()
        )
    }


    private var scoreAdded = 0
    private var objectFallingSpeed = 1.2f
    private var isFiftyPointsRewardRecived = false

    private val _playerScore = MutableLiveData(0)
    val playerScore: LiveData<Int> = _playerScore

    private fun addScorePoints(score: Int) {
        _playerScore.value = _playerScore.value!!.plus(score)
        scoreAdded += score

        if (scoreAdded >= 100) {
            addHealthPoints()
            objectFallingSpeed += 0.2f
            scoreAdded = 0
            isFiftyPointsRewardRecived = false

        } else if (scoreAdded >= 50 && !isFiftyPointsRewardRecived) {
            addHealthPoints()
            isFiftyPointsRewardRecived = true
        }
    }

    private fun minusScorePoints(score: Int) {
        _playerScore.value = _playerScore.value!!.minus(score)
    }


    private val _playerHealth = MutableLiveData(3)
    val playerHealth: LiveData<Int> = _playerHealth

    private fun addHealthPoints() {
        _playerHealth.value = _playerHealth.value!!.plus(1)
    }

    private fun minusHealthPoints() {
        _playerHealth.value = _playerHealth.value!!.minus(1)
        if (_playerHealth.value!! <= 0) {
            _startNextScreen.value = true
        }
    }


    private var currentMilliseconds: Long = 30000

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    @SuppressLint("DefaultLocale")
    private fun setTime(milliseconds: Long) {
        currentMilliseconds = milliseconds
        val seconds = (milliseconds / 1000) % 60
        val minutes = (milliseconds / (1000 * 60)) % 60
        val hours = (milliseconds / (1000 * 60 * 60)) % 24
        _time.value = String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private lateinit var gameCountTimer: CountDownTimer
    private lateinit var secondsTimer: CountDownTimer

    private fun initGameCountTime(millisInFuture: Long) {
        gameCountTimer = object : CountDownTimer(millisInFuture, 20) {
            override fun onTick(milliseconds: Long) {
                setTime(milliseconds)
                _itemList.forEachIndexed { index, gameItemModel ->
                    _itemList[index] = _itemList[index].copy(
                        positionY = (gameItemModel.positionY + objectFallingSpeed)
                    )
                }
            }

            override fun onFinish() {
                _startNextScreen.value = true
            }
        }.start()
    }

    private fun initSecondsTimer() {
        secondsTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(milish: Long) {
                secondsTime += 1
            }

            override fun onFinish() {
                secondsTimer.start()
            }
        }.start()
    }


    private val _playerPositionX = MutableLiveData(0f)
    val playerPositionX: LiveData<Float> = _playerPositionX

    fun movePlayer(positionX: Float) {
        _playerPositionX.value = positionX
    }

}