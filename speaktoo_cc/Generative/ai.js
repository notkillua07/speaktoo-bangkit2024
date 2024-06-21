import { VertexAI } from '@google-cloud/vertexai';
require('dotenv').config();

const vertexAI = new VertexAI({ project: process.env.PROJECT_ID, location: 'asia-southeast1' });
const model = 'gemini-1.5-flash-001';

const generativeModel = vertexAI.preview.getGenerativeModel({
    model,
    generationConfig: {
        maxOutputTokens: 8192,
        temperature: 1,
        topP: 0.95,
    },
    safetySettings: [
        {
            category: 'HARM_CATEGORY_HATE_SPEECH',
            threshold: 'BLOCK_MEDIUM_AND_ABOVE'
        },
        {
            category: 'HARM_CATEGORY_DANGEROUS_CONTENT',
            threshold: 'BLOCK_MEDIUM_AND_ABOVE'
        },
        {
            category: 'HARM_CATEGORY_SEXUALLY_EXPLICIT',
            threshold: 'BLOCK_MEDIUM_AND_ABOVE'
        },
        {
            category: 'HARM_CATEGORY_HARASSMENT',
            threshold: 'BLOCK_MEDIUM_AND_ABOVE'
        }
    ],
});

async function generateContent(text1, audio1){
    const request = {
        contents: [
            { role: 'user', parts: [text1, audio1] }
        ],
    };

    const streamingResp = await generativeModel.generateContentStream(request);
    return streamingResp.response
}

export default generateContent;