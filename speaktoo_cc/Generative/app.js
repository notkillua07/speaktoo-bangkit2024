import express from 'express';
import bodyParser from 'body-parser';
import cors from 'cors';
import multer from 'multer';
import generateContent from './ai.js';

const app = express();
const port = 5000;
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cors({
    origin: "*",
    methods: "GET,HEAD,PUT,PATCH,POST,DELETE",
}));

const upload = multer({ 
    storage: multer.memoryStorage() 
});

app.post('/generate', upload.single('audio'), async (req, res) => {
    try {
        const word = req.body.word;
        const audioFile = req.file;
        const base64file = audioFile.buffer.toString('base64');

        const text1 = { text: `you're an English teacher. how do you pronounce "${word}" give me feedback on my pronunciation in the audio` };

        const audio1 = {
            inlineData: {
                mimeType: 'audio/wav',
                data: base64file
            }
        };

        const result = await generateContent(text1, audio1);
        const response = result.candidates[0].content.parts[0].text.replace(/\*/g, '').replace(/\n/g, '').replace(/\//g, '').replace(/\n\n/g, '').replace(/\"/g, '') //.replace(/\*\*/g, '');

        res.status(200).json({
            'status': 'success',
            'feedback': response
        });

    } catch (error) {
        console.error('Error generating content:', error);
        res.status(500).json({ status: 'fail', message: 'Error generating content' });
    }
});

app.listen(port, () => {
    console.log('App is running on', port);
})
