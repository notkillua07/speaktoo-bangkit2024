const axios = require('axios');
const signupEmail = require('../services/signupEmail');
const loginEmail = require('../services/loginEmail');
const forgetPassword = require('../services/forgetPassword');
const uploadUserProfilePic = require('../services/profileUser');
const { 
    postUserProgress, 
    getUserProgress, 
    updateUserProgress, 
    getWords, 
    getCompletedWords, 
    postUserLogs,
    editUserUsername,
    addUserProfilePic
} = require('../services/sqlServices');

async function getWord(word) {
    let url = 'https://api.dictionaryapi.dev/api/v2/entries/en/' + word;

    try{
        const response = await axios.get(url);
        const data = response.data;
        
        let word = data[0].word;
        let audio = 'maaf audio tidak tersedia';
        let definition = data[0].meanings;
        
        data[0].phonetics.forEach((phonetic) => {
            if(phonetic.audio != ''){
                audio = phonetic.audio
            }
        });

        return {
            'status': 'success',
            'message': 'berhasil get',
            'data': {
                'word': word,
                'meaning': definition,
                'audio': audio
            }
        };
    }
    catch(error) {
        console.log(error);
        return {
            'status': 'fail',
            'message': 'gagal get word'
        };
    }
}

async function loginUser(email, password){
    try{
        const userCredential = await loginEmail(email, password);
        const userCredentialSQL = await getUserProgress(userCredential.uid);

        if(userCredential === 'fail' || userCredentialSQL === 'fail' ){
            return {
                'status': 'fail',
                'message': 'Login failed, silahkan cek kembali email dan password anda'
            };
        }

        return {
            'status': 'success',
            'message': 'Login berhasil',
            'data': {
                'uid': userCredential.uid,
                'email': userCredential.email,
                'username': userCredentialSQL.username,
                'progress': userCredentialSQL.progress,
                'profile_pic': userCredentialSQL.profile_pic
            }
        };
    }catch(error){
        console.error(error);
        return {
            status: 'fail',
            message: 'Terjadi kesalahan silahkan cek kembali email dan password anda'
        };
    }
}

async function signupUser(email, password, username){
    try {
        const userCredential = await signupEmail(email, password);
        const userCredentialSQL = await postUserProgress(userCredential.uid, username);

        if(userCredential === 'fail' || userCredentialSQL === 'fail' ){
            return {
                'status': 'fail',
                'message': 'Signup failed, silahkan cek kembali email dan password anda'
            };
        }

        return {
            "status": "success",
            "message": "Signup berhasil",
            "data": {
                'uid': userCredential.uid,
                'email': userCredential.email,
                'username': userCredentialSQL
            }
        };
    } catch (error) {
        console.log(error);
        return {
            'status': 'fail',
            'message': 'Signup failed, silahkan cek kembali email dan password anda'
        };
    }
}

async function updateProgress(user_id, progress){
    try {
        const result = await updateUserProgress(user_id, progress);

        if(result === 'fail'){
            return {
                'status': 'fail',
                'message': 'gagal update progress'
            };
        }

        return {
            'status' : 'success',
	        'message' : 'berhasil update progress',
	        'data' : {
	            'uid' : result.user_id,
	            'progress' : result.progress
            }
        };

    } catch (error) {
        console.log(error);
        return {
            'status': 'fail',
            'message': 'gagal update progress'
        }        
    }
}

async function getWordsByDifficulty(user_id, difficulty) {
    try {
        const words = await getWords(difficulty);
        const completedWords = await getCompletedWords(user_id, difficulty);

        if(words === 'fail' || completedWords === 'fail') {
            return {
                'status': 'fail',
                'message': 'gagal get words by difficulty'
            };
        }

        const result = [];
        words.forEach(item => {
            const found = completedWords.find(element => element.word === item.word);
            const completed = found ? found.completed : 0;
            result.push({ ...item, completed });
        });

        console.log(result);

        return {
            'status' : 'success',
	        'message' : 'berhasil get words by difficulty',
	        'data' : result
        }
    } catch (error) {
        console.log(error);
        return {
            'status': 'fail',
            'message': 'gagal get words by difficulty'
        }   
    }
}

async function postLogs(user_id, word_id){
    try {
        const result = await postUserLogs(user_id, word_id);

        if(result === 'fail'){
            return {
                'status': 'fail',
                'message': 'gagal post logs'
            };
        }

        return {
            'status': 'success',
	        'message': result
        };

    } catch (error) {
        console.log(error);
        return {
            'status': 'fail',
            'message': 'gagal post logs'
        }   
    }
}

async function userForgetPassword(email) {
    try {
        const result = await forgetPassword(email);

        if(result === 'fail'){
            return {
                'status': 'fail',
                'message': 'mohon cek kembali format email anda'
            };
        }

        return {
            'status': 'success',
	        'message': result
        };
    } catch (error) {
        console.log(error);
        return {
            'status': 'fail',
            'message': 'mohon cek kembali format email anda'
        };
    }
}

async function editUsername(user_id, username) {
    try {
        const result = await editUserUsername(user_id, username);

        if(result === 'fail'){
            return {
                'status': 'fail',
                'message': 'mohon cek kembail akun anda'
            }
        }

        return {
            'status': 'success',
            'message': 'berhasil mengganti username',
            'data': result
        };
    } catch (error) {
        console.log(error);
        return {
            'status': 'fail',
            'message': 'mohon cek kembail akun anda'
        };
    }
}

async function uploadProfilePic(user_id, file, filename){
    try {
        const file_extension = filename.slice(
            ((filename.lastIndexOf('.') - 1) >>> 0) + 2
        );
        
        const result = await uploadUserProfilePic(user_id, file, file_extension);
        const resultSQL = await addUserProfilePic(user_id, result);

        if(result === 'fail' || resultSQL === 'fail'){
            return {
                'status': 'fail',
                'message': 'mohon cek kembail file anda'
            };
        }

        return {
            'status': 'success',
            'message': 'berhasil upload profile picture'
        };
    } catch (error) {
        console.log(error);
        return {
            'status': 'fail',
            'message': 'terjadi kesalahan pada server'
        };
    }
}

module.exports = { 
    getWord, 
    loginUser, 
    signupUser, 
    updateProgress, 
    getWordsByDifficulty, 
    postLogs, 
    userForgetPassword,
    editUsername,
    uploadProfilePic
};

