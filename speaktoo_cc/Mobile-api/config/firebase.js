// Import the functions you need from the SDKs you need
const { initializeApp } = require('firebase/app');
//const { getAnalytics } = require('firebase/analytics');
const { getAuth } = require('firebase/auth');
const { getStorage } = require('firebase/storage');
require('dotenv').config();

const firebaseConfig = {
  apiKey: process.env.API_KEY,
  authDomain: process.env.AUTH_DOMAIN,
  projectId: process.env.PROJECT_ID,
  storageBucket: process.env.STORAGE_BUCKET,
  messagingSenderId: process.env.MESSAGING_SENDER_ID,
  appId: process.env.APP_ID,
  measurementId: process.env.MEASUREMENT_ID
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const storage = getStorage();

module.exports = { auth, storage };