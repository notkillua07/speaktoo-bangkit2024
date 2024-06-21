const axios = require('axios');

function splitResponse(data){
    let word = data[0].word;
    let meanings = data[0].meanings.map((meaning) => {
        let definitions = meaning.definitions.map((definition) => {
            return definition.definition
        })

        return {
            'partOfSpeech': meaning.partOfSpeech,
            'definitions': definitions
        };
    });

    return {
        'word': word,
        'meanings': meanings
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
  
        const results = await Promise.all(promises); // Wait for all promises to resolve
        return results;
    } catch (error) {
      console.log(error);
      return 'fail';
    }
}  

module.exports = getWordService;