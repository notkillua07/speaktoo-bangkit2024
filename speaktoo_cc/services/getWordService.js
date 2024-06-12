const axios = require('axios');

function splitResponse(data){
    let word = data[0].word;
    let audio = 'maaf audio tidak tersedia';
    let definition = data[0].meanings;
        
    data[0].phonetics.forEach((phonetic) => {
        if(phonetic.audio != ''){
            audio = phonetic.audio
        }
    });

    return {
        'word': word,
        'meaning': definition,
        'audio': audio
    };
}

async function getWordService(word) {
    try {
        const words = word.split(" ");
        const promises = words.map(async (word) => {
            let url = 'https://api.dictionaryapi.dev/api/v2/entries/en/' + word;
            const response = await axios.get(url);
            const data = response.data;
            const result = splitResponse(data);
            return result;
        });
  
        const results = await Promise.all(promises);
        return results;
    } catch (error) {
        console.log(error);
        return 'fail';
    }
}  

module.exports = getWordService;