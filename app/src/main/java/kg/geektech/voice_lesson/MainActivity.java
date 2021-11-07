package kg.geektech.voice_lesson;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;

import com.davemorrissey.labs.subscaleview.ImageSource;

import java.util.ArrayList;
import java.util.Locale;

import kg.geektech.voice_lesson.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initListeners();
    }

    private void initListeners() {
        textToSpeech = new TextToSpeech(getApplicationContext(),
                i -> {
                    if (i != TextToSpeech.ERROR) {
                        textToSpeech.setLanguage(Locale.getDefault());
                    }
                });

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        ArrayList<String> text = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        binding.textTest.setText(text.get(0));

                        initImg(text.get(0));
                    }
                }
        );

        binding.button.setOnClickListener(view -> {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            activityResultLauncher.launch(intent);
        });
    }

    private void initImg(String s) {
        switch (s) {
            case "кошка":
                binding.imageView.setImage(ImageSource.resource(R.drawable.cat));
                textToSpeech.speak("Дома́шняя ко́шка — домашнее животное, одно из наиболее популярных «животных-компаньонов». " +
                                "С точки зрения научной систематики, домашняя кошка — млекопитающее семейства кошачьих отряда хищных.",
                        TextToSpeech.QUEUE_FLUSH, null, null);
                break;
            case "рыба":
                binding.imageView.setImage(ImageSource.resource(R.drawable.forel));
                textToSpeech.speak("Рыбы (лат. Pisces) — парафилетическая группа (по современной кладистической классификации) " +
                                "водных позвоночных животных. Обширная группа челюстноротых, для которых характерно жаберное дыхание " +
                                "на всех этапах постэмбрионального развития организма. Рыбы обитают как в солёных, так и в пресных " +
                                "водоёмах — от глубоких океанических впадин до горных ручьёв. Рыбы играют важную роль в большинстве " +
                                "водных экосистем как составляющая пищевых цепей. Многие виды рыб употребляются человеком в пищу и " +
                                "поэтому имеют важное промысловое значение.",
                        TextToSpeech.QUEUE_FLUSH, null, null);
                break;
            case "горох":
                binding.imageView.setImage(ImageSource.resource(R.drawable.goroh));
                textToSpeech.speak("Горо́х — род однолетних и многолетних травянистых растений семейства бобовых. " +
                                "Широко используется как пищевая и кормовая культура",
                        TextToSpeech.QUEUE_FLUSH, null, null);
                break;
            case "змея":
                binding.imageView.setImage(ImageSource.resource(R.drawable.snake));
                textToSpeech.speak("Зме́и — подотряд класса пресмыкающихся отряда чешуйчатые. " +
                        "Змеи обитают на всех континентах, кроме Антарктиды и нескольких крупных островов, " +
                        "таких как Ирландия и Новая Зеландия, а также множества мелких островов Атлантического " +
                        "океана и центральной части Тихого океана.", TextToSpeech.QUEUE_FLUSH, null, null);
                break;
        }
    }

}