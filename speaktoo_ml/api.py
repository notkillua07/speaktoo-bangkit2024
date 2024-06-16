import deepspeech
import numpy as np
import wave
from pydub import AudioSegment
from fastapi import FastAPI, File, UploadFile, Form, HTTPException
from fastapi.responses import JSONResponse
import os
import tempfile

# Convert audio to required format
def convert_audio(input_file, output_file):
    audio = AudioSegment.from_file(input_file)
    audio = audio.set_frame_rate(16000).set_channels(1).set_sample_width(2)
    audio.export(output_file, format='wav')

# Read the WAV file
def read_wav_file(filename):
    with wave.open(filename, 'rb') as wf:
        sample_rate = wf.getframerate()
        if sample_rate != 16000:
            raise ValueError('Only 16000Hz input WAV files are supported.')
        audio = np.frombuffer(wf.readframes(wf.getnframes()), dtype=np.int16)
    return audio

# Function to compare the transcribed text with the reference passage
def analyze_pronunciation(reference, transcribed, window=3):
    ref_words = reference.split()
    trans_words = transcribed.split()
    
    feedback = []
    wrong_words = []
    correct_count = 0
    total_count = len(ref_words)
    
    for i, ref_word in enumerate(ref_words):
        found_match = False
        # Define the window range
        start = max(0, i - window)
        end = min(len(trans_words), i + window + 1)
        
        for j in range(start, end):
            if j < len(trans_words):
                trans_word = trans_words[j]
                if ref_word.lower() == trans_word.lower():
                    correct_count += 1
                    found_match = True
                    break
        
        if not found_match:
            feedback.append(f'Mispronounced or missing word "{ref_word}".')
            wrong_words.append(ref_word)
    
    if len(trans_words) > len(ref_words):
        for j in range(len(ref_words), len(trans_words)):
            feedback.append(f'Extra word "{trans_words[j]}".')
    
    accuracy = (correct_count / total_count) * 100
    return accuracy, feedback, wrong_words

# Absolute paths to model and scorer files
model_file_path = ".deepspeech-model/deepspeech-0.9.3-models.pbmm"
scorer_file_path = ".deepspeech-model/deepspeech-0.9.3-models.scorer"

# Check if model and scorer files are accessible
try:
    with open(model_file_path, 'rb') as f:
        pass
    with open(scorer_file_path, 'rb') as f:
        pass
except Exception as e:
    raise FileNotFoundError(f"Error accessing model or scorer file: {e}")

# Load the model and scorer
model = deepspeech.Model(model_file_path)
model.enableExternalScorer(scorer_file_path)

# Initialize FastAPI
app = FastAPI()

@app.post("/transcribe/")
async def transcribe_audio(file: UploadFile = File(...), reference_passage: str = Form(...)):
    try:
        # Create a temporary directory
        with tempfile.TemporaryDirectory() as tmpdirname:
            # Save the uploaded file
            input_audio_path = os.path.join(tmpdirname, file.filename)
            with open(input_audio_path, "wb") as f:
                f.write(await file.read())

            # Check if the file is already a 16000Hz WAV
            with wave.open(input_audio_path, 'rb') as wf:
                if wf.getframerate() == 16000 and wf.getnchannels() == 1 and wf.getsampwidth() == 2:
                    converted_audio_path = input_audio_path
                else:
                    # Convert the audio file to the required format
                    converted_audio_path = os.path.join(tmpdirname, f"converted_{file.filename}.wav")
                    convert_audio(input_audio_path, converted_audio_path)

            # Read the converted WAV file
            audio_data = read_wav_file(converted_audio_path)

            # Perform the transcription
            transcribed_text = model.stt(audio_data)

            # Analyze pronunciation
            accuracy, feedback, wrong_words = analyze_pronunciation(reference_passage, transcribed_text)

        return JSONResponse(content={
            "transcription": transcribed_text,
            "accuracy": accuracy,
            "feedback": feedback,
            "wrong_words": wrong_words,
        })

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8000)
