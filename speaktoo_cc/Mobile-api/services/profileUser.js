const { storage } = require('../config/firebase');
const { ref, uploadBytes, getDownloadURL } = require('firebase/storage');

async function uploadUserProfilePic(user_id, file, file_extension){
    try {
        const path = user_id + '/' + user_id + '.' + file_extension;
        const fileRef = ref(storage, path);

        await uploadBytes(fileRef, file);

        const url = await getDownloadURL(fileRef);

        return url;
    } catch (error) {
        console.log(error);
        return 'fail';
    }
};

module.exports = uploadUserProfilePic;