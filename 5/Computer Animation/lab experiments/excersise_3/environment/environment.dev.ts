export const environment = {
  useEmulators: true,
  emulators: {
    functions: {
      host: 'localhost',
      port: 5001,
    },
    firestore: {
      host: 'localhost',
      port: 8080,
    },
    database: {
      host: 'localhost',
      port: 9000,
    },
  },
  firebaseConfig: {
    apiKey: 'AIzaSyC8XiA3KYPtHVDREAljwZJse-wz-HWeQ0c',
    authDomain: 'driving-school-d56f0.firebaseapp.com',
    projectId: 'driving-school-d56f0',
    storageBucket: 'driving-school-d56f0.firebasestorage.app',
    messagingSenderId: '881290574735',
    appId: '1:881290574735:web:41e31fcfe3ee50147229fa',
    measurementId: 'G-33CJBSVTD4',
    databaseURL: 'http://localhost:9000?ns=driving-school-d56f0',
  },
  googleMapsApiKey: 'AIzaSyCK57K3pLRJoAk_YsjlQJp1nPAtQPQKlyI',
  drivingSchoolId: 'dev-driving-school',
};

