package tbm.church

class AppConstants {

    companion object{

        const val SPLASH_TIME_OUT : Long = 3000
        const val LOG : String = "tbm.church"

        const val FIREBASE_HOME_STORAGE_BASE_URL = "gs://antonychurch-a68f2.appspot.com"
        const val FIREBASE_HOME_URL = "/church/home/"
        const val FIREBASE_HOME_WITH_BASE_URL = "$FIREBASE_HOME_STORAGE_BASE_URL/church/home/"


        const val FIRESTORE_BASE_PATH = "AntonyChurch"
        const val FIRESTORE_HOME = "Home"



    }

}