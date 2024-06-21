const db = require('../config/sqlConfig');

async function postUserProgress(user_id, username, callback){
    try {
        const sql = 'INSERT INTO user_progress (user_id, username) VALUES (?, ?)';
        await db.query(sql, [user_id, username]);
        return username;
    } catch (error) {
        console.log(error);
        return 'fail';
    }
}

async function getUserProgress(user_id){
    try{
        const sql = 'SELECT * FROM user_progress WHERE user_id = ?';
        const [result] = await db.query(sql, [user_id]);
        return result[0];
    } catch (error) {
        console.log(error);
        return 'fail';
    }
    
}

async function updateUserProgress(user_id, progress){
    try {
        const sql = 'UPDATE user_progress SET progress = ? WHERE user_id = ?';
        await db.query(sql, [progress, user_id]);
        const result = {
            user_id,
            progress
        };
        return result;
    } catch (error) {
        console.log(error);
        return 'fail';
    }
}

async function getWords(difficulty){
    try {
        const sql = 'SELECT words.word_id, word FROM WORDS WHERE word_type = ?'
        const [result] = await db.query(sql, [difficulty]);
        return result;
    } catch (error) {
        console.log(error);
        return 'fail';
    }
}

async function getCompletedWords(user_id, difficulty){
    try {
        const sql = 'SELECT words.word, COALESCE(user_logs.completed, 0) AS completed FROM user_logs RIGHT JOIN words ON words.word_id = user_logs.word_id WHERE user_logs.user_id = ? AND words.word_type = ?';
        const [result] = await db.query(sql, [user_id, difficulty]);
        return result;
    } catch (error) {
        console.log(error);
        return 'fail';
    }
}

async function postUserLogs(user_id, word_id){
    try {
        const sql = 'INSERT INTO user_logs (user_id, word_id, completed) VALUES (?, ?, ?)';
        await db.query(sql, [user_id, word_id, 1]);
        return 'behasil post logs';
    } catch (error) {
        console.log(error);
        return 'fail';
    }
}

async function editUserUsername (user_id, username){
    try {
        const sql = 'UPDATE user_progress SET username = ? WHERE user_id = ?';
        await db.query(sql, [username, user_id]);
        return username;
    } catch (error) {
        console.log(error);
        return 'fail';
    }
}

async function addUserProfilePic(user_id, url){
    try {
        const sql = 'UPDATE user_progress SET profile_pic = ? WHERE user_id = ?';
        await db.query(sql, [url, user_id]);
        return url;
    } catch (error) {
        console.log(error)
        return 'fail';
    }
}

module.exports = { 
    postUserProgress, 
    getUserProgress, 
    updateUserProgress, 
    getWords, 
    getCompletedWords, 
    postUserLogs,
    editUserUsername,
    addUserProfilePic
};
