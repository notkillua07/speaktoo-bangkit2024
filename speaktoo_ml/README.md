![screenshot](flow.png)
# Machine Learning Documentation

1. **Start**:
   - The process begins.

2. **Load the model and scorer (DeepSpeech)**:
   - The DeepSpeech model and its corresponding scorer are loaded into memory. This step initializes the components necessary for speech-to-text conversion.

3. **Initialize FastAPI**:
   - FastAPI, a web framework for building APIs with Python, is initialized. This framework handles incoming HTTP requests and responses.

4. **Input audio from user**:
   - The user uploads or provides audio input, which is captured by the system.

5. **Save uploaded data in temporary file**:
   - The provided audio input is saved in a temporary file on the server for further processing.

6. **Convert audio, read WAV file**:
   - The audio file is converted into a WAV format if it is not already in that format. The WAV file is then read and prepared for transcription.

7. **Transcribe audio**:
   - Using the loaded DeepSpeech model, the system transcribes the audio file into text.

8. **Analyze pronunciation**:
   - The transcribed text is analyzed for pronunciation. This step involves comparing the user's pronunciation against standard or expected pronunciations to provide feedback or analysis.

9. **Return JSON Response**:
   - The results, including the transcription and any pronunciation analysis, are packaged into a JSON response.

10. **End**:
    - The process concludes with the JSON response being sent back to the user.

## Requirements

- Python 3.8+
- DeepSpeech
- FastAPI
- Pydantic
- Uvicorn

## Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/yourusername/speaktoo.git
    cd speaktoo
    ```

2. Create a virtual environment:
    ```sh
    python3 -m venv venv
    source venv/bin/activate
    ```

3. Install the required packages:
    ```sh
    pip install -r requirements.txt
    ```

## Usage

1. Start the FastAPI server:
    ```sh
    uvicorn main:app --reload
    ```

2. Access the application at `http://127.0.0.1:8000`.

3. Upload an audio file using the provided API endpoint.

4. Receive the JSON response containing the transcription and pronunciation analysis.
