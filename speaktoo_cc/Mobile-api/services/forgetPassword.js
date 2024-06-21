const { auth } = require('../config/firebase');
const { sendPasswordResetEmail } = require('firebase/auth');

async function forgetPassword(email){
    try {
        await sendPasswordResetEmail(auth, email);
        return 'mohon cek notifikasi email anda';
    } catch (error) {
        console.log(error);
        return 'fail';
    }
}

module.exports = forgetPassword;