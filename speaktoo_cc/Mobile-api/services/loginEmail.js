const { auth } = require('../config/firebase');
const { signInWithEmailAndPassword, updatePassword } = require('firebase/auth');

async function loginEmail(email, password) {
    try {
        const userCredential = await signInWithEmailAndPassword(auth, email, password)
        return {
            'uid': userCredential.user.uid,
            'email': userCredential.user.email
        };
    } catch (error) {
        console.log(error);
        return 'fail';
    }
}

async function editPassword(email, oldPassword, newPassword) {
    try {
        const userCredential = await signInWithEmailAndPassword(auth, email, oldPassword);
        const update = await updatePassword(userCredential.user, newPassword);
        return 'success';
    } catch (error) {
        console.log(error);
        return 'fail';
    }
}

module.exports = {loginEmail, editPassword};
